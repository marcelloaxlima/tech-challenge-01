package br.com.fiap.soat07.techchallenge01.domain.usecase;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import br.com.fiap.soat07.techchallenge01.domain.entity.Pedido;
import br.com.fiap.soat07.techchallenge01.domain.enumeration.PedidoStatusEnum;

public interface PedidoUseCase {
	
	/**
	 * Get pageable
	 * @param pageable {@link Pageable}
	 * @return {@link Page<Pedido>}
	 */
	Page<Pedido> getPageable(Pageable pageable);
	
	/**
	 * Get by id
	 * @param id {@link Long}
	 * @return {@link Pedido}
	 */
	Pedido getById(Long id);
	
	/**
	 * Create new
	 * @param pedido {@link Pedido}
	 * @return {@link Pedido}
	 */
	Pedido create(Pedido pedido);
	
	/**
	 * Update by id
	 * @param id {@link Long}
	 * @param pedido {@link Pedido}
	 * @return {@link Pedido}
	 */
	Pedido update(Long id, Pedido pedido);
	
	/**
	 * Update by id
	 * @param id {@link Long}
	 * @param status {@link PedidoStatusEnum}
	 * @return {@link Pedido}
	 */
	Pedido updateStatus(Long id, PedidoStatusEnum status);
	
	/**
	 * Delete by id
	 * @param id {@link Long}
	 */
	void delete(Long id);

}
