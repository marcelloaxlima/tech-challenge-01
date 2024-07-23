package br.com.fiap.soat07.clean.infra.service;

import br.com.fiap.soat07.clean.core.gateway.ClienteGateway;
import br.com.fiap.soat07.clean.core.usecase.cliente.CreateClienteUseCase;
import br.com.fiap.soat07.clean.core.usecase.cliente.DeleteClienteUseCase;
import br.com.fiap.soat07.clean.core.usecase.cliente.SearchClienteUseCase;
import br.com.fiap.soat07.clean.core.usecase.cliente.UpdateClienteUseCase;
import br.com.fiap.soat07.clean.infra.repository.mysql.ClienteRepository;
import org.springframework.stereotype.Component;

@Component
public class ClienteService {
    private final ClienteGateway clienteGateway;

    private CreateClienteUseCase createClienteUseCase;
    private DeleteClienteUseCase deleteClienteUseCase;
    private UpdateClienteUseCase updateClienteUseCase;
    private SearchClienteUseCase searchClienteUseCase;

    public ClienteService(final ClienteRepository clienteGateway) {
        this.clienteGateway = clienteGateway;
    }

    public CreateClienteUseCase getCreateClienteUseCase() {
        if (createClienteUseCase == null)
            this.createClienteUseCase = new CreateClienteUseCase(clienteGateway);
        return createClienteUseCase;
    }

    public DeleteClienteUseCase getDeleteClienteUseCase() {
        if (deleteClienteUseCase == null)
            this.deleteClienteUseCase = new DeleteClienteUseCase(clienteGateway);
        return deleteClienteUseCase;
    }

    public UpdateClienteUseCase getUpdateClienteUseCase() {
        if (updateClienteUseCase == null)
            this.updateClienteUseCase = new UpdateClienteUseCase(clienteGateway);
        return updateClienteUseCase;
    }

    public SearchClienteUseCase getSearchClienteUseCase() {
        if (searchClienteUseCase == null)
            this.searchClienteUseCase = new SearchClienteUseCase(clienteGateway);
        return searchClienteUseCase;
    }
}
