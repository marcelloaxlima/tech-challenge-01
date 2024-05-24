package br.com.fiap.soat07.techchallenge01.adapter.out.persistence.mysql.mapper;

import org.mapstruct.Mapper;

import br.com.fiap.soat07.techchallenge01.application.domain.entity.Cliente;
import br.com.fiap.soat07.techchallenge01.adapter.out.persistence.mysql.model.ClienteModel;

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
