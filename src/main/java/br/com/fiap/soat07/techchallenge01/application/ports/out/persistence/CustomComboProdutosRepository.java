package br.com.fiap.soat07.techchallenge01.application.ports.out.persistence;


import java.util.List;

import br.com.fiap.soat07.techchallenge01.adapter.out.persistence.mysql.model.ProdutoModel;

public interface CustomComboProdutosRepository {
	
	void delete (long comboId, List<Long> produtoIds);
	
	List<ProdutoModel> getProdutosByComboId(long comboId);

}
