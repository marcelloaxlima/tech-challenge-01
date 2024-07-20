package br.com.fiap.soat07.clean.core.domain.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import br.com.fiap.soat07.clean.core.domain.enumeration.MetodoPagamentoEnum;
import br.com.fiap.soat07.clean.core.domain.enumeration.PagamentoStatusEnum;
import br.com.fiap.soat07.clean.core.domain.enumeration.ProvedorPagamentoEnum;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class Pagamento {
	
	private String id;
	private Long pedidoId;
	private PagamentoStatusEnum status;
	private ProvedorPagamentoEnum provedorServico;
	private MetodoPagamentoEnum metodoPagamento;
	private LocalDate data;
	private BigDecimal valor;

}
