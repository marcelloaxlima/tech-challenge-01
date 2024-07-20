package br.com.fiap.soat07.clean.core.usecase.produto;

import br.com.fiap.soat07.clean.core.domain.entity.Combo;
import br.com.fiap.soat07.clean.core.domain.entity.Pedido;
import br.com.fiap.soat07.clean.core.domain.entity.Produto;
import br.com.fiap.soat07.clean.core.domain.enumeration.TipoProdutoEnum;
import br.com.fiap.soat07.clean.core.gateway.ProdutoGateway;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Collection;
import java.util.Optional;

public class SearchProdutoUseCase {

	private final ProdutoGateway produtoGateway;


	public SearchProdutoUseCase(ProdutoGateway produtoGateway) {
		this.produtoGateway = produtoGateway;
	}


	/**
	 * Get by id
	 * @param id {@link Long}
	 * @return {@link Optional<Produto>}
	 */
	public Optional<Produto> findById(Long id) {
		if (id == null)
			throw new IllegalArgumentException("Obrigatório informar código do produto");

		return produtoGateway.findById(id);
	}


	public Collection<Produto> find(int pageNumber, int pageSize) {
		return produtoGateway.find(pageNumber, pageSize);
	}

	public Collection<Produto> find(Pedido pedido) {
		if (pedido == null)
			throw new IllegalArgumentException("Obrigatório informar o pedido");

		return produtoGateway.find(pedido);
	}

	public Collection<Produto> find(Combo combo) {
		if (combo == null)
			throw new IllegalArgumentException("Obrigatório informar o combo");

		return produtoGateway.find(combo);
	}

	public Collection<Produto> find(TipoProdutoEnum tipo, int pageNumber, int pageSize) {
		if (tipo == null)
			throw new IllegalArgumentException("Obrigatório informar o tipo do pedido");

		return produtoGateway.find(tipo, pageNumber, pageSize);
	}

}
