package br.com.fiap.soat07.clean.core.usecase.cliente;

import br.com.fiap.soat07.clean.Utils;
import br.com.fiap.soat07.clean.core.domain.entity.Cliente;
import br.com.fiap.soat07.clean.core.gateway.ClienteGateway;
import br.com.fiap.soat07.clean.infra.rest.dto.ClienteDTO;

import java.time.OffsetDateTime;

public class CreateClienteUseCase {

    private final ClienteGateway clienteGateway;

    public CreateClienteUseCase(final ClienteGateway clienteGateway) {
        this.clienteGateway = clienteGateway;
    }

    public Cliente execute(final ClienteDTO clienteDTO) {
        if (clienteDTO == null)
            throw new IllegalArgumentException();

        if (clienteDTO.getNome() == null || clienteDTO.getNome().isEmpty())
            throw new IllegalArgumentException("Obrigatório o nome");

        // deveria verificar a unicidade do cpf e do código
        // o código deveria ser autoincremental, mas passível de ajuste

        Cliente cliente = new Cliente();
        cliente.setCodigo(clienteDTO.getCodigo());
        cliente.setNome(clienteDTO.getNome());
        cliente.setCpf(clienteDTO.getCpf());
        cliente.setDataCriacao(Utils.now());
        cliente.setUltimaModificacao(cliente.getDataCriacao());

        cliente = clienteGateway.save(cliente);
        return cliente;
    }

}
