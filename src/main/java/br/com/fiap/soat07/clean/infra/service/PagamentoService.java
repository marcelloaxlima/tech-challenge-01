package br.com.fiap.soat07.clean.infra.service;

import br.com.fiap.soat07.clean.core.gateway.PagamentoGateway;
import br.com.fiap.soat07.clean.core.gateway.PedidoGateway;
import br.com.fiap.soat07.clean.core.usecase.pagamento.CreatePagamentoUseCase;
import br.com.fiap.soat07.clean.core.usecase.pagamento.RetornoGatewayPagamentoUseCase;
import br.com.fiap.soat07.clean.core.usecase.pagamento.SearchPagamentoUseCase;
import br.com.fiap.soat07.clean.core.usecase.pagamento.UpdatePagamentoUseCase;
import org.springframework.stereotype.Component;

@Component
public class PagamentoService {

    private final CreatePagamentoUseCase createPagamentoUseCase;
    private final RetornoGatewayPagamentoUseCase retornoGatewayPagamentoUseCase;
    private final SearchPagamentoUseCase searchPagamentoUseCase;
    private final UpdatePagamentoUseCase updatePagamentoUseCase;

    public PagamentoService(final PedidoGateway pedidoGateway, final PagamentoGateway pagamentoGateway) {
        this.createPagamentoUseCase = new CreatePagamentoUseCase(pedidoGateway, pagamentoGateway);
        this.retornoGatewayPagamentoUseCase = new RetornoGatewayPagamentoUseCase(pagamentoGateway);
        this.searchPagamentoUseCase = new SearchPagamentoUseCase(pedidoGateway);
        this.updatePagamentoUseCase = new UpdatePagamentoUseCase(pedidoGateway);
    }

    public CreatePagamentoUseCase getCreatePagamentoUseCase() {
        return createPagamentoUseCase;
    }

    public RetornoGatewayPagamentoUseCase getRetornoGatewayPagamentoUseCase() {
        return retornoGatewayPagamentoUseCase;
    }

    public SearchPagamentoUseCase getSearchPagamentoUseCase() {
        return searchPagamentoUseCase;
    }

    public UpdatePagamentoUseCase getUpdatePagamentoUseCase() {
        return updatePagamentoUseCase;
    }
}
