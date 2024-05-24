package br.com.fiap.soat07.techchallenge01.adapter.out.persistence.mysql.model;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.Set;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import br.com.fiap.soat07.techchallenge01.application.domain.enumeration.TipoProdutoEnum;
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
//@SQLDelete(sql = "UPDATE PRODUTOS SET dataExclusao = CURRENT_TIMESTAMP WHERE id=?")
//@Where(clause = "dataExclusao IS NULL")
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
	
	@ManyToMany(mappedBy = "produtos")
	private Set<PedidoModel> pedidos;
	
	@ManyToMany(mappedBy = "produtos")
	private Set<ComboModel> combos;
	
	@CreatedDate
    @Column(nullable = false, updatable = false)
    private OffsetDateTime dataCriacao;

    @LastModifiedDate
    @Column(nullable = false)
    private OffsetDateTime ultimaModificacao;

	@Column
	private OffsetDateTime dataExclusao;
}
