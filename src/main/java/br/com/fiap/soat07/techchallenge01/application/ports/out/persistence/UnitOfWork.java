package br.com.fiap.soat07.techchallenge01.application.ports.out.persistence;

public interface UnitOfWork {
	void begin();
	void commit();
	void rollback();
}
