package br.com.fiap.soat07.techchallenge01.domain.usecase;

import br.com.fiap.soat07.techchallenge01.domain.entity.Pagamento;

public interface PagamentoUseCase {
	
	
	
	/**
	 * Get by id
	 * @param pagamento {@link Pagamento}
	 * @return {@link Pagamento}
	 */
	Pagamento executar(Pagamento pagamento);
	
	

}
