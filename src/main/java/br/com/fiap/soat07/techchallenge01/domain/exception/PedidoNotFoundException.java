package br.com.fiap.soat07.techchallenge01.domain.exception;

public class PedidoNotFoundException extends RuntimeException {

    private final Long id;

    public PedidoNotFoundException(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

}
