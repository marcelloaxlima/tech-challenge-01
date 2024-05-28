package br.com.fiap.soat07.techchallenge01.application.exception;

public class ProdutoDuplicadoComboException extends BusinessException {	
	
	private static final long serialVersionUID = -5689357819110848695L;
	
	private static final String MESSAGE = "Não é permitido adicionar produtos do mesmo tipo no Combo";
    public ProdutoDuplicadoComboException() {
        super(MESSAGE);
    }
}
