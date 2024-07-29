package br.com.fiap.soat07.clean.core.usecase.pagamento;

import br.com.fiap.soat07.clean.core.domain.entity.Pagamento;
import br.com.fiap.soat07.clean.core.domain.entity.Pedido;
import br.com.fiap.soat07.clean.core.domain.enumeration.PagamentoStatusEnum;
import br.com.fiap.soat07.clean.core.domain.enumeration.PedidoStatusEnum;
import br.com.fiap.soat07.clean.core.gateway.PagamentoGateway;
import br.com.fiap.soat07.clean.core.gateway.PedidoGateway;

public class UpdatePagamentoUseCase {

	private final PedidoGateway pedidoGateway;

	public UpdatePagamentoUseCase(PedidoGateway pedidoGateway) {
		this.pedidoGateway = pedidoGateway;
	}

	public Pagamento executar(Pedido pedido, Pagamento pagamento, PagamentoStatusEnum status) {

		if (PagamentoStatusEnum.PAGO.equals(status))
			pedido.setStatus(PedidoStatusEnum.PAGO);
		pedidoGateway.save(pedido);

		pagamento.setStatus(status);
		return pedidoGateway.save(pedido, pagamento);
	}

}
