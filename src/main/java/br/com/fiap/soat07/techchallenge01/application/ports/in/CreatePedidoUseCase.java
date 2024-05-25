package br.com.fiap.soat07.techchallenge01.application.ports.in;

import br.com.fiap.soat07.techchallenge01.application.domain.entity.Pedido;
import br.com.fiap.soat07.techchallenge01.application.domain.entity.Produto;

public interface CreatePedidoUseCase {
	
	/**
	 * Create new
	 * @param id
	 * @return {@link Produto}
	 */
	Pedido create(Long id);
	
}
