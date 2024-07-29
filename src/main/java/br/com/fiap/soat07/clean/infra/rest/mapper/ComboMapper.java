package br.com.fiap.soat07.clean.infra.rest.mapper;

import org.mapstruct.Mapper;

import br.com.fiap.soat07.clean.infra.rest.dto.ComboDTO;
import br.com.fiap.soat07.clean.core.domain.entity.Combo;

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
