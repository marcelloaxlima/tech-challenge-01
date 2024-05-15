package br.com.fiap.soat07.techchallenge01.infra.repository.model;

import java.util.List;
import java.util.Set;

import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
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
@Table(name = "COMBOS")
@EntityListeners(AuditingEntityListener.class)
public class ComboModel {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	
	private String nome;
	
	@ManyToOne
    @JoinColumn(name = "cliente_id", nullable = true)
	private ClienteModel cliente;
	
	@ManyToMany(mappedBy = "combos")
	private Set<ProdutoModel> produtos;
	
	@ManyToMany(mappedBy = "combos")
	private Set<PedidoModel> pedidos;
	

}
