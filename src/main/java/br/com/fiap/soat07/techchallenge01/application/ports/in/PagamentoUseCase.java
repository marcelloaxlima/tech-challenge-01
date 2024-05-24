package br.com.fiap.soat07.techchallenge01.application.ports.in;

import br.com.fiap.soat07.techchallenge01.application.domain.entity.Pagamento;

public interface PagamentoUseCase {
	
	
	
	/**
	 * Get by id
	 * @param pagamento {@link Pagamento}
	 * @return {@link Pagamento}
	 */
	Pagamento executar(Pagamento pagamento);
	
	

}
