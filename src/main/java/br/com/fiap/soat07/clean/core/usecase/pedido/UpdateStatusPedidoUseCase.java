package br.com.fiap.soat07.clean.core.usecase.pedido;

import br.com.fiap.soat07.clean.core.domain.entity.Pedido;
import br.com.fiap.soat07.clean.core.domain.enumeration.PedidoStatusEnum;
import br.com.fiap.soat07.clean.core.exception.PedidoSituacaoInvalidaException;
import br.com.fiap.soat07.clean.core.gateway.PedidoGateway;

public class UpdateStatusPedidoUseCase {

    private final PedidoGateway pedidoGateway;

    public UpdateStatusPedidoUseCase(PedidoGateway pedidoGateway) {
        this.pedidoGateway = pedidoGateway;
    }

    public Pedido execute(Pedido pedido, PedidoStatusEnum status) {

        if (pedido.getStatus().isCancelado())
            throw new PedidoSituacaoInvalidaException("Pedido está cancelado");
        if (pedido.getStatus().isFinalizado())
            throw new PedidoSituacaoInvalidaException("Pedido está finalizado");

        if (pedido.getStatus().getStep() < status.getStep())
            throw new PedidoSituacaoInvalidaException("Pedido está na situação "+pedido.getStatus()+" e não pode ser atualizado para "+status);

        pedido.setStatus(status);
        pedido = pedidoGateway.save(pedido);

        return pedido;
    }

}
