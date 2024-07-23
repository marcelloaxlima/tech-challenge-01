package br.com.fiap.soat07.clean.infra.repository.mysql;

import br.com.fiap.soat07.clean.Utils;
import br.com.fiap.soat07.clean.core.domain.entity.Combo;
import br.com.fiap.soat07.clean.core.domain.entity.Pedido;
import br.com.fiap.soat07.clean.core.domain.entity.Produto;
import br.com.fiap.soat07.clean.core.domain.enumeration.TipoProdutoEnum;
import br.com.fiap.soat07.clean.core.exception.ProdutoNotFoundException;
import br.com.fiap.soat07.clean.core.gateway.ProdutoGateway;
import br.com.fiap.soat07.clean.infra.repository.mysql.mapper.ProdutoRepositoryMapper;
import br.com.fiap.soat07.clean.infra.repository.mysql.model.ComboModel;
import br.com.fiap.soat07.clean.infra.repository.mysql.model.ProdutoModel;
import jakarta.persistence.*;
import org.hibernate.Session;
import org.hibernate.query.NativeQuery;
import org.hibernate.query.Query;
import org.hibernate.query.TupleTransformer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

@Repository
public class ProdutoRepository implements ProdutoGateway {

    @PersistenceContext
    private EntityManager entityManager;

    @Autowired
    private ProdutoRepositoryMapper produtoMapper;


    /**
     * Find by id
     * @param id {@link Long}
     * @return {@link Optional<ProdutoModel>}
     */
    Optional<ProdutoModel> _findById(long id) {
        String hql = """
            SELECT p
            FROM ProdutoModel p
            WHERE p.id = :produtoId
            """;

        try {
            ProdutoModel model = entityManager.createQuery(hql, ProdutoModel.class)
                    .setParameter("produtoId", id)
                    .getSingleResult();

            return Optional.of(model);
        } catch (NoResultException e) {
            return Optional.empty();
        }
    }

    Collection<ProdutoModel> _find(Collection<Produto> produtos) {
        String hql = """
            SELECT p
            FROM ProdutoModel p
            WHERE p.id IN (:produtoIds)
            """;

        try {
            Set<Long> ids = produtos.stream().map(Produto::getId).collect(Collectors.toSet());
            Collection<ProdutoModel> model = entityManager.createQuery(hql, ProdutoModel.class)
                    .setParameter("produtoIds", ids)
                    .getResultList();

            return model;
        } catch (NoResultException e) {
            return Collections.emptyList();
        }
    }

    /**
     * Find by id
     * @param id {@link Long}
     * @return {@link Optional<Produto>}
     */
    public Optional<Produto> findById(long id) {
        return _findById(id).map(model -> produtoMapper.toDomain(model));
    }

    public Produto save(Produto produto) {
        ProdutoModel model = null;
        if (produto.getId() == null) {
            LocalDateTime dataCriacao = Utils.now();
            ProdutoModel produtoModel = ProdutoModel.builder()
                    .codigo(produto.getCodigo())
                    .nome(produto.getNome())
                    .tipoProduto(produto.getTipoProduto())
                    .valor(produto.getValor())
                    .dataCriacao(dataCriacao)
                    .ultimaModificacao(dataCriacao)
                    .build();
            entityManager.persist(model);
        } else {
            model = _findById(produto.getId()).orElseThrow(() -> new ProdutoNotFoundException(produto.getId()));
            model.setCodigo(produto.getCodigo());
            model.setNome(produto.getNome());
            model.setTipoProduto(produto.getTipoProduto());
            model.setValor(produto.getValor());
            model.setUltimaModificacao(produto.getUltimaModificacao());
            model.setDataExclusao(produto.getDataExclusao());
            entityManager.merge(model);
        }

        return produtoMapper.toDomain(model);
    }

    /**
     * Exclusão lógica
     * @param produto
     */
    public void delete(Produto produto) {
        ProdutoModel model = _findById(produto.getId()).orElseThrow(() -> new ProdutoNotFoundException(produto.getId()));

        model.setDataExclusao(Utils.now());

        entityManager.merge(model);
    }

    @Override
    public Collection<Produto> find(TipoProdutoEnum tipo, int pageNumber, int pageSize) {
        String hql = """
            SELECT p
            FROM ProdutoModel p
            WHERE p.tipoProduto = :tipo
              AND p.dataExclusao IS NULL
            """;
        pageNumber = Math.max(pageNumber, 1);
        pageSize = Math.max(pageSize, 1);
        int firstResult = (pageNumber - 1) * pageSize;

        TypedQuery<ProdutoModel> query = entityManager.createQuery(hql, ProdutoModel.class);
        List<ProdutoModel> produtos = query
                .setParameter("tipo", tipo)
                .getResultList();

        return produtos.stream().map(model -> produtoMapper.toDomain(model)).toList();
    }

    @Override
    public Collection<Produto> find(Combo combo) {
        String hql = """
            SELECT p
            FROM ProdutoModel p
            WHERE p.combos.id = :comboId
              AND p.dataExclusao IS NULL
			""";
        List<ProdutoModel> produtos = entityManager.unwrap(Session.class).createQuery(hql, ProdutoModel.class)
                .setParameter("comboId", combo.getId())
                .getResultList();

        return produtos.stream().map(model -> produtoMapper.toDomain(model)).toList();
    }

    @Override
    public Collection<Produto> find(Pedido pedido) {
        String hql = """
            SELECT p
            FROM ProdutoModel p
            WHERE p.pedidos.id = :pedidoId
              AND p.dataExclusao IS NULL
			""";
        List<ProdutoModel> produtos = entityManager.unwrap(Session.class).createQuery(hql, ProdutoModel.class)
                .setParameter("pedidoId", pedido.getId())
                .getResultList();

        return produtos.stream().map(model -> produtoMapper.toDomain(model)).toList();
    }

    @Override
    public Collection<Produto> find(int pageNumber, int pageSize) {
        String hql = """
            SELECT p
            FROM ProdutoModel p
            WHERE 1 = 1
              AND p.dataExclusao IS NULL
            """;
        pageNumber = Math.max(pageNumber, 1);
        pageSize = Math.max(pageSize, 1);
        int firstResult = (pageNumber - 1) * pageSize;

        Query<ProdutoModel> query = entityManager.unwrap(Session.class).createQuery(hql, ProdutoModel.class);
        List<ProdutoModel> result = (List<ProdutoModel>)query
                .setFirstResult(firstResult)
                .setMaxResults(pageSize)
                .getResultList();

        return result.stream().map(model -> produtoMapper.toDomain(model)).toList();
    }

    public static TupleTransformer<ProdutoModel> getProdutoModelTransformer() {
        return (tuple, aliases) -> {
            ProdutoModel a = new ProdutoModel();
            a.setId((Long) tuple[0]);
            a.setNome((String) tuple[1]);
            a.setTipoProduto(TipoProdutoEnum.valueOf((String) tuple[2]));
            a.setCodigo((String) tuple[3]);
            a.setValor((BigDecimal) tuple[4]);
            a.setDataCriacao(tuple[5] == null ? null : LocalDateTime.from(((Timestamp) tuple[5]).toLocalDateTime()));
            a.setUltimaModificacao(tuple[6] == null ? null : LocalDateTime.from(((Timestamp) tuple[6]).toLocalDateTime()));
            a.setDataExclusao(tuple[7] == null ? null : LocalDateTime.from(((Timestamp) tuple[7]).toLocalDateTime()));
            return a;
        };
    }

}
