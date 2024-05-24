package br.com.fiap.soat07.techchallenge01.adapter.in.rest.dto.mapper;

import org.mapstruct.Mapper;

import br.com.fiap.soat07.techchallenge01.adapter.in.rest.dto.PedidoDTO;
import br.com.fiap.soat07.techchallenge01.application.domain.entity.Pedido;

@Mapper (componentModel = "spring")
public interface PedidoMapper {
	
	/**
	 * To domain mapper
	 * @param pedidoModel
	 * @return {@link Pedido}
	 */
	Pedido toDomain(PedidoDTO pedidoModel);
	
	/**
	 * To model mapper
	 * @param pedido
	 * @return {@link PedidoDTO}
	 */
	PedidoDTO toDTO(Pedido pedido);

}
