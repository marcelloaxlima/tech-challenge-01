package br.com.fiap.soat07.clean.core.usecase.cliente;

import br.com.fiap.soat07.clean.core.domain.entity.Cliente;
import br.com.fiap.soat07.clean.core.gateway.ClienteGateway;
import br.com.fiap.soat07.clean.infra.rest.dto.ClienteDTO;

public class UpdateClienteUseCase {

    private final ClienteGateway clienteGateway;

    public UpdateClienteUseCase(ClienteGateway clienteGateway) {
        this.clienteGateway = clienteGateway;
    }

    public Cliente execute(Cliente cliente, ClienteDTO clienteDTO) {

        if (clienteDTO.getId() == 0)
            throw new IllegalArgumentException("Obrigatório informar o código do cliente");

        cliente.setCodigo(cliente.getCodigo());
        cliente.setNome(cliente.getNome());
        cliente.setCpf(cliente.getCpf());

        clienteGateway.save(cliente);
        return cliente;
    }

}
