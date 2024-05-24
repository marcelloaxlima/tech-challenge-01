package br.com.fiap.soat07.techchallenge01.application.domain.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import br.com.fiap.soat07.techchallenge01.application.domain.enumeration.MetodoPagamentoEnum;
import br.com.fiap.soat07.techchallenge01.application.domain.enumeration.PagamentoStatusEnum;
import br.com.fiap.soat07.techchallenge01.application.domain.enumeration.ProvedorPagamentoEnum;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class Pagamento {
	
	private long id;	
	private long pedidoId;	
	private PagamentoStatusEnum status;
	private ProvedorPagamentoEnum provedorServiço;
	private MetodoPagamentoEnum metodoPagamento;

}
