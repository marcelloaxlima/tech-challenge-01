package br.com.fiap.soat07.clean.core.usecase.pagamento;

import br.com.fiap.soat07.clean.core.domain.entity.Pagamento;
import br.com.fiap.soat07.clean.core.domain.entity.Pedido;
import br.com.fiap.soat07.clean.core.domain.enumeration.MetodoPagamentoEnum;
import br.com.fiap.soat07.clean.core.domain.enumeration.PagamentoStatusEnum;
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
			String transactionCode = pagamentoGateway.getTransaction(metodo, pedido.getValor());

			pagamento = new Pagamento();
			pagamento.setPedidoId(pedido.getId());
			pagamento.setId(transactionCode);
			pagamento.setStatus(PagamentoStatusEnum.NAO_CONCLUIDO);
			pagamento.setProvedorServico(provedor);
			pagamento.setMetodoPagamento(metodo);
			pedidoGateway.save(pedido, pagamento);
		} else {
			pagamento = pagamentoOp.get();
			PagamentoStatusEnum status = pagamentoGateway.getSituacao(pedido.getCodigo());
			pagamento.setStatus(status);
			pedidoGateway.save(pedido, pagamento);
		}

		return pagamento;
	}

}
