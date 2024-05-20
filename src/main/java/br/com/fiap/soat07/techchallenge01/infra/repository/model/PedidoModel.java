package br.com.fiap.soat07.techchallenge01.infra.repository.model;

import java.math.BigDecimal;
import java.util.List;

import jakarta.persistence.Id;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import org.springframework.util.ObjectUtils;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
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
@Table(name = "PEDIDOS")
@EntityListeners(AuditingEntityListener.class)
public class PedidoModel {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	
	private String codigo;
	
	private String nome;
	
	@OneToMany(mappedBy = "pedido", cascade = CascadeType.ALL, orphanRemoval = true)
	private List<ComboModel> combos;
	
	@OneToMany(mappedBy = "pedido", cascade = CascadeType.ALL, orphanRemoval = true)
	private List<ProdutoModel> produtos;

	
	public BigDecimal getValor() {
		return ObjectUtils.isEmpty(produtos) ? new BigDecimal(0.00) : produtos.stream().map(m -> m.getValor())
		        .reduce(BigDecimal.ZERO, BigDecimal::add);
	}

	public void setNome(String nome) {
		if (nome == null || nome.isEmpty() || nome.isBlank())
			throw new IllegalArgumentException("Obrigatório definir o nome do produto");
		this.nome = nome.trim();
	}

}
