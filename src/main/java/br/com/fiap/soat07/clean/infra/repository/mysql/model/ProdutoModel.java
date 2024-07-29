package br.com.fiap.soat07.clean.infra.repository.mysql.model;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.util.Objects;
import java.util.Set;

import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import br.com.fiap.soat07.clean.core.domain.enumeration.TipoProdutoEnum;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;

@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@EntityListeners(AuditingEntityListener.class)
@Table(name = "PRODUTOS")
public class ProdutoModel {
	
	@Id
	@Column(nullable = false, updatable = false)
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column(name="codigo", unique=true)
	private String codigo;
	
	@Column(unique=true)
	private String nome;
	
	@Column(precision = 10, scale = 2)
	private BigDecimal valor;
	
	@Enumerated(EnumType.STRING)
	@Column
	private TipoProdutoEnum tipoProduto;
	
	@ManyToMany(mappedBy = "produtos")
	private Set<PedidoModel> pedidos;
	
	@ManyToMany(mappedBy = "produtos")
	private Set<ComboModel> combos;
	
	@CreatedDate
    @Column(nullable = false, updatable = false)
    private LocalDateTime dataCriacao;

    @LastModifiedDate
    @Column(nullable = false)
    private LocalDateTime ultimaModificacao;

	@Column
	private LocalDateTime dataExclusao;

	@Override
	public String toString() {
		return "ProdutoModel{" +
				"codigo='" + codigo +
				", id=" + id +
				", nome='" + nome +
				", valor=" + valor +
				", tipoProduto=" + tipoProduto +
				'}';
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (!(o instanceof ProdutoModel that)) return false;
        return Objects.equals(getCodigo(), that.getCodigo()) && Objects.equals(getNome(), that.getNome()) && Objects.equals(getValor(), that.getValor()) && getTipoProduto() == that.getTipoProduto();
	}

	@Override
	public int hashCode() {
		return Objects.hash(getCodigo(), getNome(), getValor(), getTipoProduto());
	}
}
