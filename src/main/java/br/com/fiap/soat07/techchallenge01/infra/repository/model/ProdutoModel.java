package br.com.fiap.soat07.techchallenge01.infra.repository.model;

import java.math.BigDecimal;

import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import br.com.fiap.soat07.techchallenge01.domain.enumeration.TipoProdutoEnum;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
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
	
	private String codigo;
	
	private String nome;
	
	private BigDecimal valor;
	
	private TipoProdutoEnum tipoProduto;
	
	@ManyToOne
    @JoinColumn(name = "pedido_id", nullable = true)
	private PedidoModel pedido;
	
	@ManyToOne
    @JoinColumn(name = "combo_id", nullable = true)
	private ComboModel combo;
	

}
