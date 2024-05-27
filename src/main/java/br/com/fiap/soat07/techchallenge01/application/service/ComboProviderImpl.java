package br.com.fiap.soat07.techchallenge01.application.service;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import br.com.fiap.soat07.techchallenge01.application.domain.entity.Combo;
import br.com.fiap.soat07.techchallenge01.application.domain.entity.Produto;
import br.com.fiap.soat07.techchallenge01.application.exception.ComboNotFoundException;
import br.com.fiap.soat07.techchallenge01.application.exception.ProdutoDuplicadoComboException;
import br.com.fiap.soat07.techchallenge01.application.ports.in.ComboUseCase;
import br.com.fiap.soat07.techchallenge01.application.ports.out.persistence.ComboRepository;
import br.com.fiap.soat07.techchallenge01.application.ports.out.persistence.CustomComboProdutosRepository;
import br.com.fiap.soat07.techchallenge01.application.ports.out.persistence.ProdutoRepository;
import br.com.fiap.soat07.techchallenge01.adapter.out.persistence.mysql.mapper.ClienteRepositoryMapper;
import br.com.fiap.soat07.techchallenge01.adapter.out.persistence.mysql.mapper.ComboRepositoryMapper;
import br.com.fiap.soat07.techchallenge01.adapter.out.persistence.mysql.mapper.ProdutoRepositoryMapper;
import br.com.fiap.soat07.techchallenge01.adapter.out.persistence.mysql.model.ComboModel;
import br.com.fiap.soat07.techchallenge01.adapter.out.persistence.mysql.model.ProdutoModel;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ComboProviderImpl implements ComboUseCase {

	private final ComboRepository comboRepository;
	
	private final ProdutoRepository produtoRepository;

	private final ComboRepositoryMapper comboMapper;
	
	private final ClienteRepositoryMapper clienteMapper;
	
	private final ProdutoRepositoryMapper produtoMapper;
	
	private final CustomComboProdutosRepository customComboProdutosRepository;

	@Override
	@Transactional
	public Page<Combo> getPageable(Pageable pageable) {
		return new PageImpl<>(comboRepository.findAll(pageable).stream().map(comboMapper::toDomain).toList(), pageable,
				comboRepository.findAll(pageable).getNumberOfElements());
	}

	@Override
	@Transactional
	public Combo getById(long id) {
		Optional<ComboModel> comboModelOptional = this.comboRepository.findComboById(id);
		ComboModel comboModel = comboModelOptional.orElseThrow(() -> new ComboNotFoundException(id));		
		Optional<Set<ProdutoModel>> produtoModelOptional = produtoRepository.findProdutosByComboId(id);
		
		List<Produto> produtos = produtoModelOptional.orElseGet(null).stream()
				.map(produtoMapper::toDomain).toList();
		Combo combo = comboMapper.toDomain(comboModel); 
		combo.setProdutos(produtos);
		return combo;
	}

	@Override
	public Combo create(Combo combo) {
		return comboMapper.toDomain(comboRepository.save(comboMapper.toModel(combo)));
	}

	@Override
	@Transactional
	public Combo update(long id, Combo comboAtualizado) {
		
		ComboModel comboModel = this.comboRepository.findById(id).orElseThrow(() -> new ComboNotFoundException(id));

		if (hasDuplicates(comboAtualizado.getProdutos().stream().map(p -> p.getTipoProduto()).toList())) throw new ProdutoDuplicadoComboException();
		
		List<ProdutoModel> produtosAntigo = customComboProdutosRepository.getProdutosByComboId(id);
		List<Long> produtosAntigos = produtosAntigo.stream().map(ProdutoModel::getId).toList();
		customComboProdutosRepository.delete(id, produtosAntigos);

		List<ProdutoModel> produtos = comboAtualizado.getProdutos().stream().map(produtoMapper::toModel).toList();
		Set<ProdutoModel> produtosModel = new HashSet<>(produtos);
		comboModel.setProdutos(produtosModel);
		
		return comboMapper.toDomain(
				comboRepository.saveAndFlush(comboModel));
	}

	@Override
	public void delete(long id) {
		this.comboRepository.findById(id).orElseThrow(() -> new ComboNotFoundException(id));
		comboRepository.deleteById(id);
	}
	
	private static <T> boolean hasDuplicates(List<T> list) {
        Set<T> seen = new HashSet<>();
        return list.stream().anyMatch(e -> !seen.add(e));
    }

}
