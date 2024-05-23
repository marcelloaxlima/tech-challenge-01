package br.com.fiap.soat07.techchallenge01.domain.provider;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

import br.com.fiap.soat07.techchallenge01.domain.entity.Produto;
import br.com.fiap.soat07.techchallenge01.domain.enumeration.TipoProdutoEnum;
import br.com.fiap.soat07.techchallenge01.domain.exception.ProdutoNotFoundException;
import br.com.fiap.soat07.techchallenge01.domain.usecase.CreateProdutoUseCase;
import br.com.fiap.soat07.techchallenge01.domain.usecase.DeleteProdutoUseCase;
import br.com.fiap.soat07.techchallenge01.domain.usecase.SearchProdutoUseCase;
import br.com.fiap.soat07.techchallenge01.domain.usecase.UpdateProdutoUseCase;
import br.com.fiap.soat07.techchallenge01.infra.repository.ProdutoRepository;
import br.com.fiap.soat07.techchallenge01.infra.repository.mapper.ProdutoRepositoryMapper;
import br.com.fiap.soat07.techchallenge01.infra.repository.model.ProdutoModel;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class ProdutoService implements CreateProdutoUseCase, UpdateProdutoUseCase, DeleteProdutoUseCase, SearchProdutoUseCase {

	private final ProdutoRepository repository;

	private final ProdutoRepositoryMapper mapper;


	@Override
	public Produto create(Produto produto) {
		return mapper.toDomain(repository.save(mapper.toModel(produto)));
	}

	@Override
	public Produto update(Long id, Produto itemAtualizado) {
		if (id == null)
			throw new IllegalArgumentException("Obrigatório informar o código do produto");

		ProdutoModel itemModel = this.repository.findById(id).orElseThrow(() -> new ProdutoNotFoundException(id));

		return mapper.toDomain(
				repository.save(
						ProdutoModel.builder()
								.id(itemModel.getId())
								.nome(itemAtualizado.getNome())
								.valor(itemAtualizado.getValor())
								.build()));

	}

	@Override
	public void delete(Long id) {
		if (id == null)
			throw new IllegalArgumentException("Obrigatório informar o código do produto");

		ProdutoModel itemModel = this.repository.findById(id).orElseThrow(() -> new ProdutoNotFoundException(id));

		repository.delete(itemModel);
	}

	@Override
	public Produto getById(Long id) {
		Optional<ProdutoModel> itemModel = this.repository.findById(id);
		return mapper.toDomain(itemModel.orElseThrow(() -> new ProdutoNotFoundException(id)));
	}

	@Override
	public Page<Produto> search(Pageable pageable) {
		return new PageImpl<>(
				repository.findAll(pageable).stream().map(mapper::toDomain).toList(), pageable,
				repository.findAll(pageable).getNumberOfElements());

	}

	@Override
	public Page<Produto> search(TipoProdutoEnum tipo, Pageable pageable) {
		if (tipo == null)
			search(pageable);

		return new PageImpl<>(
				repository.search(tipo, pageable).stream().map(mapper::toDomain).toList(), pageable,
				repository.search(tipo, pageable).getNumberOfElements());

	}
}
