package br.com.fiap.soat07.clean.core.gateway;

import java.util.Optional;

import br.com.fiap.soat07.clean.core.domain.entity.Combo;
import br.com.fiap.soat07.clean.core.domain.entity.Produto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Collection;

public interface ComboGateway {

    /**
     * Get by id
     * @param id {@link Long}
     * @return {@link Optional<Combo>}
     * @see Optional
     */
    Optional<Combo> findById(long id);

    Combo save(Combo combo);

    void delete(Combo combo);

    void deleteProdutosOfCombo(Combo combo, Produto produto);


    /**
     * Get pageable
     * @param pageNumber
     * @param pageSize
     * @return {@link Page < Combo >}
     */
    Collection<Combo> find(int pageNumber, int pageSize);
}
