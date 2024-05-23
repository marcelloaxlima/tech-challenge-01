package br.com.fiap.soat07.techchallenge01.infra.repository.mapper;

import org.mapstruct.Mapper;

import br.com.fiap.soat07.techchallenge01.domain.entity.Pedido;
import br.com.fiap.soat07.techchallenge01.infra.repository.model.PedidoModel;

@Mapper (componentModel = "spring")
public interface PedidoRepositoryMapper {
	
	/**
	 * To domain mapper
	 * @param pedidoModel
	 * @return {@link Pedido}
	 */
	Pedido toDomain(PedidoModel pedidoModel);
	
	/**
	 * To model mapper
	 * @param pedido
	 * @return {@link PedidoModel}
	 */
	PedidoModel toModel(Pedido pedido);

}
