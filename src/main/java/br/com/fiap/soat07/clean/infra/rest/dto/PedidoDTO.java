package br.com.fiap.soat07.clean.infra.rest.dto;

import java.math.BigDecimal;
import java.util.List;
import java.util.Set;

import org.springframework.util.ObjectUtils;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import br.com.fiap.soat07.clean.core.domain.enumeration.PedidoStatusEnum;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class PedidoDTO {
	
	private Long id;
	private String nomeCliente;
	private String codigo;
	private Long comboId;
	private ClienteDTO cliente;
	private Set<ProdutoDTO> produtos;
	private PedidoStatusEnum status;

	
	public BigDecimal getValor() {
		if (getProdutos() == null || getProdutos().isEmpty()) {
			return BigDecimal.ZERO;
		}

		return getProdutos().stream()
				.map(ProdutoDTO::getValor)
		        .reduce(BigDecimal.ZERO, BigDecimal::add);
	}

}
