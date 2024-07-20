package br.com.fiap.soat07.clean.core.gateway;

import br.com.fiap.soat07.clean.core.domain.entity.Pagamento;
import br.com.fiap.soat07.clean.core.domain.entity.Pedido;
import br.com.fiap.soat07.clean.core.domain.enumeration.MetodoPagamentoEnum;
import br.com.fiap.soat07.clean.core.domain.enumeration.PagamentoStatusEnum;

import java.math.BigDecimal;
import java.util.Optional;

public interface PagamentoGateway {

	/**
	 * Executar pagamento
	 * @param pagamento {@link Pagamento}
	 * @return {@link PagamentoStatusEnum}
	 */
	PagamentoStatusEnum executar(Pagamento pagamento);

	PagamentoStatusEnum getSituacao(String codigo);

	String getTransaction(MetodoPagamentoEnum metodo, BigDecimal valor);
}
