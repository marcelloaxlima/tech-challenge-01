package br.com.fiap.soat07.techchallenge01.infra.repository.impl;

import java.util.List;

import org.springframework.stereotype.Repository;

import br.com.fiap.soat07.techchallenge01.infra.repository.CustomPedidoProdutosRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;

@Repository
public class CustomPedidoProdutosRepositoryImpl implements CustomPedidoProdutosRepository {
	
	@PersistenceContext
    private EntityManager entityManager;

	@Override
	@Transactional
	public void delete(long pedidoid, List<Long> produtoIds) {
		String deleteQuery = "DELETE FROM pedido_produtos WHERE pedidoid = :pedidoid AND produtoid = :produtoid";
		
		for (Long produtoId:produtoIds) {
		entityManager.createNativeQuery(deleteQuery)
        .setParameter("pedidoid", pedidoid)
        .setParameter("produtoid", produtoId)
        .executeUpdate();
		}
		entityManager.flush();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Long> getProdutosByPedidoId(long pedidoid) {
		String deleteQuery = "SELECT * FROM pedido_produtos WHERE pedidoid = :pedidoid";
		return entityManager.createNativeQuery(deleteQuery, Long.class)
        .setParameter("pedidoid", pedidoid)
        .getResultList();

	}

}
