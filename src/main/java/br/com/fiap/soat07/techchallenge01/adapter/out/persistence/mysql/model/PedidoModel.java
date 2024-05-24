package br.com.fiap.soat07.techchallenge01.adapter.out.persistence.mysql.model;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.List;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import org.springframework.util.ObjectUtils;

import br.com.fiap.soat07.techchallenge01.application.domain.enumeration.PedidoStatusEnum;
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
@Table(name = "PEDIDOS")
public class PedidoModel {
	
	@Id
	@Column(nullable = false, updatable = false)
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	
	@Column
	private String codigo;
	
	@Column
	private String nomeCliente;
	
	@Enumerated(EnumType.STRING)
	@Column
	private PedidoStatusEnum status;
	
	@Column(precision = 10, scale = 2)
    private BigDecimal valor;
	
	@ManyToMany(cascade = { CascadeType.MERGE})
    @JoinTable(name = "pedido_produtos",
    joinColumns = @JoinColumn(name = "pedidoid", nullable = true, updatable = true),
    inverseJoinColumns = @JoinColumn(name = "produtoid", nullable = true, updatable = true))
	private List<ProdutoModel> produtos;
	
	@CreatedDate
    @Column(nullable = false, updatable = false)
    private OffsetDateTime dataCriacao;

    @LastModifiedDate
    @Column(nullable = false)
    private OffsetDateTime ultimaModificacao;

	
	public BigDecimal getValor() {
		return ObjectUtils.isEmpty(produtos) ? new BigDecimal(0.00) : produtos.stream().map(m -> m.getValor())
		        .reduce(BigDecimal.ZERO, BigDecimal::add);
	}

	

}
