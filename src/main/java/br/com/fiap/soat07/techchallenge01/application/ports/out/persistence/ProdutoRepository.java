package br.com.fiap.soat07.techchallenge01.application.ports.out.persistence;

import java.util.Optional;
import java.util.Set;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import br.com.fiap.soat07.techchallenge01.adapter.out.persistence.mysql.model.ProdutoModel;
import br.com.fiap.soat07.techchallenge01.application.domain.enumeration.TipoProdutoEnum;

public interface ProdutoRepository extends JpaRepository<ProdutoModel, Long> {
	
	@Query(
			  value = "select p1_1.id,p1_1.codigo,p1_1.data_criacao,p1_1.data_exclusao,p1_1.nome,p1_1.tipo_produto,p1_1.ultima_modificacao,p1_1.valor from combo_produtos p1_0 join produtos p1_1 on p1_1.id=p1_0.produtoid where p1_0.comboid=?1", 
			  nativeQuery = true)
	Optional<Set<ProdutoModel>> findProdutosByComboId(Long id);
	

    @Override
    @Query(
            value = "SELECT * FROM PRODUTOS p WHERE p.id = ?1",
            nativeQuery = true)
    Optional<ProdutoModel> findById(Long id);

    @Query(
            value = "SELECT p FROM ProdutoModel p WHERE p.tipoProduto = ?1")
    Page<ProdutoModel> searchByTipo(TipoProdutoEnum tipo, Pageable pageable);


}
