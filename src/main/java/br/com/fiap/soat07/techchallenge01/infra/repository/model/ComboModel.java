package br.com.fiap.soat07.techchallenge01.infra.repository.model;

import java.time.OffsetDateTime;
import java.util.Set;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.FetchType;
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
@EntityListeners(AuditingEntityListener.class)
@Table(name = "COMBOS")
public class ComboModel {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	
	@Column
	private String nome;
	
	@ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "cliente_id", nullable = true, updatable = true)
	private ClienteModel cliente;
	      
    @ManyToMany(cascade = { CascadeType.MERGE})
    @JoinTable(name = "combo_produtos",
            joinColumns = @JoinColumn(name = "comboid", nullable = true, updatable = true),
            inverseJoinColumns = @JoinColumn(name = "produtoid", nullable = true, updatable = true))
	private Set<ProdutoModel> produtos;
	
    @ManyToMany(cascade = { CascadeType.MERGE})
    @JoinTable(name = "combo_pedidos",
    joinColumns = @JoinColumn(name = "comboid", nullable = true, updatable = true),
    inverseJoinColumns = @JoinColumn(name = "pedidoid", nullable = true, updatable = true))
	private Set<PedidoModel> pedidos;
	
    @CreatedDate
    @Column(nullable = false, updatable = false)
    private OffsetDateTime dataCriacao;

    @LastModifiedDate
    @Column(nullable = false)
    private OffsetDateTime ultimaModificacao;
}
