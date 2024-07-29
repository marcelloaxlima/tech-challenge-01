package br.com.fiap.soat07.clean.infra.rest.mapper;

import org.mapstruct.Mapper;

import br.com.fiap.soat07.clean.infra.rest.dto.ClienteDTO;
import br.com.fiap.soat07.clean.core.domain.entity.Cliente;
import org.mapstruct.Mapping;

@Mapper (componentModel = "spring")
public interface ClienteMapper {
	
	/**
	 * To domain mapper
	 * @param clienteDTO ClienteDTO
	 * @return {@link ClienteDTO}
	 */
	Cliente toDomain(ClienteDTO clienteDTO);
	
	/**
	 * To model mapper
	 * @param cliente Cliente
	 * @return {@link ClienteDTO}
	 */
	ClienteDTO toDTO(Cliente cliente);

}
