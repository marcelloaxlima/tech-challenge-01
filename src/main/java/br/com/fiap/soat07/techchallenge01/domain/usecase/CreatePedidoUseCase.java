package br.com.fiap.soat07.techchallenge01.domain.usecase;

import br.com.fiap.soat07.techchallenge01.domain.entity.Pedido;
import br.com.fiap.soat07.techchallenge01.domain.entity.Produto;

public interface CreatePedidoUseCase {
	
	/**
	 * Create new
	 * @param id
	 * @return {@link Produto}
	 */
	Pedido create(Long id);
	
}
