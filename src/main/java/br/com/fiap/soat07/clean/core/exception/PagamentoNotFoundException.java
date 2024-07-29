package br.com.fiap.soat07.clean.core.exception;

public class PagamentoNotFoundException extends ResourceNotFoundException {

	private static final long serialVersionUID = -4893287016150443440L;

	private static final String MESSAGE = "Não foi encontrado um pagamento com o Id:%s";
    public PagamentoNotFoundException(String id) {
        super(String.format(MESSAGE, id));
    }

}
