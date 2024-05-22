package br.com.fiap.soat07.techchallenge01.infra.repository.mapper;

import org.mapstruct.Mapper;

import br.com.fiap.soat07.techchallenge01.domain.entity.Produto;
import br.com.fiap.soat07.techchallenge01.infra.repository.model.ProdutoModel;

@Mapper (componentModel = "spring")
public interface ProdutoRepositoryMapper {
	
	/**
	 * To domain mapper
	 * @param itemModel
	 * @return {@link Produto}
	 */
	Produto toDomain(ProdutoModel itemModel);
	
	/**
	 * To model mapper
	 * @param cliente
	 * @return {@link ProdutoModel}
	 */
	ProdutoModel toModel(Produto item);

}
