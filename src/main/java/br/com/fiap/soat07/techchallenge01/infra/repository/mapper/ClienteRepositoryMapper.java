package br.com.fiap.soat07.techchallenge01.infra.repository.mapper;

import org.mapstruct.Mapper;

import br.com.fiap.soat07.techchallenge01.domain.entity.Cliente;
import br.com.fiap.soat07.techchallenge01.infra.repository.model.ClienteModel;

@Mapper (componentModel = "spring")
public interface ClienteRepositoryMapper {
	
	/**
	 * To domain mapper
	 * @param clienteModel
	 * @return {@link Cliente}
	 */
	Cliente toDomain(ClienteModel clienteModel);
	
	/**
	 * To model mapper
	 * @param cliente
	 * @return {@link ClienteModel}
	 */
	ClienteModel toModel(Cliente cliente);

}
