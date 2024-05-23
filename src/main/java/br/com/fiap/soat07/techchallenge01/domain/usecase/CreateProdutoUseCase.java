package br.com.fiap.soat07.techchallenge01.domain.usecase;

import br.com.fiap.soat07.techchallenge01.domain.entity.Produto;

public interface CreateProdutoUseCase {
	
	/**
	 * Create new
	 * @param produto {@link Produto}
	 * @return {@link Produto}
	 */
	Produto create(Produto produto);
	
}
