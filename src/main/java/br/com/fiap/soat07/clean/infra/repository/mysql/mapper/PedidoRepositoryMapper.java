package br.com.fiap.soat07.clean.infra.repository.mysql.mapper;

import br.com.fiap.soat07.clean.core.domain.entity.Pagamento;
import org.mapstruct.Mapper;

import br.com.fiap.soat07.clean.core.domain.entity.Pedido;
import br.com.fiap.soat07.clean.infra.repository.mysql.model.PedidoModel;
import org.mapstruct.Mapping;

@Mapper (componentModel = "spring")
public interface PedidoRepositoryMapper {
	
	/**
	 * To domain mapper
	 * @param pedidoModel
	 * @return {@link Pedido}
	 */
	Pedido toDomain(PedidoModel pedidoModel);

	/**
	 * To domain mapper
	 * @param pedidoModel PedidoModel
	 * @return {@link Pagamento}
	 */
	@Mapping(target = "id", source = "pedidoModel.transactionCode")
	@Mapping(target = "pedidoId", source = "pedidoModel.id")
	@Mapping(target = "provedorServico", source = "pedidoModel.provedor")
	@Mapping(target = "metodoPagamento", source = "pedidoModel.metodo")
	@Mapping(target = "data", source = "pedidoModel.transactionTime")
	@Mapping(target = "status", source = "pedidoModel.pagamentoStatus")
	Pagamento toDomainPagamento(PedidoModel pedidoModel);

	/**
	 * To model mapper
	 * @param pedido
	 * @return {@link PedidoModel}
	 */
	PedidoModel toModel(Pedido pedido);

}
