package br.com.fiap.soat07.techchallenge01.domain.entity;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.util.ObjectUtils;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Pedido {
	
	private long id;	
	private long codigo;
	private long nome;
	private Cliente cliente;
	private List<Produto> itens;

	
	public BigDecimal getValor() {
		return ObjectUtils.isEmpty(itens) ? new BigDecimal(0.00) : itens.stream().map(m -> m.getValor())
		        .reduce(BigDecimal.ZERO, BigDecimal::add);
	}

}
