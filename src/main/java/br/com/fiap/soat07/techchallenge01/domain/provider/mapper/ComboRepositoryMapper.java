package br.com.fiap.soat07.techchallenge01.domain.provider.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.beans.factory.annotation.Autowired;

import br.com.fiap.soat07.techchallenge01.domain.entity.Combo;
import br.com.fiap.soat07.techchallenge01.infra.repository.model.ComboModel;

@Mapper (componentModel = "spring")

public interface ComboRepositoryMapper {
	

	
	/**
	 * To domain mapper
	 * @param comboModel
	 * @return {@link Combo}
	 */
	//@Mapping(target = "id", source = "person.id", 
	//	      defaultExpression = "java(java.util.UUID.randomUUID().toString())")
	Combo toDomain(ComboModel comboModel);
	
	/**
	 * To model mapper
	 * @param combo
	 * @return {@link ComboModel}
	 */
	ComboModel toModel(Combo combo);

}
