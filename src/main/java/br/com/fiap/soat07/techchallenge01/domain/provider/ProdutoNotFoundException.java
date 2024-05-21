package br.com.fiap.soat07.techchallenge01.domain.provider;

public class ProdutoNotFoundException extends RuntimeException {

    private final Long id;

    public ProdutoNotFoundException(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

}
