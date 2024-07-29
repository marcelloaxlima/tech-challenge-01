package br.com.fiap.soat07.clean.infra.repository.mysql.model;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.util.List;
import java.util.Objects;
import java.util.Set;

import br.com.fiap.soat07.clean.core.domain.entity.Produto;
import br.com.fiap.soat07.clean.core.domain.enumeration.MetodoPagamentoEnum;
import br.com.fiap.soat07.clean.core.domain.enumeration.PagamentoStatusEnum;
import br.com.fiap.soat07.clean.core.domain.enumeration.ProvedorPagamentoEnum;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import org.springframework.util.ObjectUtils;

import br.com.fiap.soat07.clean.core.domain.enumeration.PedidoStatusEnum;

@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@EntityListeners(AuditingEntityListener.class)
@Table(name = "PEDIDOS")
public class PedidoModel {
	
	@Id
	@Column(nullable = false, updatable = false)
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column
	private String codigo;
	
	@Column
	private String nomeCliente;

	@OneToOne
	@JoinColumn(name = "combo_id", referencedColumnName = "id")
	private ComboModel combo;

	@Enumerated(EnumType.STRING)
	@Column
	private PedidoStatusEnum status;

	@Column(precision = 10, scale = 2)
    private BigDecimal valor;
	
	@ManyToMany(cascade = { CascadeType.MERGE})
    @JoinTable(name = "pedido_produtos",
    joinColumns = @JoinColumn(name = "pedidoid", nullable = true, updatable = true),
    inverseJoinColumns = @JoinColumn(name = "produtoid", nullable = true, updatable = true))
	private Set<ProdutoModel> produtos;
	
	@CreatedDate
    @Column(nullable = false, updatable = false)
    private LocalDateTime dataCriacao;

    @LastModifiedDate
    @Column(nullable = false)
    private LocalDateTime ultimaModificacao;

	@Enumerated(EnumType.STRING)
	@Column(length = 30, nullable = true)
	private ProvedorPagamentoEnum provedor;

	@Enumerated(EnumType.STRING)
	@Column(length = 30, nullable = true)
	private MetodoPagamentoEnum metodo;

	@Enumerated(EnumType.STRING)
	@Column(length = 30, nullable = true)
	private PagamentoStatusEnum pagamentoStatus;

	@Column
	private String transactionCode;

	@Column
	private LocalDateTime transactionTime;


	public BigDecimal getValor() {
		if (getProdutos() == null)
			return BigDecimal.ZERO.setScale(2, RoundingMode.HALF_EVEN);

		return getProdutos().stream()
				.map(ProdutoModel::getValor)
				.reduce(BigDecimal.ZERO, BigDecimal::add);
	}


	@Override
	public String toString() {
		return "PedidoModel{" +
				"id=" + id +
				", codigo='" + codigo + '\'' +
				", nomeCliente='" + nomeCliente + '\'' +
				", status=" + status +
				", valor=" + valor +
				'}';
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (!(o instanceof PedidoModel that)) return false;
        return getId() == that.getId() && Objects.equals(getCodigo(), that.getCodigo()) && Objects.equals(getNomeCliente(), that.getNomeCliente()) && getStatus() == that.getStatus();
	}

	@Override
	public int hashCode() {
		return Objects.hash(getId(), getCodigo(), getNomeCliente(), getStatus());
	}
}
