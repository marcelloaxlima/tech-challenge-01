package br.com.fiap.soat07.techchallenge01.domain.exception;

public class ComboNotFoundException extends RuntimeException {

    private final Long id;

    public ComboNotFoundException(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }
}
