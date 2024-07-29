package br.com.fiap.soat07.clean.core.exception;

public class ProdutoNotFoundException extends ResourceNotFoundException {

    private static final long serialVersionUID = 1369928619481103297L;
	
    private static final String MESSAGE = "Não foi encontrado um produto com o Id:%d";
    public ProdutoNotFoundException(long id ) {
        super(String.format(MESSAGE, id));
    }

}
