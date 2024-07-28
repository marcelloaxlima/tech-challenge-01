package br.com.fiap.soat07.clean.infra.repository.mysql;

import br.com.fiap.soat07.clean.core.domain.entity.Pagamento;
import br.com.fiap.soat07.clean.core.domain.entity.Pedido;
import br.com.fiap.soat07.clean.core.domain.entity.Produto;
import br.com.fiap.soat07.clean.core.domain.enumeration.PedidoStatusEnum;
import br.com.fiap.soat07.clean.core.domain.enumeration.ProvedorPagamentoEnum;
import br.com.fiap.soat07.clean.core.exception.ComboNotFoundException;
import br.com.fiap.soat07.clean.core.exception.PedidoNotFoundException;
import br.com.fiap.soat07.clean.core.gateway.PedidoGateway;
import br.com.fiap.soat07.clean.infra.repository.mysql.mapper.PedidoRepositoryMapper;
import br.com.fiap.soat07.clean.infra.repository.mysql.mapper.ProdutoRepositoryMapper;
import br.com.fiap.soat07.clean.infra.repository.mysql.model.ComboModel;
import br.com.fiap.soat07.clean.infra.repository.mysql.model.PedidoModel;
import br.com.fiap.soat07.clean.infra.repository.mysql.model.ProdutoModel;
import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
import jakarta.persistence.PersistenceContext;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.*;

@Component
@RequiredArgsConstructor
public class PedidoRepository implements PedidoGateway {

    @PersistenceContext
    private EntityManager entityManager;

    @Autowired
    private ComboRepository comboRepository;
    @Autowired
    private ProdutoRepository produtoRepository;

    @Autowired
    private PedidoRepositoryMapper pedidoMapper;
    @Autowired
    private ProdutoRepositoryMapper produtoMapper;


    /**
     * Find by id
     * @param id {@link Long}
     * @return {@link Optional<PedidoModel>}
     */
    private Optional<PedidoModel> _findById(long id) {
        final String hql = """
            SELECT p
            FROM PedidoModel p
            WHERE p.id = :pedidoId
            """;

        try {
            PedidoModel model = (PedidoModel) entityManager.createQuery(hql, PedidoModel.class)
                    .setParameter("pedidoId", id)
                    .getSingleResult();

            return Optional.of(model);
        } catch (NoResultException e) {
            return Optional.empty();
        }
    }

    /**
     * Find by id
     * @param id {@link Long}
     * @return {@link Optional<Pedido>}
     */
    @Override
    public Optional<Pedido> findById(long id) {
        return _findById(id).map(c -> pedidoMapper.toDomain(c));
    }

    @Override
    public Optional<Pagamento> findPagamento(ProvedorPagamentoEnum provedor, String id) {
        final String hql = """
            SELECT p
            FROM PedidoModel p
            WHERE p.provedor = :provedor
              AND p.transactionCode = :id
            """;

        try {
            PedidoModel model = (PedidoModel) entityManager.createQuery(hql, PedidoModel.class)
                    .setParameter("provedor", provedor)
                    .setParameter("id", id)
                    .getSingleResult();

            return Optional.of(pedidoMapper.toDomainPagamento(model));
        } catch (NoResultException e) {
            return Optional.empty();
        }
    }

    @Override
    public Optional<Pagamento> findPagamento(Pedido pedido) {
        PedidoModel pedidoModel = _findById(pedido.getId()).orElseThrow(() -> new PedidoNotFoundException(pedido.getId()));
        Pagamento pagamento = pedidoMapper.toDomainPagamento(pedidoModel);
        return Optional.ofNullable(pagamento);
    }

