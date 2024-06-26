package br.com.fiap.soat07.techchallenge01.application.domain.entity;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.List;

import org.springframework.util.ObjectUtils;

import br.com.fiap.soat07.techchallenge01.application.domain.enumeration.PedidoStatusEnum;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Pedido {
	
	private long id;		
	private String nomeCliente;
	private String codigo;
	private List<Combo> combos;
	private List<Produto> produtos;
	private PedidoStatusEnum status;
	private OffsetDateTime dataCriacao;
	private OffsetDateTime ultimaModificacao;

	
	public BigDecimal getValor() {
		return ObjectUtils.isEmpty(produtos) ? new BigDecimal(0.00) : produtos.stream().map(m -> m.getValor())
		        .reduce(BigDecimal.ZERO, BigDecimal::add);
	}

}
