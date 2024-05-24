package br.com.fiap.soat07.techchallenge01.adapter.out.persistence.mysql.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.beans.factory.annotation.Autowired;

import br.com.fiap.soat07.techchallenge01.application.domain.entity.Combo;
import br.com.fiap.soat07.techchallenge01.adapter.out.persistence.mysql.model.ComboModel;

@Mapper (componentModel = "spring")
public abstract class ComboRepositoryMapper {
	
	@Autowired
    public ProdutoRepositoryMapper produtoMapper;
	
	/**
	 * To domain mapper
	 * @param comboModel
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
