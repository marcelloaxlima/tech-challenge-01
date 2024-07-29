package br.com.fiap.soat07.clean.core.exception;

public class ComboNotFoundException extends ResourceNotFoundException {

    private static final long serialVersionUID = 1369928619481103297L;
	
    private static final String MESSAGE = "Não foi encontrado um combo com o Id:%d";
    public ComboNotFoundException(long id ) {
        super(String.format(MESSAGE, id));
    }

}
