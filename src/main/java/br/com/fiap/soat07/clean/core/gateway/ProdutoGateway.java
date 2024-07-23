package br.com.fiap.soat07.clean.core.gateway;

import br.com.fiap.soat07.clean.core.domain.entity.Combo;
import br.com.fiap.soat07.clean.core.domain.entity.Pedido;
import br.com.fiap.soat07.clean.core.domain.entity.Produto;
import br.com.fiap.soat07.clean.core.domain.enumeration.TipoProdutoEnum;

import java.util.Collection;
import java.util.Optional;

public interface ProdutoGateway {

    /**
     * Get by id
     * @param id {@link Long}
     * @return {@link Produto}
     */
    Optional<Produto> findById(long id);

    Produto save(Produto produto);

    void delete(Produto produto);

    Collection<Produto> find(Pedido pedido);

    Collection<Produto> find(int pageNumber, int pageSize);

    Collection<Produto> find(Combo combo);

    Collection<Produto> find(TipoProdutoEnum tipo, int pageNumber, int pageSize);
}
