package br.com.fiap.soat07.clean.core.exception;

import java.io.Serial;

public class ClienteNotFoundException extends ResourceNotFoundException {

    @Serial
    private static final long serialVersionUID = 1369928619481103297L;
	
    private static final String MESSAGE = "Não foi encontrado um cliente com o Id:%d";
    private static final String MESSAGE_CPF = "Não foi encontrado um cliente com o CPF:%s";

    public ClienteNotFoundException(long id ) {
        super(String.format(MESSAGE, id));
    }

    public ClienteNotFoundException(String cpf) {
        super(String.format(MESSAGE_CPF, cpf));
    }

}
