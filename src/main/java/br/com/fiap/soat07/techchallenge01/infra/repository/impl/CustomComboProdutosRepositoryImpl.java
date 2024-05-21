package br.com.fiap.soat07.techchallenge01.infra.repository.impl;

import java.util.List;

import org.springframework.stereotype.Repository;

import br.com.fiap.soat07.techchallenge01.infra.repository.CustomComboProdutosRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;

@Repository
public class CustomComboProdutosRepositoryImpl implements CustomComboProdutosRepository {
	
	@PersistenceContext
    private EntityManager entityManager;

	@Override
	@Transactional
	public void delete(long comboid, List<Long> produtoIds) {
		String deleteQuery = "DELETE FROM combo_produtos WHERE comboid = :comboid AND produtoid = :produtoid";
		
		for (Long produtoId:produtoIds) {
		entityManager.createNativeQuery(deleteQuery)
        .setParameter("comboid", comboid)
        .setParameter("produtoid", produtoId)
        .executeUpdate();
		}
		entityManager.flush();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Long> getProdutosByComboId(long comboId) {
		String deleteQuery = "SELECT * FROM combo_produtos WHERE comboid = :comboid";
		return entityManager.createNativeQuery(deleteQuery, Long.class)
        .setParameter("comboid", comboId)
        .getResultList();

	}

}
