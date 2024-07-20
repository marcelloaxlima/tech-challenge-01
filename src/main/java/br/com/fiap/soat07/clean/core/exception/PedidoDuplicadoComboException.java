package br.com.fiap.soat07.clean.core.exception;

public class PedidoDuplicadoComboException extends BusinessException {

	private static final long serialVersionUID = -5689357819110848695L;

	private static final String MESSAGE = "Não é permitido criar dois pedidos a partir do mesmo combo";
    public PedidoDuplicadoComboException() {
        super(MESSAGE);
    }
}
