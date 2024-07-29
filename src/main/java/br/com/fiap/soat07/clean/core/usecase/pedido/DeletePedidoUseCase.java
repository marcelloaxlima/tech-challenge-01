package br.com.fiap.soat07.clean.core.usecase.pedido;

import br.com.fiap.soat07.clean.core.domain.entity.Combo;
import br.com.fiap.soat07.clean.core.domain.entity.Pedido;
import br.com.fiap.soat07.clean.core.gateway.ComboGateway;
import br.com.fiap.soat07.clean.core.gateway.PedidoGateway;

public class DeletePedidoUseCase {

    private final PedidoGateway pedidoGateway;

    public DeletePedidoUseCase(final PedidoGateway pedidoGateway) {
        this.pedidoGateway = pedidoGateway;
    }

    public void execute(final Pedido pedido) {
        if (pedido == null)
            throw new IllegalArgumentException();

        pedidoGateway.delete(pedido);
    }

}
