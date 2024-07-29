package br.com.fiap.soat07.clean.infra.pagamento;

import br.com.fiap.soat07.clean.core.domain.entity.Pedido;
import br.com.fiap.soat07.clean.core.domain.enumeration.PedidoStatusEnum;
import br.com.fiap.soat07.clean.core.exception.PedidoNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import br.com.fiap.soat07.clean.core.domain.entity.Pagamento;
import br.com.fiap.soat07.clean.core.domain.enumeration.MetodoPagamentoEnum;
import br.com.fiap.soat07.clean.core.domain.enumeration.PagamentoStatusEnum;
import br.com.fiap.soat07.clean.core.domain.enumeration.ProvedorPagamentoEnum;
import br.com.fiap.soat07.clean.core.gateway.PagamentoGateway;

import java.math.BigDecimal;
import java.util.Random;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class MercadoPagoPagamentoGateway implements PagamentoGateway {


	@Override
	public PagamentoStatusEnum getSituacao(Pagamento pagamento) {
		if (pagamento == null)
			throw new IllegalArgumentException("Obrigatório pedido");
		if (pagamento.getId() != null && !pagamento.getId().isEmpty())
			return PagamentoStatusEnum.NAO_ENCONTRADO;
		return PagamentoStatusEnum.NAO_CONCLUIDO;
	}

	@Override
	public Pagamento create(Pedido pedido) {
		Pagamento pagamento = new Pagamento(pedido);
		pagamento.setPedidoId(pedido.getId());

		// ACESSO MERCADO PAGO
		String transactionCode = UUID.randomUUID().toString();
		pagamento.setId(transactionCode);
		pagamento.setProvedorServico(ProvedorPagamentoEnum.MERCADO_PAGO);

		return pagamento;
	}


	/**
	 * Mock
	 * @param pagamento
	 * @return QRCode para pagamento
	 */
	@Override
	public String getQRCode(Pagamento pagamento) {
		return "";
	}

}
