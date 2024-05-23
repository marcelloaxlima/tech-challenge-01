package br.com.fiap.soat07.techchallenge01.domain.provider;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

import br.com.fiap.soat07.techchallenge01.domain.entity.Produto;
import br.com.fiap.soat07.techchallenge01.domain.exception.ProdutoNotFoundException;
import br.com.fiap.soat07.techchallenge01.domain.usecase.ProdutoUseCase;
import br.com.fiap.soat07.techchallenge01.infra.repository.ProdutoRepository;
import br.com.fiap.soat07.techchallenge01.infra.repository.mapper.ProdutoRepositoryMapper;
import br.com.fiap.soat07.techchallenge01.infra.repository.model.ProdutoModel;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class ProdutoProviderImpl implements ProdutoUseCase {

	private final ProdutoRepository repository;

	private final ProdutoRepositoryMapper mapper;

	@Override
	public Page<Produto> getPageable(Pageable pageable) {
		return new PageImpl<>(repository.findAll(pageable).stream().map(mapper::toDomain).toList(), pageable,
				repository.findAll(pageable).getNumberOfElements());

	}

	@Override
	public Produto getById(Long id) {
		Optional<ProdutoModel> itemModel = this.repository.findById(id);
		return mapper.toDomain(itemModel.orElseThrow(() -> new ProdutoNotFoundException(id)));
	}

	@Override
	public Produto create(Produto produto) {
		return mapper.toDomain(repository.save(mapper.toModel(produto)));
	}

	@Override
	public Produto update(Long id, Produto produtoAtualizado) {
		
		this.repository.findById(id).orElseThrow(() -> new ProdutoNotFoundException(id));
		
		ProdutoModel produtoModel = ProdutoModel.builder()
				.codigo(produtoAtualizado.getCodigo())
				.id(produtoAtualizado.getId())
				.nome(produtoAtualizado.getNome())
				.tipoProduto(produtoAtualizado.getTipoProduto())
				.valor(produtoAtualizado.getValor())
				.build();
		return mapper.toDomain(
				repository.save(produtoModel));
	}

	@Override
	public void delete(Long id) {
		this.repository.findById(id).orElseThrow(() -> new ProdutoNotFoundException(id));
		repository.deleteById(id);
	}

}
