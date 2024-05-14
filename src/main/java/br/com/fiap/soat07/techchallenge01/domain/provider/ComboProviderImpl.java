package br.com.fiap.soat07.techchallenge01.domain.provider;

import java.util.Optional;

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
import br.com.fiap.soat07.techchallenge01.infra.repository.model.ComboModel;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class ComboProviderImpl implements ComboUseCase {

	private final ComboRepository repository;

	private final ComboRepositoryMapper comboMapper;
	
	private final ClienteRepositoryMapper clienteMapper;
	
	private final ProdutoRepositoryMapper produtoMapper;

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
	public Combo update(long id, Combo comboAtualizado) {
		
		Combo combo = getById(id);
		if (null == combo) {
			throw new RuntimeException(); //TODO Create a specific exception
		}
		ComboModel comboModel = ComboModel.builder()
				.id(comboAtualizado.getId())
				.nome(comboAtualizado.getNome())
				.cliente( clienteMapper.toModel(comboAtualizado.getCliente()))
				.produtos(comboAtualizado.getProdutos().stream().map(produtoMapper::toModel).toList())
				.build();
		
		return comboMapper.toDomain(
				repository.save(comboModel));
	}

	@Override
	public void delete(long id) {
		repository.deleteById(id);
	}

}
