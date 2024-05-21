package br.com.fiap.soat07.techchallenge01.application.model.dto;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.util.ObjectUtils;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import br.com.fiap.soat07.techchallenge01.domain.enumeration.PedidoStatusEnum;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class PedidoDTO {
	
	private long id;		
	private String nomeCliente;
	private String codigo;
	private ClienteDTO cliente;
	private List<ComboDTO> combos;
	private List<ProdutoDTO> produtos;
	private PedidoStatusEnum status;

	
	public BigDecimal getValor() {
		return ObjectUtils.isEmpty(produtos) ? new BigDecimal(0.00) : produtos.stream().map(m -> m.getValor())
		        .reduce(BigDecimal.ZERO, BigDecimal::add);
	}

}
