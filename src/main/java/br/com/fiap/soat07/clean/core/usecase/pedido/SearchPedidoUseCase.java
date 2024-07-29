package br.com.fiap.soat07.clean.core.usecase.pedido;

import br.com.fiap.soat07.clean.core.domain.entity.Combo;
import br.com.fiap.soat07.clean.core.domain.entity.Pedido;
import br.com.fiap.soat07.clean.core.gateway.PedidoGateway;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.Optional;

@Component
public class SearchPedidoUseCase {

	private final PedidoGateway pedidoGateway;


	public SearchPedidoUseCase(PedidoGateway pedidoGateway) {
		this.pedidoGateway = pedidoGateway;
	}


	/**
	 * Get by id
	 * @param id {@link Long}
	 * @return {@link Optional<Pedido>}
	 */
	public Optional<Pedido> findById(Long id) {
		if (id == null)
			throw new IllegalArgumentException("Obrigatório informar código do pedido");

		return pedidoGateway.findById(id);
	}


	public Collection<Pedido> find(int pageNumber, int pageSize) {
		return pedidoGateway.find(pageNumber, pageSize);
	}


}
