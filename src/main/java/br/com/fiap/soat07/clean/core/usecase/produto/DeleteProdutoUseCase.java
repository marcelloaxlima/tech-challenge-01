package br.com.fiap.soat07.clean.core.usecase.produto;

import br.com.fiap.soat07.clean.Utils;
import br.com.fiap.soat07.clean.core.domain.entity.Produto;
import br.com.fiap.soat07.clean.core.gateway.ProdutoGateway;
import br.com.fiap.soat07.clean.infra.rest.dto.ProdutoDTO;

import java.time.OffsetDateTime;

public class DeleteProdutoUseCase {

	private final ProdutoGateway produtoGateway;

	public DeleteProdutoUseCase(final ProdutoGateway produtoGateway) {
		this.produtoGateway = produtoGateway;
	}

	/**
	 * Delete by id
	 * Exclusão lógica, atualiza o campo dataExclusao
	 * @param produto {@link Produto}
	 */

	public void execute(final Produto produto) {
		if (produto == null)
			throw new IllegalArgumentException();

		produto.setDataExclusao(Utils.now());
		produtoGateway.save(produto);
	}
}
