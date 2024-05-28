package br.com.fiap.soat07.techchallenge01.application.exception;

public class ProdutoDuplicadoComboException extends ResourceNotFoundException {	
	private static final String MESSAGE = "Não é permitido adicionar produtos do mesmo tipo no Combo";
    public ProdutoDuplicadoComboException() {
        super(String.format(MESSAGE, 400));
    }
}
