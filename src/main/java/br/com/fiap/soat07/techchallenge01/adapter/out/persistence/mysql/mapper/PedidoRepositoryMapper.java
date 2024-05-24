package br.com.fiap.soat07.techchallenge01.adapter.out.persistence.mysql.mapper;

import org.mapstruct.Mapper;

import br.com.fiap.soat07.techchallenge01.application.domain.entity.Pedido;
import br.com.fiap.soat07.techchallenge01.adapter.out.persistence.mysql.model.PedidoModel;

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
