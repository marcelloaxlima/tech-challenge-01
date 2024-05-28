package br.com.fiap.soat07.techchallenge01.adapter.out.persistence.mysql;

import java.util.List;

import org.springframework.stereotype.Repository;

import br.com.fiap.soat07.techchallenge01.adapter.out.persistence.mysql.model.ProdutoModel;
import br.com.fiap.soat07.techchallenge01.application.ports.out.persistence.CustomComboProdutosRepository;
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
	public List<ProdutoModel> getProdutosByComboId(long comboId) {
		String query = "SELECT produtoid FROM combo_produtos WHERE comboid = :comboid";
		List<Long> produtos = entityManager.createNativeQuery(query, Long.class)
        .setParameter("comboid", comboId)
        .getResultList();

		return produtos.stream().map(produtoId -> entityManager.find(ProdutoModel.class, produtoId)).toList();
	}
}
