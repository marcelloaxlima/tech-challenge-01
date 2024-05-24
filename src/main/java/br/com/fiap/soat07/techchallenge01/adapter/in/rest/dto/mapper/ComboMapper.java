package br.com.fiap.soat07.techchallenge01.adapter.in.rest.dto.mapper;

import org.mapstruct.Mapper;

import br.com.fiap.soat07.techchallenge01.adapter.in.rest.dto.ComboDTO;
import br.com.fiap.soat07.techchallenge01.application.domain.entity.Combo;

@Mapper (componentModel = "spring")
public interface ComboMapper {
	
	/**
	 * To domain mapper
	 * @param comboModel
	 * @return {@link Combo}
	 */
	Combo toDomain(ComboDTO comboModel);
	
	/**
	 * To model mapper
	 * @param combo
	 * @return {@link ComboDTO}
	 */
	ComboDTO toDTO(Combo combo);

}
