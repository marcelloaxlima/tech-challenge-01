package br.com.fiap.soat07.techchallenge01.domain.entity;

import java.math.BigDecimal;

import br.com.fiap.soat07.techchallenge01.domain.enumeration.TipoProdutoEnum;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Produto {
	
	private long id;	
	private String codigo;	
	private String nome;	
	private BigDecimal valor;	
	private TipoProdutoEnum tipoProduto;	
	
}
