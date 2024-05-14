package br.com.fiap.soat07.techchallenge01.application.mapper;

import org.mapstruct.Mapper;

import br.com.fiap.soat07.techchallenge01.application.model.dto.ProdutoDTO;
import br.com.fiap.soat07.techchallenge01.domain.entity.Produto;

@Mapper (componentModel = "spring")
public interface ProdutoMapper {
	
	/**
	 * To domain mapper
	 * @param itemModel
	 * @return {@link Produto}
	 */
	Produto toDomain(ProdutoDTO itemModel);
	
	/**
	 * To model mapper
	 * @param cliente
	 * @return {@link ProdutoDTO}
	 */
	ProdutoDTO toDTO(Produto item);

}