    /**
     * Find by id
     * @param id {@link Long}
     * @return {@link Optional<Pedido>}
     */
    @Override
    public Optional<Pedido> findByCombo(long id) {
        final String hql = """
            SELECT p
            FROM PedidoModel p
            WHERE p.combo.id = :comboId
            """;

        try {
            PedidoModel model = (PedidoModel) entityManager.createQuery(hql, PedidoModel.class)
                    .setParameter("comboId", id)
                    .getSingleResult();

            return Optional.of(pedidoMapper.toDomain(model));
        } catch (NoResultException e) {
            return Optional.empty();
        }
    }

    @Override
    public Pedido save(Pedido pedido) {
        ComboModel comboModel = comboRepository._findById(pedido.getCombo().getId()).orElseThrow(() -> new ComboNotFoundException(pedido.getCombo().getId()));
        Set<ProdutoModel> produtosModel = new HashSet<>(produtoRepository._find(pedido.getProdutos()));

        PedidoModel model = null;
        if (pedido.getId() == null) {
            model = new PedidoModel();

            model.setCombo(comboModel);
            model.setNomeCliente(pedido.getNomeCliente());
            model.setDataCriacao(pedido.getDataCriacao());
            model.setUltimaModificacao(pedido.getUltimaModificacao());
            model.setProdutos(produtosModel);
            model.setStatus(pedido.getStatus());

            entityManager.persist(model);

        } else {
            model = _findById(pedido.getId()).orElseThrow(() -> new PedidoNotFoundException(pedido.getId()));
            model.setNomeCliente(pedido.getNomeCliente());
            model.setUltimaModificacao(pedido.getUltimaModificacao());
            model.setProdutos(produtosModel);
            model.setStatus(pedido.getStatus());

            entityManager.merge(model);
        }

        return pedidoMapper.toDomain(model);
    }

    @Override
    public Pagamento save(Pedido pedido, Pagamento pagamento) {

        PedidoModel model = null;
        if (pedido.getId() == null) {
            model = new PedidoModel();

            model.setUltimaModificacao(pedido.getUltimaModificacao());
            model.setProvedor(pagamento.getProvedorServico());
            model.setMetodo(pagamento.getMetodoPagamento());
            model.setTransactionCode(pagamento.getId());

            entityManager.persist(model);

        } else {
            model = _findById(pedido.getId()).orElseThrow(() -> new PedidoNotFoundException(pedido.getId()));
            model.setProvedor(pagamento.getProvedorServico());
            model.setMetodo(pagamento.getMetodoPagamento());
            model.setTransactionCode(pagamento.getId());

            entityManager.merge(model);
        }

        return pedidoMapper.toDomainPagamento(model);
    }

    @Override
    public void delete(Pedido pedido) {
        PedidoModel model = _findById(pedido.getId()).orElseThrow(() -> new PedidoNotFoundException(pedido.getId()));

        entityManager.remove(model);
    }

    @Override
    public void deleteProduto(Pedido pedido, Produto produto) {
        final String sql = """
            DELETE FROM pedido_produtos
            WHERE pedidoId = :pedidoId
              AND produtoId = :produtoId
            """;

        entityManager.createNativeQuery(sql)
                .setParameter("pedidoId", pedido.getId())
                .setParameter("produtoId", produto.getId())
                .executeUpdate();
        entityManager.flush();
    }


    @SuppressWarnings("unchecked")
    @Override
    public Collection<Pedido> find(int pageNumber, int pageSize) {
        String hql = """
            SELECT p
            FROM PedidoModel p
            WHERE 1 = 1
              AND p.status <> :situacao
            ORDER BY p.dataCriacao DESC
            """;
        pageNumber = Math.max(pageNumber, 1);
        pageSize = Math.max(pageSize, 1);
        int firstResult = (pageNumber - 1) * pageSize;

        List<PedidoModel> result = entityManager.createQuery(hql, PedidoModel.class)
                .setParameter("situacao", PedidoStatusEnum.FINALIZADO)
                .setFirstResult(firstResult)
                .setMaxResults(pageSize)
                .getResultList();

        return result.stream().map(model -> pedidoMapper.toDomain(model)).toList();
    }

}
