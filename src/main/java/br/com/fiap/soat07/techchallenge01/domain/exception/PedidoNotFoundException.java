package br.com.fiap.soat07.techchallenge01.domain.exception;

public class PedidoNotFoundException extends ResourceNotFoundException {

	private static final long serialVersionUID = -4893287016150443440L;
	
	private static final String MESSAGE = "Não foi encontrado um pedido com o Id:%d";
    public PedidoNotFoundException(long id ) {
        super(String.format(MESSAGE, id));
    }

}
