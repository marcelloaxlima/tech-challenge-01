package br.com.fiap.soat07.techchallenge01.infra.repository;


import java.util.List;

public interface CustomPedidoProdutosRepository {
	
	void delete (long pedidoid, List<Long> produtoIds);
	
	List<Long> getProdutosByPedidoId(long pedidoid);

}
