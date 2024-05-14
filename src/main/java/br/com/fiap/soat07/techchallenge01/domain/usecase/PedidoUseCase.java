package br.com.fiap.soat07.techchallenge01.domain.usecase;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import br.com.fiap.soat07.techchallenge01.domain.entity.Pedido;

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
	 * @param cliente {@link Pedido}
	 * @return {@link Pedido}
	 */
	Pedido update(Long id, Pedido pedido);
	
	/**
	 * Delete by id
	 * @param id {@link Long}
	 */
	void delete(Long id);

}
