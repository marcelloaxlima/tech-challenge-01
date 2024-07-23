package br.com.fiap.soat07.clean.core.domain.value;

public class CPFInvalidoException extends RuntimeException {

    private final String valor;

    public CPFInvalidoException(String valor) {
        this.valor = valor;
    }

    public String getValor() {
        return valor;
    }
}
