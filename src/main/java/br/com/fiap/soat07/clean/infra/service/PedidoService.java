package br.com.fiap.soat07.clean.infra.service;

import br.com.fiap.soat07.clean.core.gateway.ComboGateway;
import br.com.fiap.soat07.clean.core.gateway.PedidoGateway;
import br.com.fiap.soat07.clean.core.usecase.pedido.*;
import br.com.fiap.soat07.clean.infra.repository.mysql.ComboRepository;
import br.com.fiap.soat07.clean.infra.repository.mysql.PedidoRepository;
import org.springframework.stereotype.Component;

@Component
public class PedidoService {

    private final CreatePedidoUseCase createPedidoUseCase;
    private final DeletePedidoUseCase deletePedidoUseCase;
    private final UpdatePedidoUseCase updatePedidoUseCase;
    private final UpdateStatusPedidoUseCase updateStatusPedidoUseCase;
    private final SearchPedidoUseCase searchPedidoUseCase;

    public PedidoService(final ComboRepository comboGateway, final PedidoRepository pedidoGateway) {
        this.createPedidoUseCase = new CreatePedidoUseCase(pedidoGateway);
        this.deletePedidoUseCase = new DeletePedidoUseCase(pedidoGateway);
        this.updatePedidoUseCase = new UpdatePedidoUseCase(pedidoGateway);
        this.updateStatusPedidoUseCase = new UpdateStatusPedidoUseCase(pedidoGateway);
        this.searchPedidoUseCase = new SearchPedidoUseCase(pedidoGateway);
    }

    public CreatePedidoUseCase getCreatePedidoUseCase() {
        return createPedidoUseCase;
    }

    public DeletePedidoUseCase getDeletePedidoUseCase() {
        return deletePedidoUseCase;
    }

    public UpdatePedidoUseCase getUpdatePedidoUseCase() {
        return updatePedidoUseCase;
    }

    public UpdateStatusPedidoUseCase getUpdateStatusPedidoUseCase() {
        return updateStatusPedidoUseCase;
    }

    public SearchPedidoUseCase getSearchPedidoUseCase() {
        return searchPedidoUseCase;
    }
}
