package br.com.fiap.soat07.clean.core.domain.entity;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.util.ObjectUtils;

import br.com.fiap.soat07.clean.core.domain.enumeration.PedidoStatusEnum;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
public class Pedido {
	
	private Long id;
	private String nomeCliente;
	private String codigo;
	private Cliente cliente;
	private Combo combo;
	private Set<Produto> produtos;
	private PedidoStatusEnum status;
	private LocalDateTime dataCriacao;
	private LocalDateTime ultimaModificacao;

	public Pedido() {
		this.produtos = new HashSet<>();
		this.dataCriacao = LocalDateTime.now();
	}


	public Set<Produto> getProdutos() {
		if (produtos == null)
			this.produtos = new HashSet<>();
		return produtos;
	}

	public BigDecimal getValor() {
		if (getProdutos() == null)
			return BigDecimal.ZERO.setScale(2, RoundingMode.HALF_EVEN);

		return getProdutos().stream()
				.map(Produto::getValor)
		        .reduce(BigDecimal.ZERO, BigDecimal::add);
	}

}
