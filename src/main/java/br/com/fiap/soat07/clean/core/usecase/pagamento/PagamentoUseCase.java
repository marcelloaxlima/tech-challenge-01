package br.com.fiap.soat07.clean.core.usecase.pagamento;

import br.com.fiap.soat07.clean.core.domain.entity.Pagamento;
import br.com.fiap.soat07.clean.core.domain.entity.Pedido;
import br.com.fiap.soat07.clean.core.domain.enumeration.PagamentoStatusEnum;
import br.com.fiap.soat07.clean.core.domain.enumeration.PedidoStatusEnum;
import br.com.fiap.soat07.clean.core.exception.PedidoNotFoundException;
import br.com.fiap.soat07.clean.core.gateway.PagamentoGateway;
import br.com.fiap.soat07.clean.core.gateway.PedidoGateway;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

public class PagamentoUseCase {

	private final PedidoGateway pedidoGateway;
	private final PagamentoGateway pagamentoGateway;

	public PagamentoUseCase(PedidoGateway pedidoGateway, PagamentoGateway pagamentoGateway) {
		this.pedidoGateway = pedidoGateway;
		this.pagamentoGateway = pagamentoGateway;
	}

	public Pagamento executar(Pagamento pagamento) {
		Pedido pedido = pedidoGateway.findById(pagamento.getPedidoId()).orElseThrow(() -> new PedidoNotFoundException(pagamento.getPedidoId()));

		try {
			PagamentoStatusEnum status = pagamentoGateway.executar(pagamento);
			pagamento.setStatus(status);
			pedido.setStatus(status.equals(PagamentoStatusEnum.PAGO) ? PedidoStatusEnum.PAGO : pedido.getStatus());
			pedidoGateway.save(pedido);
			return pagamento;
		}catch(Exception e) {
			pagamento.setStatus(PagamentoStatusEnum.NAO_CONCLUIDO);
			return pagamento;
		}
	}

}
