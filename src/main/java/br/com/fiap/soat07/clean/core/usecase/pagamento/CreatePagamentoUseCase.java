package br.com.fiap.soat07.clean.core.usecase.pagamento;

import br.com.fiap.soat07.clean.core.domain.entity.Pagamento;
import br.com.fiap.soat07.clean.core.domain.entity.Pedido;
import br.com.fiap.soat07.clean.core.domain.enumeration.MetodoPagamentoEnum;
import br.com.fiap.soat07.clean.core.domain.enumeration.PagamentoStatusEnum;
import br.com.fiap.soat07.clean.core.domain.enumeration.PedidoStatusEnum;
import br.com.fiap.soat07.clean.core.domain.enumeration.ProvedorPagamentoEnum;
import br.com.fiap.soat07.clean.core.gateway.PagamentoGateway;
import br.com.fiap.soat07.clean.core.gateway.PedidoGateway;

import java.util.Optional;

public class CreatePagamentoUseCase {

	private final PedidoGateway pedidoGateway;
	private final PagamentoGateway pagamentoGateway;

	public CreatePagamentoUseCase(PedidoGateway pedidoGateway, PagamentoGateway pagamentoGateway) {
		this.pedidoGateway = pedidoGateway;
		this.pagamentoGateway = pagamentoGateway;
	}

	public Pagamento executar(Pedido pedido, ProvedorPagamentoEnum provedor, MetodoPagamentoEnum metodo) {

		Optional<Pagamento> pagamentoOp = pedidoGateway.findPagamento(pedido);
		Pagamento pagamento = null;
		if (pagamentoOp.isEmpty()) {
			pagamento = pagamentoGateway.create(pedido);
			pagamento.setStatus(PagamentoStatusEnum.NAO_CONCLUIDO);
			pagamento.setProvedorServico(provedor);
			pagamento.setMetodoPagamento(metodo);
		} else {
			pagamento = pagamentoOp.get();
			PagamentoStatusEnum status = pagamentoGateway.getSituacao(pagamento);
			pagamento.setStatus(status);
		}
		pagamento = pedidoGateway.save(pedido, pagamento);

		return pagamento;
	}

}
