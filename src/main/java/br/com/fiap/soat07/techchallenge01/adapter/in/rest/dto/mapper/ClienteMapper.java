package br.com.fiap.soat07.techchallenge01.adapter.in.rest.dto.mapper;

import org.mapstruct.Mapper;

import br.com.fiap.soat07.techchallenge01.adapter.in.rest.dto.ClienteDTO;
import br.com.fiap.soat07.techchallenge01.application.domain.entity.Cliente;

@Mapper (componentModel = "spring")
public interface ClienteMapper {
	
	/**
	 * To domain mapper
	 * @param clienteModel
	 * @return {@link ClienteDTO}
	 */
	Cliente toDomain(ClienteDTO clienteDTO);
	
	/**
	 * To model mapper
	 * @param cliente
	 * @return {@link ClienteDTO}
	 */
	ClienteDTO toDTO(Cliente cliente);

}
