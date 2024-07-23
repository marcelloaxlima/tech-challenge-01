package br.com.fiap.soat07.clean.core.usecase.produto;

import br.com.fiap.soat07.clean.Utils;
import br.com.fiap.soat07.clean.core.domain.entity.Produto;
import br.com.fiap.soat07.clean.core.exception.ProdutoDuplicadoComboException;
import br.com.fiap.soat07.clean.core.gateway.ProdutoGateway;
import br.com.fiap.soat07.clean.infra.rest.dto.ProdutoDTO;

import java.time.OffsetDateTime;

public class CreateProdutoUseCase {

	private final ProdutoGateway produtoGateway;

	public CreateProdutoUseCase(final ProdutoGateway produtoGateway) {
		this.produtoGateway = produtoGateway;
	}

	/**
	 * Create new
	 * @param produtoDTO {@link ProdutoDTO}
	 * @return {@link Produto}
	 */
	public Produto execute(final ProdutoDTO produtoDTO) {
		if (produtoDTO == null)
			throw new IllegalArgumentException();

		if (produtoDTO.getCodigo() == null)
			throw new IllegalArgumentException("Obrigatório código");

		if (produtoDTO.getNome() == null)
			throw new IllegalArgumentException("Obrigatório nome");

		Produto produto = new Produto();
		produto.setCodigo(produtoDTO.getCodigo());
		produto.setNome(produtoDTO.getNome());
		produto.setValor(produtoDTO.getValor());
		produto.setTipoProduto(produtoDTO.getTipoProduto());
		produto.setDataCriacao(Utils.now());
		produto.setUltimaModificacao(produto.getDataCriacao());

		produto = produtoGateway.save(produto);
		return produto;
	}
	
}
