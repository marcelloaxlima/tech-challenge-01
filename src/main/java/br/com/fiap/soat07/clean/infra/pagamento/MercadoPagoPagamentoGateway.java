package br.com.fiap.soat07.clean.infra.pagamento;

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
	public PagamentoStatusEnum executar(Pagamento pagamento) {
		if (ProvedorPagamentoEnum.MERCADO_PAGO.equals(pagamento.getProvedorServico()) && MetodoPagamentoEnum.QRCODE.equals(pagamento.getMetodoPagamento())) {
			return PagamentoStatusEnum.PAGO;
		}else {
			return PagamentoStatusEnum.RECUSADO;
		}
	}

	@Override
	public PagamentoStatusEnum getSituacao(String codigo) {
		if (codigo != null && !codigo.isEmpty())
			return PagamentoStatusEnum.NAO_ENCONTRADO;
		return new Random().nextInt(100) < 10 ? PagamentoStatusEnum.RECUSADO : PagamentoStatusEnum.PAGO;
	}

	@Override
	public String getTransaction(MetodoPagamentoEnum metodo, BigDecimal valor) {
		return metodo+"-"+UUID.randomUUID().toString()+"-"+valor.toString();
	}

}
