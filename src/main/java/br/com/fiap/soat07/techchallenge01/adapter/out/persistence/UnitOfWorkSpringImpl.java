package br.com.fiap.soat07.techchallenge01.adapter.out.persistence;

import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.DefaultTransactionDefinition;

import br.com.fiap.soat07.techchallenge01.application.ports.out.persistence.UnitOfWork;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class UnitOfWorkSpringImpl implements UnitOfWork {
	
	private TransactionStatus transactionStatus;
	
	private final PlatformTransactionManager platformTransactionManager;

	@Override
	public void begin() {
		transactionStatus = platformTransactionManager.getTransaction(new DefaultTransactionDefinition());
	}

	@Override
	public void commit() {
		if (null != transactionStatus) {
			platformTransactionManager.commit(transactionStatus);
		}
	}

	@Override
	public void rollback() {
		if (null != transactionStatus) {
			platformTransactionManager.rollback(transactionStatus);
		}
	}

}
