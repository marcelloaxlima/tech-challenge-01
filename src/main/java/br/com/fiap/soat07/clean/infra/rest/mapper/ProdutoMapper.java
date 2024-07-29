package br.com.fiap.soat07.clean.infra.rest.mapper;

import org.mapstruct.Mapper;

import br.com.fiap.soat07.clean.infra.rest.dto.ProdutoDTO;
import br.com.fiap.soat07.clean.core.domain.entity.Produto;

@Mapper (componentModel = "spring")
public interface ProdutoMapper {
	
	/**
	 * To domain mapper
	 * @param produtoDTO
	 * @return {@link Produto}
	 */
	Produto toDomain(ProdutoDTO produtoDTO);
	
	/**
	 * To model mapper
	 * @param produto
	 * @return {@link ProdutoDTO}
	 */
	ProdutoDTO toDTO(Produto produto);

}
