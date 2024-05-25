package br.com.fiap.soat07.techchallenge01.application.ports.in;

import br.com.fiap.soat07.techchallenge01.application.domain.entity.Produto;

public interface CreateProdutoUseCase {
	
	/**
	 * Create new
	 * @param produto {@link Produto}
	 * @return {@link Produto}
	 */
	Produto create(Produto produto);
	
}
