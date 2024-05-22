package br.com.fiap.soat07.techchallenge01.application.model.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import br.com.fiap.soat07.techchallenge01.domain.enumeration.MetodoPagamentoEnum;
import br.com.fiap.soat07.techchallenge01.domain.enumeration.PagamentoStatusEnum;
import br.com.fiap.soat07.techchallenge01.domain.enumeration.ProvedorPagamentoEnum;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class PagamentoDTO {
	
	private long id;	
	private long pedidoId;		
	private PagamentoStatusEnum status;
	private ProvedorPagamentoEnum provedorServiço;
	private MetodoPagamentoEnum metodoPagamento;
}
