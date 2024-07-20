package br.com.fiap.soat07.clean.infra.service;

import br.com.fiap.soat07.clean.core.gateway.ClienteGateway;
import br.com.fiap.soat07.clean.core.gateway.PagamentoGateway;
import br.com.fiap.soat07.clean.core.gateway.PedidoGateway;
import br.com.fiap.soat07.clean.core.gateway.ProdutoGateway;
import br.com.fiap.soat07.clean.core.usecase.combo.CreateComboUseCase;
import br.com.fiap.soat07.clean.core.usecase.combo.DeleteComboUseCase;
import br.com.fiap.soat07.clean.core.usecase.combo.SearchComboUseCase;
import br.com.fiap.soat07.clean.core.usecase.combo.UpdateComboUseCase;
import br.com.fiap.soat07.clean.core.usecase.pagamento.CreatePagamentoUseCase;
import br.com.fiap.soat07.clean.core.usecase.pagamento.PagamentoUseCase;
import br.com.fiap.soat07.clean.core.usecase.pagamento.RetornoGatewayPagamentoUseCase;
import br.com.fiap.soat07.clean.infra.repository.mysql.ComboRepository;
import org.springframework.stereotype.Component;

@Component
public class PagamentoService {

    private final PagamentoUseCase pagamentoUseCase;
    private final CreatePagamentoUseCase createPagamentoUseCase;
    private final RetornoGatewayPagamentoUseCase retornoGatewayPagamentoUseCase;

    public PagamentoService(final PedidoGateway pedidoGateway, final PagamentoGateway pagamentoGateway) {
        this.createPagamentoUseCase = new CreatePagamentoUseCase(pedidoGateway, pagamentoGateway);
        this.pagamentoUseCase = new PagamentoUseCase(pedidoGateway, pagamentoGateway);
        this.retornoGatewayPagamentoUseCase = new RetornoGatewayPagamentoUseCase(pedidoGateway, pagamentoGateway);
    }

    public CreatePagamentoUseCase getCreatePagamentoUseCase() {
        return createPagamentoUseCase;
    }

    public PagamentoUseCase getPagamentoUseCase() {
        return pagamentoUseCase;
    }

    public RetornoGatewayPagamentoUseCase getRetornoGatewayPagamentoUseCase() {
        return retornoGatewayPagamentoUseCase;
    }


}
