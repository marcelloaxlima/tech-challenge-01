package br.com.fiap.soat07.techchallenge01.domain.provider;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

import br.com.fiap.soat07.techchallenge01.domain.entity.Combo;
import br.com.fiap.soat07.techchallenge01.domain.provider.mapper.ComboRepositoryMapper;
import br.com.fiap.soat07.techchallenge01.domain.usecase.ComboUseCase;
import br.com.fiap.soat07.techchallenge01.infra.repository.ComboRepository;
import br.com.fiap.soat07.techchallenge01.infra.repository.model.ComboModel;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class ComboProviderImpl implements ComboUseCase {

	private final ComboRepository repository;

	private final ComboRepositoryMapper mapper;

	@Override
	public Page<Combo> getPageable(Pageable pageable) {
		return new PageImpl<>(repository.findAll(pageable).stream().map(mapper::toDomain).toList(), pageable,
				repository.findAll(pageable).getNumberOfElements());

	}

	// TODO Criate specific exception
	@Override
	public Combo getById(long id) {
		Optional<ComboModel> comboModel = this.repository.findById(id);
		return mapper.toDomain(comboModel.orElseThrow(() -> new RuntimeException()));
	}

	@Override
	public Combo create(Combo combo) {
		return mapper.toDomain(repository.save(mapper.toModel(combo)));
	}

	@Override
	public Combo update(long id, Combo comboAtualizado) {
		Combo combo = getById(id);
		return mapper.toDomain(
				repository.save(ComboModel.builder().id(combo.getId()).nome(comboAtualizado.getNome()).build()));
	}

	@Override
	public void delete(long id) {
		repository.deleteById(id);
	}

}
