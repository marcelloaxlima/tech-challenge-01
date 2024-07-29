package br.com.fiap.soat07.clean.infra.repository.mysql;

import br.com.fiap.soat07.clean.core.domain.entity.Cliente;
import br.com.fiap.soat07.clean.core.domain.entity.Combo;
import br.com.fiap.soat07.clean.core.domain.entity.Produto;
import br.com.fiap.soat07.clean.core.exception.ClienteNotFoundException;
import br.com.fiap.soat07.clean.core.exception.ComboNotFoundException;
import br.com.fiap.soat07.clean.core.gateway.ClienteGateway;
import br.com.fiap.soat07.clean.core.gateway.ComboGateway;
import br.com.fiap.soat07.clean.infra.repository.mysql.mapper.ComboRepositoryMapper;
import br.com.fiap.soat07.clean.infra.repository.mysql.mapper.ProdutoRepositoryMapper;
import br.com.fiap.soat07.clean.infra.repository.mysql.model.ClienteModel;
import br.com.fiap.soat07.clean.infra.repository.mysql.model.ComboModel;
import br.com.fiap.soat07.clean.infra.repository.mysql.model.ProdutoModel;
import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
import jakarta.persistence.PersistenceContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.*;

@Repository
public class ComboRepository implements ComboGateway {

	@PersistenceContext
	private EntityManager entityManager;

	@Autowired
	private ClienteRepository clienteRepository;

	@Autowired
	private ProdutoRepository produtoRepository;

	@Autowired
	private ComboRepositoryMapper comboMapper;


	/**
	 * Find by id
	 * @param id {@link Long}
	 * @return {@link Optional<ComboModel>}
	 */
	Optional<ComboModel> _findById(long id) {
		String hql = """
            SELECT c
            FROM ComboModel c
            WHERE c.id = :comboId
            """;

		try {
			ComboModel model = (ComboModel) entityManager.createQuery(hql, ComboModel.class)
					.setParameter("comboId", id)
					.getSingleResult();

			return Optional.of(model);
		} catch (NoResultException e) {
			return Optional.empty();
		}
	}


	/**
	 * Find by id
	 * @param id {@link Long}
	 * @return {@link Optional< Combo >}
	 */
	@Override
	public Optional<Combo> findById(long id) {
		return _findById(id).map(model -> comboMapper.toDomain(model));
	}

	@Override
	public Combo save(Combo combo) {
		ClienteModel clienteModel = clienteRepository._findById(combo.getCliente().getId()).orElseThrow(() -> new ClienteNotFoundException(combo.getCliente().getId()));
		Set<ProdutoModel> produtosModel = new HashSet<>(produtoRepository._find(combo.getProdutos()));

		ComboModel model = null;
		if (combo.getId() == null) {
			model = new ComboModel();

			model.setCliente(clienteModel);
			model.setNome(combo.getNome());
			model.setUltimaModificacao(combo.getUltimaModificacao());
			model.setProdutos(produtosModel);

			entityManager.persist(model);

		} else {
			model = _findById(combo.getId()).orElseThrow(() -> new ComboNotFoundException(combo.getId()));
			model.setCliente(clienteModel);
			model.setNome(combo.getNome());
			model.setUltimaModificacao(combo.getUltimaModificacao());
			model.setProdutos(produtosModel);

			entityManager.merge(model);
		}

		return comboMapper.toDomain(model);
	}

	@Override
	public void delete(Combo combo) {
		ComboModel model = _findById(combo.getId()).orElseThrow(() -> new ComboNotFoundException(combo.getId()));

		entityManager.remove(model);
	}

	@Override
	public void deleteProdutosOfCombo(Combo combo, Produto produto) {
		String deleteQuery = "DELETE FROM combo_produtos WHERE comboid = :comboId AND produtoId IN ( :produtoId )";

		entityManager.createNativeQuery(deleteQuery)
				.setParameter("comboId", combo.getId())
				.setParameter("produtoId", produto.getId())
				.executeUpdate();
		entityManager.flush();
	}

	@Override
	public Collection<Combo> find(int pageNumber, int pageSize) {
		String hql = """
            SELECT c
            FROM ComboModel c
            WHERE 1 = 1
            """;

		pageNumber = Math.max(pageNumber, 1);
		pageSize = Math.max(pageSize, 1);
		int firstResult = (pageNumber - 1) * pageSize;

		List<ComboModel> result = entityManager.createQuery(hql, ComboModel.class)
				.setFirstResult(firstResult)
				.setMaxResults(pageSize)
				.getResultList();

		return result.stream().map(model -> comboMapper.toDomain(model)).toList();
	}

}
