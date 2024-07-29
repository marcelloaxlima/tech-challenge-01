package br.com.fiap.soat07.clean.infra.repository.mysql.model;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.util.Objects;
import java.util.Set;

import br.com.fiap.soat07.clean.core.domain.entity.Produto;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@EntityListeners(AuditingEntityListener.class)
@Table(name = "COMBOS")
public class ComboModel {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column
	private String nome;
	
	@ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "cliente_id", nullable = true, updatable = true)
	private ClienteModel cliente;

    @OneToOne(mappedBy = "combo")
    private PedidoModel combo;

    @ManyToMany(cascade = { CascadeType.MERGE}, fetch = FetchType.EAGER)
    @JoinTable(name = "combo_produtos",
            joinColumns = @JoinColumn(name = "comboid", nullable = true, updatable = true),
            inverseJoinColumns = @JoinColumn(name = "produtoid", nullable = true, updatable = true))
	private Set<ProdutoModel> produtos;

    @CreatedDate
    @Column(nullable = false, updatable = false)
    private LocalDateTime dataCriacao;

    @LastModifiedDate
    @Column(nullable = false)
    private LocalDateTime ultimaModificacao;



    public BigDecimal getValor() {
        if (getProdutos() == null)
            return BigDecimal.ZERO.setScale(2, RoundingMode.HALF_EVEN);

        return getProdutos().stream()
                .map(ProdutoModel::getValor)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    @Override
    public String toString() {
        return "ComboModel{" +
                "cliente=" + cliente +
                ", nome='" + nome + '\'' +
                ", id=" + id +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ComboModel that)) return false;
        return Objects.equals(getNome(), that.getNome()) && Objects.equals(getCliente(), that.getCliente()) && Objects.equals(getDataCriacao(), that.getDataCriacao());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getNome(), getCliente(), getDataCriacao());
    }
}
