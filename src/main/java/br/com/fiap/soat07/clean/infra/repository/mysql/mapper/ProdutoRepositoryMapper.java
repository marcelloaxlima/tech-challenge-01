package br.com.fiap.soat07.clean.infra.repository.mysql.mapper;

import org.mapstruct.Mapper;

import br.com.fiap.soat07.clean.core.domain.entity.Produto;
import br.com.fiap.soat07.clean.infra.repository.mysql.model.ProdutoModel;

@Mapper (componentModel = "spring")
public interface ProdutoRepositoryMapper {
	
	/**
	 * To domain mapper
	 * @param model
	 * @return {@link Produto}
	 */
	Produto toDomain(ProdutoModel model);
	
	/**
	 * To model mapper
	 * @param produto
	 * @return {@link ProdutoModel}
	 */
	ProdutoModel toModel(Produto produto);

}
