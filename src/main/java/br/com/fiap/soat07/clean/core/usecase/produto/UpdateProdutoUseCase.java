package br.com.fiap.soat07.clean.core.usecase.produto;

import br.com.fiap.soat07.clean.Utils;
import br.com.fiap.soat07.clean.core.domain.entity.Produto;
import br.com.fiap.soat07.clean.core.gateway.ProdutoGateway;
import br.com.fiap.soat07.clean.infra.rest.dto.ProdutoDTO;

import java.time.OffsetDateTime;

public class UpdateProdutoUseCase {

	private final ProdutoGateway produtoGateway;

	public UpdateProdutoUseCase(final ProdutoGateway produtoGateway) {
		this.produtoGateway = produtoGateway;
	}

	/**
	 * Update by id
	 * @param produto {@link Produto}
	 * @param produtoDTO {@link ProdutoDTO}
	 * @return {@link Produto}
	 */
	public Produto execute(Produto produto, ProdutoDTO produtoDTO) {
		produto.setCodigo(produtoDTO.getCodigo());
		produto.setNome(produtoDTO.getNome());
		produto.setValor(produtoDTO.getValor());
		produto.setUltimaModificacao(Utils.now());

		produto = produtoGateway.save(produto);

		return produto;
	}
	
}
