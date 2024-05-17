package br.com.fiap.soat07.techchallenge01.domain.provider;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

import br.com.fiap.soat07.techchallenge01.domain.entity.Combo;
import br.com.fiap.soat07.techchallenge01.domain.provider.mapper.ClienteRepositoryMapper;
import br.com.fiap.soat07.techchallenge01.domain.provider.mapper.ComboRepositoryMapper;
import br.com.fiap.soat07.techchallenge01.domain.provider.mapper.ProdutoRepositoryMapper;
import br.com.fiap.soat07.techchallenge01.domain.usecase.ComboUseCase;
import br.com.fiap.soat07.techchallenge01.infra.repository.ComboRepository;
import br.com.fiap.soat07.techchallenge01.infra.repository.CustomComboProdutosRepository;
import br.com.fiap.soat07.techchallenge01.infra.repository.model.ComboModel;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class ComboProviderImpl implements ComboUseCase {

	private final ComboRepository repository;

	private final ComboRepositoryMapper comboMapper;
	
	private final ClienteRepositoryMapper clienteMapper;
	
	private final ProdutoRepositoryMapper produtoMapper;
	
	private final CustomComboProdutosRepository customComboProdutosRepository;

	@Override
	public Page<Combo> getPageable(Pageable pageable) {
		return new PageImpl<>(repository.findAll(pageable).stream().map(comboMapper::toDomain).toList(), pageable,
				repository.findAll(pageable).getNumberOfElements());

	}

	// TODO Criate specific exception
	@Override
	public Combo getById(long id) {
		Optional<ComboModel> comboModel = this.repository.findById(id);
		return comboMapper.toDomain(comboModel.orElseThrow(() -> new RuntimeException()));
	}

	@Override
	public Combo create(Combo combo) {
		return comboMapper.toDomain(repository.save(comboMapper.toModel(combo)));
	}

	@Override
	@Transactional
	public Combo update(long id, Combo comboAtualizado) {
		
		this.repository.findById(id).orElseThrow(() -> new RuntimeException());
		
		if (hasDuplicates(comboAtualizado.getProdutos().stream().map(p -> p.getTipoProduto()).toList())) throw new RuntimeException(); //TODO create a specific exception
		
		List<Long> produtos = customComboProdutosRepository.getProdutosByComboId(id);
				
		customComboProdutosRepository.delete(id, produtos);
		ComboModel comboModelAtualizado = ComboModel.builder()
				.id(comboAtualizado.getId())
				.nome(comboAtualizado.getNome())
				.cliente( clienteMapper.toModel(comboAtualizado.getCliente()))
				.produtos(comboAtualizado.getProdutos().stream().map(produtoMapper::toModel).collect(Collectors.toSet()))
				.build();
		
		return comboMapper.toDomain(
				repository.saveAndFlush(comboModelAtualizado));
	}

	@Override
	public void delete(long id) {
		repository.deleteById(id);
	}
	
	private static <T> boolean hasDuplicates(List<T> list) {
        Set<T> seen = new HashSet<>();
        return list.stream().anyMatch(e -> !seen.add(e));
    }

}
