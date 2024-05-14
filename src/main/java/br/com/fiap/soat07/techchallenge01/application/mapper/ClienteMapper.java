package br.com.fiap.soat07.techchallenge01.application.mapper;

import org.mapstruct.Mapper;

import br.com.fiap.soat07.techchallenge01.application.model.dto.ClienteDTO;
import br.com.fiap.soat07.techchallenge01.domain.entity.Cliente;

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
