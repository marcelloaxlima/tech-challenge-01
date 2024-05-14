package br.com.fiap.soat07.techchallenge01.infra.repository.model;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import br.com.fiap.soat07.techchallenge01.domain.enumeration.TipoProdutoEnum;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "PRODUTOS")
@EntityListeners(AuditingEntityListener.class)
public class ProdutoModel {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	
	@Column(unique=true)
	private String codigo;
	
	@Column(unique=true)
	private String nome;
	
	private BigDecimal valor;
	
	private TipoProdutoEnum tipoProduto;
	
	@ManyToOne
    @JoinColumn(name = "pedido_id", nullable = true)
	private PedidoModel pedido;
	
	@ManyToMany
    @JoinTable(name = "combo_produtos",
    joinColumns = @JoinColumn(name = "produtoid"),
    inverseJoinColumns = @JoinColumn(name = "comboid"))
	private List<ComboModel> combos;
	

}
