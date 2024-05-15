package br.com.fiap.soat07.techchallenge01.infra.repository.model;

import java.math.BigDecimal;
import java.util.List;
import java.util.Set;

import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import br.com.fiap.soat07.techchallenge01.domain.enumeration.TipoProdutoEnum;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
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
	
	@Enumerated(EnumType.STRING)
	private TipoProdutoEnum tipoProduto;
	
	@ManyToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinTable(name = "pedido_produtos",
    joinColumns = @JoinColumn(name = "produtoid"),
    inverseJoinColumns = @JoinColumn(name = "pedidoid"))
	private Set<PedidoModel> pedidos;
	
	@ManyToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinTable(name = "combo_produtos",
    joinColumns = @JoinColumn(name = "produtoid"),
    inverseJoinColumns = @JoinColumn(name = "comboid"))
	private Set<ComboModel> combos;
	

}
