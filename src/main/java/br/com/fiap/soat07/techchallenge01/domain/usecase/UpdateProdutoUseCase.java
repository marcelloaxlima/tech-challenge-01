package br.com.fiap.soat07.techchallenge01.domain.usecase;

import br.com.fiap.soat07.techchallenge01.domain.entity.Produto;

public interface UpdateProdutoUseCase {
	
	/**
	 * Update by id
	 * @param id {@link Long}
	 * @param produto {@link Produto}
	 * @return {@link Produto}
	 */
	Produto update(Long id, Produto produto);
	
}
