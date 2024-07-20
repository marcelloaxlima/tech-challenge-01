package br.com.fiap.soat07.clean.core.usecase.pedido;

import br.com.fiap.soat07.clean.Utils;
import br.com.fiap.soat07.clean.core.domain.entity.Pedido;
import br.com.fiap.soat07.clean.core.exception.ComboNotFoundException;
import br.com.fiap.soat07.clean.core.gateway.PedidoGateway;
import br.com.fiap.soat07.clean.infra.rest.dto.PedidoDTO;
import org.springframework.stereotype.Component;

import java.time.OffsetDateTime;

@Component
public class UpdatePedidoUseCase {

    private final PedidoGateway pedidoGateway;

    public UpdatePedidoUseCase(PedidoGateway pedidoGateway) {
        this.pedidoGateway = pedidoGateway;
    }

    public Pedido execute(Pedido pedido, Pedido atualizacoes) {

        pedido.setCodigo(atualizacoes.getCodigo());
        pedido.setNomeCliente(atualizacoes.getNomeCliente());
        pedido.setUltimaModificacao(Utils.now());

        pedidoGateway.save(pedido);
        return pedido;
    }

}
