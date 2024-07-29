package br.com.fiap.soat07.clean.core.usecase.cliente;

import br.com.fiap.soat07.clean.Utils;
import br.com.fiap.soat07.clean.core.domain.entity.Cliente;
import br.com.fiap.soat07.clean.core.exception.ClienteNotFoundException;
import br.com.fiap.soat07.clean.core.gateway.ClienteGateway;

public class DeleteClienteUseCase {

    private final ClienteGateway clienteGateway;

    public DeleteClienteUseCase(final ClienteGateway clienteGateway) {
        this.clienteGateway = clienteGateway;
    }

    public void execute(final Cliente cliente) {
        if (cliente == null)
            throw new IllegalArgumentException();

        cliente.setDataExclusao(Utils.now());
        clienteGateway.save(cliente);
    }

}
