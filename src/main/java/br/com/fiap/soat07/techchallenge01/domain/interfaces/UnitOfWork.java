package br.com.fiap.soat07.techchallenge01.domain.interfaces;

public interface UnitOfWork {
	void begin();
	void commit();
	void rollback();
}
