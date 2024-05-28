package br.com.fiap.soat07.techchallenge01.application.exception;

public class BusinessException extends RuntimeException {

	private static final long serialVersionUID = 5639956129938097231L;

	public BusinessException(String message) {
        super(message);
    }
}