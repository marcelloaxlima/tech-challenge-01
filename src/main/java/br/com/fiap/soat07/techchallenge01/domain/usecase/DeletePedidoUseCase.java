package br.com.fiap.soat07.techchallenge01.domain.usecase;

import br.com.fiap.soat07.techchallenge01.domain.entity.Pedido;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface DeletePedidoUseCase {
	
	/**
	 * Delete by id
	 * @param id {@link Long}
	 */
	void delete(Long id);

}
