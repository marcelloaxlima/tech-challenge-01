package br.com.fiap.soat07.techchallenge01.application.ports.out.pagamento;

import br.com.fiap.soat07.techchallenge01.application.domain.entity.Pagamento;
import br.com.fiap.soat07.techchallenge01.application.domain.enumeration.PagamentoStatusEnum;

public interface SistemaPagamento {
	/**
	 * Executar pagamento
	 * @param pagamento {@link Pagamento}
	 * @return {@link PagamentoStatusEnum}
	 */
	PagamentoStatusEnum executar(Pagamento pagamento);
	
}
