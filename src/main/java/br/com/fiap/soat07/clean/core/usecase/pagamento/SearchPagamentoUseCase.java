package br.com.fiap.soat07.clean.core.usecase.pagamento;

import br.com.fiap.soat07.clean.core.domain.entity.Combo;
import br.com.fiap.soat07.clean.core.domain.entity.Pagamento;
import br.com.fiap.soat07.clean.core.domain.entity.Pedido;
import br.com.fiap.soat07.clean.core.domain.entity.Produto;
import br.com.fiap.soat07.clean.core.domain.enumeration.ProvedorPagamentoEnum;
import br.com.fiap.soat07.clean.core.domain.enumeration.TipoProdutoEnum;
import br.com.fiap.soat07.clean.core.exception.PedidoNotFoundException;
import br.com.fiap.soat07.clean.core.gateway.PedidoGateway;

import java.util.Collection;
import java.util.Optional;

public class SearchPagamentoUseCase {

	private final PedidoGateway pedidoGateway;


	public SearchPagamentoUseCase(PedidoGateway pedidoGateway) {
		this.pedidoGateway = pedidoGateway;
	}


	/**
	 * Get Pagamento by pedidoId
	 * @param id {@link Long}
	 * @return {@link Optional<Pagamento>}
	 */
	public Optional<Pagamento> findById(Long id) {
		if (id == null)
			throw new IllegalArgumentException("Obrigatório informar código do pagamento");

		Pedido pedido = pedidoGateway.findById(id).orElseThrow(() -> new PedidoNotFoundException(id));

		return pedidoGateway.findPagamento(pedido);
	}


	/**
	 * Get by Pedido
	 * @param pedido {@link Pedido}
	 * @return {@link Optional<Pagamento>}
	 */
	public Optional<Pagamento> findByPedido(Pedido pedido) {
		if (pedido == null)
			throw new IllegalArgumentException("Obrigatório informar o pedido");

		return pedidoGateway.findPagamento(pedido);
	}


	/**
	 * Get by Pedido
	 * @param provedor {@link ProvedorPagamentoEnum} provedor de pagamento
	 * @param id {@link String} código de pagamento do provedor
	 * @return {@link Optional<Pagamento>}
	 */
	public Optional<Pagamento> findByProvedor(ProvedorPagamentoEnum provedor, String id) {
		if (provedor == null)
			throw new IllegalArgumentException("Obrigatório informar o provedor");
		if (id == null || id.isEmpty())
			throw new IllegalArgumentException("Obrigatório informar o código de transação de pagamento");

		return pedidoGateway.findPagamento(provedor, id);
	}


}
