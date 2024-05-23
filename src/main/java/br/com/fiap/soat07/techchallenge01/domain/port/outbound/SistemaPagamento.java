package br.com.fiap.soat07.techchallenge01.domain.port.outbound;

import br.com.fiap.soat07.techchallenge01.domain.entity.Pagamento;
import br.com.fiap.soat07.techchallenge01.domain.enumeration.PagamentoStatusEnum;

public interface SistemaPagamento {
	/**
	 * Executar pagamento
	 * @param pagamento {@link Pagamento}
	 * @return {@link PagamentoStatusEnum}
	 */
	PagamentoStatusEnum executar(Pagamento pagamento);
	
}
