package br.com.fiap.soat07.clean.core.usecase.pedido;

import br.com.fiap.soat07.clean.core.domain.entity.Combo;
import br.com.fiap.soat07.clean.core.domain.entity.Pedido;
import br.com.fiap.soat07.clean.core.domain.entity.Produto;
import br.com.fiap.soat07.clean.core.domain.enumeration.PedidoStatusEnum;
import br.com.fiap.soat07.clean.core.exception.ComboNotFoundException;
import br.com.fiap.soat07.clean.core.exception.PedidoDuplicadoComboException;
import br.com.fiap.soat07.clean.core.exception.ProdutoDuplicadoComboException;
import br.com.fiap.soat07.clean.core.gateway.ComboGateway;
import br.com.fiap.soat07.clean.core.gateway.PedidoGateway;
import br.com.fiap.soat07.clean.infra.repository.mysql.model.ClienteModel;
import br.com.fiap.soat07.clean.infra.repository.mysql.model.ComboModel;
import br.com.fiap.soat07.clean.infra.repository.mysql.model.PedidoModel;
import br.com.fiap.soat07.clean.infra.repository.mysql.model.ProdutoModel;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

import static br.com.fiap.soat07.clean.Utils.hasProdutoDuplicates;

public class CreatePedidoUseCase {
	private final PedidoGateway pedidoGateway;

	public CreatePedidoUseCase(PedidoGateway pedidoGateway) {
		this.pedidoGateway = pedidoGateway;
	}


	/**
	 * Create new Pedido com base num Combo
	 * @param combo Combo
	 * @return {@link Combo}
	 */
	public Pedido execute(Combo combo) {
		if (pedidoGateway.findByCombo(combo.getId()).isPresent())
			throw new PedidoDuplicadoComboException();

		if (hasProdutoDuplicates(combo.getProdutos()))
			throw new ProdutoDuplicadoComboException();

		Pedido pedido = new Pedido();
		pedido.setCombo(combo);
		pedido.setNomeCliente(combo.getCliente().getNome());
		pedido.setStatus(PedidoStatusEnum.INICIADO);
		for (Produto produto : combo.getProdutos())
			pedido.getProdutos().add(produto);

		pedido = pedidoGateway.save(pedido);

		return pedido;
	}

}
