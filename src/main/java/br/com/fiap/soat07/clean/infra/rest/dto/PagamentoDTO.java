package br.com.fiap.soat07.clean.infra.rest.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import br.com.fiap.soat07.clean.core.domain.enumeration.MetodoPagamentoEnum;
import br.com.fiap.soat07.clean.core.domain.enumeration.PagamentoStatusEnum;
import br.com.fiap.soat07.clean.core.domain.enumeration.ProvedorPagamentoEnum;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class PagamentoDTO {
	
	private Long id;
	private Long pedidoId;
	private PagamentoStatusEnum status;
	private ProvedorPagamentoEnum provedorServico;
	private MetodoPagamentoEnum metodoPagamento;
}
