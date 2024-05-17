package br.com.fiap.soat07.techchallenge01.infra.repository;


import java.util.List;

public interface CustomComboProdutosRepository {
	
	void delete (long comboId, List<Long> produtoIds);
	
	List<Long> getProdutosByComboId(long comboId);

}
