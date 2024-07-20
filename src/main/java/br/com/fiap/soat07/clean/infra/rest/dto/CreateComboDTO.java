package br.com.fiap.soat07.clean.infra.rest.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class CreateComboDTO {
	
	private IdentificacaoDoClienteDTO cliente;
	private List<ProdutoDTO> produtos;

}
