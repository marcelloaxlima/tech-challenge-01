package br.com.fiap.soat07.techchallenge01.application.mapper;

import org.mapstruct.Mapper;

import br.com.fiap.soat07.techchallenge01.application.model.dto.PedidoDTO;
import br.com.fiap.soat07.techchallenge01.domain.entity.Pedido;

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
