package br.com.fiap.soat07.clean.core.usecase.pagamento;

import br.com.fiap.soat07.clean.core.domain.entity.Pagamento;
import br.com.fiap.soat07.clean.core.domain.entity.Pedido;
import br.com.fiap.soat07.clean.core.domain.enumeration.PagamentoStatusEnum;
import br.com.fiap.soat07.clean.core.domain.enumeration.PedidoStatusEnum;
import br.com.fiap.soat07.clean.core.exception.PedidoNotFoundException;
import br.com.fiap.soat07.clean.core.gateway.PagamentoGateway;
import br.com.fiap.soat07.clean.core.gateway.PedidoGateway;

import java.util.Optional;

public class RetornoGatewayPagamentoUseCase {

	private final PedidoGateway pedidoGateway;
	private final PagamentoGateway pagamentoGateway;

	public RetornoGatewayPagamentoUseCase(PedidoGateway pedidoGateway, PagamentoGateway pagamentoGateway) {
		this.pedidoGateway = pedidoGateway;
		this.pagamentoGateway = pagamentoGateway;
	}

	public PagamentoStatusEnum executar(Pedido pedido) {

		return pagamentoGateway.getSituacao(pedido.getCodigo());

	}

}
