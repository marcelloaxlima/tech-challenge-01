package br.com.fiap.soat07.clean.infra.repository.mysql.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.beans.factory.annotation.Autowired;

import br.com.fiap.soat07.clean.core.domain.entity.Combo;
import br.com.fiap.soat07.clean.infra.repository.mysql.model.ComboModel;

@Mapper (componentModel = "spring")
public abstract class ComboRepositoryMapper {
	
	@Autowired
    public ProdutoRepositoryMapper produtoMapper;
	
	/**
	 * To domain mapper
	 * @param comboModel Combo
	 * @return {@link Combo}
	 */
	@Mapping(target = "produtos", defaultExpression = "java(produtoMapper.toDomain(source.getProdutos()))")
	public abstract Combo toDomain(ComboModel comboModel);
	
	/**
	 * To model mapper
	 * @param combo
	 * @return {@link ComboModel}
	 */
	public abstract ComboModel toModel(Combo combo);

}
