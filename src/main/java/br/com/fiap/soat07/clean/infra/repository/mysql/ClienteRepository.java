package br.com.fiap.soat07.clean.infra.repository.mysql;

import br.com.fiap.soat07.clean.core.domain.entity.Cliente;
import br.com.fiap.soat07.clean.core.exception.ClienteNotFoundException;
import br.com.fiap.soat07.clean.core.gateway.ClienteGateway;
import br.com.fiap.soat07.clean.infra.repository.mysql.mapper.ClienteRepositoryMapper;
import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
import jakarta.persistence.PersistenceContext;

import br.com.fiap.soat07.clean.infra.repository.mysql.model.ClienteModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

@Repository
public class ClienteRepository implements ClienteGateway {

    @PersistenceContext
    private EntityManager entityManager;

    @Autowired
    private ClienteRepositoryMapper mapper;

    /**
     * Find by id
     * @param id {@link Long}
     * @return {@link Optional<Cliente>}
     */
    Optional<ClienteModel> _findById(Long id) {
        String hql = """
            SELECT c
            FROM ClienteModel c
            WHERE c.id = :clienteId
            """;

        try {
            ClienteModel model = (ClienteModel) entityManager.createQuery(hql, ClienteModel.class)
                    .setParameter("clienteId", id)
                    .getSingleResult();

            return Optional.of(model);
        } catch (NoResultException e) {
            return Optional.empty();
        }
    }

    /**
     * Find by id
     * @param id {@link Long}
     * @return {@link Optional<Cliente>}
     */
    @Override
    public Optional<Cliente> findById(Long id) {
        return _findById(id).map(c -> mapper.toDomain(c));
    }

    /**
     * Find by id
     * @param cpf {@link String}
     * @return {@link Optional<Cliente>}
     */
    @Override
    public Optional<Cliente> findByCpf(String cpf) {
        String hql = """
            SELECT c
            FROM ClienteModel c
            WHERE c.cpf = :cpf
            """;

        try {
            ClienteModel model = (ClienteModel) entityManager.createQuery(hql, ClienteModel.class)
                    .setParameter("cpf", cpf)
                    .getSingleResult();

            Cliente domain = mapper.toDomain(model);
            return Optional.of(domain);
        } catch (NoResultException e) {
            return Optional.empty();
        }
    }

    /**
     * Find by id
     * @param codigo {@link String}
     * @return {@link Optional<Cliente>}
     */
    @Override
    public Optional<Cliente> findByCodigo(String codigo) {
        String hql = """
            SELECT c
            FROM ClienteModel c
            WHERE c.codigo = :codigo
            """;

        try {
            ClienteModel model = (ClienteModel) entityManager.createQuery(hql, ClienteModel.class)
                    .setParameter("codigo", codigo)
                    .getSingleResult();

            Cliente domain = mapper.toDomain(model);
            return Optional.of(domain);
        } catch (NoResultException e) {
            return Optional.empty();
        }
    }

    @Override
    public Cliente save(Cliente cliente) {
        Optional<ClienteModel> optional = _findById(cliente.getId());
        ClienteModel model = null;
        if (optional.isEmpty()) {
            model = new ClienteModel();
            model.setCodigo(cliente.getCodigo());
            model.setCpf(cliente.getCpf());
            model.setNome(cliente.getNome());
            model.setDataCriacao(cliente.getDataCriacao());
            model.setUltimaModificacao(cliente.getUltimaModificacao());

            entityManager.persist(model);
        } else {
            model = optional.get();
            model.setCodigo(cliente.getCodigo());
            model.setCpf(cliente.getCpf());
            model.setNome(cliente.getNome());
            model.setUltimaModificacao(cliente.getUltimaModificacao());
            model.setDataExclusao(cliente.getDataExclusao());

            entityManager.merge(model);
        }

        return mapper.toDomain(model);
    }

    @Override
    public void delete(Cliente cliente) {
        ClienteModel model = _findById(cliente.getId()).orElseThrow(() -> new ClienteNotFoundException(cliente.getId()));

        entityManager.remove(model);
    }

    @Override
    public Collection<Cliente> find(Integer pageNumber, Integer pageSize) {
        String hql = """
            SELECT c
            FROM ClienteModel c
            WHERE 1 = 1
            """;
        pageNumber = Math.max(pageNumber, 1);
        pageSize = Math.max(pageSize, 1);
        int firstResult = (pageNumber - 1) * pageSize;

        List<ClienteModel> result = entityManager.createQuery(hql)
                .setFirstResult(firstResult)
                .setMaxResults(pageSize)
                .getResultList();

        return result.stream().map(model -> mapper.toDomain(model)).toList();
    }

}
