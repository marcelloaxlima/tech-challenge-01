package br.com.fiap.soat07.techchallenge01.infra.repository;

import br.com.fiap.soat07.techchallenge01.domain.enumeration.TipoProdutoEnum;
import org.springframework.data.jpa.repository.JpaRepository;

import br.com.fiap.soat07.techchallenge01.infra.repository.model.ProdutoModel;
import org.springframework.data.jpa.repository.Query;

import java.util.Collection;
import java.util.Optional;

public interface ProdutoRepository extends JpaRepository<ProdutoModel, Long> {

    @Override
    @Query(
            value = "SELECT * FROM PEDIDOS p WHERE p.id = ?1",
            nativeQuery = true)
    Optional<ProdutoModel> findById(Long id);

    @Query(
            value = "SELECT * FROM PEDIDOS p WHERE p.tipoProduto = ?1",
            nativeQuery = true)
    Collection<ProdutoModel> search(TipoProdutoEnum tipo);


}
