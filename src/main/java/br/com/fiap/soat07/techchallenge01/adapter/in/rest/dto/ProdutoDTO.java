package br.com.fiap.soat07.techchallenge01.adapter.in.rest.dto;

import java.math.BigDecimal;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import br.com.fiap.soat07.techchallenge01.application.domain.enumeration.TipoProdutoEnum;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class ProdutoDTO {
	
	private long id;	
	private String nome;
	private String codigo;	
	private BigDecimal valor;
	private TipoProdutoEnum tipoProduto;
	
}
