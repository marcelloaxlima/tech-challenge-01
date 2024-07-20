package br.com.fiap.soat07.clean.core.gateway;

import br.com.fiap.soat07.clean.core.domain.entity.Combo;
import br.com.fiap.soat07.clean.core.domain.entity.Pagamento;
import br.com.fiap.soat07.clean.core.domain.entity.Pedido;
import br.com.fiap.soat07.clean.core.domain.entity.Produto;
import org.springframework.data.domain.Page;

import java.util.Collection;
import java.util.Optional;

public interface PedidoGateway {

    /**
     * Get by id
     * @param id {@link Long}
     * @return {@link Combo}
     */
    Optional<Pedido> findById(long id);

    Optional<Pedido> findByCombo(long id);

    Optional<Pagamento> findPagamento(Pedido pedido);

    Pedido save(Pedido pedido);

    Pagamento save(Pedido pedido, Pagamento pagamento);

    void delete(Pedido pedido);

    void deleteProduto(Pedido pedido, Produto produto);

//    void save(long pedidoid, List<Long> produtoIds);

    /**
     * Get pageable
     * @param pageNumber
     * @param pageSize
     * @return {@link Page < Pedido >}
     */
    Collection<Pedido> find(int pageNumber, int pageSize);

}
