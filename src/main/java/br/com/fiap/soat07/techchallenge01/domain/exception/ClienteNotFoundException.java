package br.com.fiap.soat07.techchallenge01.domain.exception;

public class ClienteNotFoundException extends ResourceNotFoundException {

    private static final long serialVersionUID = 1369928619481103297L;
	
    private static final String MESSAGE = "Não foi encontrado um cliente com o Id:%d";
    public ClienteNotFoundException(long id ) {
        super(String.format(MESSAGE, id));
    }

}
