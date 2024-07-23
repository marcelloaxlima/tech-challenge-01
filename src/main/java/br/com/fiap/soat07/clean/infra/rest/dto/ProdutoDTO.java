package br.com.fiap.soat07.clean.infra.rest.dto;

import java.math.BigDecimal;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import br.com.fiap.soat07.clean.core.domain.enumeration.TipoProdutoEnum;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class ProdutoDTO {
	
	private Long id;
	private String nome;
	private String codigo;	
	private BigDecimal valor;
	private TipoProdutoEnum tipoProduto;

	public BigDecimal getValor() {
		if (valor == null)
			return BigDecimal.ZERO;
		return valor;
	}
}
