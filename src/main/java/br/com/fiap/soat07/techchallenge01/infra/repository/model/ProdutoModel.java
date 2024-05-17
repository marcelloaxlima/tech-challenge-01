package br.com.fiap.soat07.techchallenge01.infra.repository.model;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.Set;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
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
@EntityListeners(AuditingEntityListener.class)
@Table(name = "PRODUTOS")
public class ProdutoModel {
	
	@Id
	@Column(nullable = false, updatable = false)
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	
	@Column(unique=true)
	private String codigo;
	
	@Column(unique=true)
	private String nome;
	
	@Column(precision = 10, scale = 2)
	private BigDecimal valor;
	
	@Enumerated(EnumType.STRING)
	@Column
	private TipoProdutoEnum tipoProduto;
	
	//@ManyToMany(cascade = { CascadeType.MERGE, CascadeType.PERSIST})
    //@JoinTable(name = "pedido_produtos",
    //joinColumns = @JoinColumn(name = "produtoid"),
    //inverseJoinColumns = @JoinColumn(name = "pedidoid"))	
	@ManyToMany(mappedBy = "produtos")
	private Set<PedidoModel> pedidos;
	
	//@ManyToMany(cascade = { CascadeType.MERGE, CascadeType.PERSIST})
    //@JoinTable(name = "combo_produtos",
    //joinColumns = @JoinColumn(name = "produtoid"),
    //inverseJoinColumns = @JoinColumn(name = "comboid"))
	@ManyToMany(mappedBy = "produtos")
	private Set<ComboModel> combos;
	
	@CreatedDate
    @Column(nullable = false, updatable = false)
    private OffsetDateTime dataCriacao;

    @LastModifiedDate
    @Column(nullable = false)
    private OffsetDateTime ultimaModificacao;
}
