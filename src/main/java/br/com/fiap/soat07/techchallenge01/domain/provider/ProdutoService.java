package br.com.fiap.soat07.techchallenge01.domain.provider;

import br.com.fiap.soat07.techchallenge01.domain.entity.Produto;
import br.com.fiap.soat07.techchallenge01.domain.provider.mapper.ProdutoRepositoryMapper;
import br.com.fiap.soat07.techchallenge01.domain.usecase.CreateProdutoUseCase;
import br.com.fiap.soat07.techchallenge01.domain.usecase.DeletePedidoUseCase;
import br.com.fiap.soat07.techchallenge01.domain.usecase.UpdateProdutoUseCase;
import br.com.fiap.soat07.techchallenge01.infra.repository.ProdutoRepository;
import br.com.fiap.soat07.techchallenge01.infra.repository.model.ProdutoModel;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@RequiredArgsConstructor
public class ProdutoService implements CreateProdutoUseCase, UpdateProdutoUseCase, DeletePedidoUseCase {

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

		ProdutoModel item = repository.getById(id);

		return mapper.toDomain(
				repository.save(
						ProdutoModel.builder()
								.id(item.getId())
								.nome(itemAtualizado.getNome())
								.valor(itemAtualizado.getValor())
								.build()));

	}

	@Override
	public void delete(Long id) {
		repository.deleteById(id);
	}

}
