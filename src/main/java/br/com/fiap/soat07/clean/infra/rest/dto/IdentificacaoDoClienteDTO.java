package br.com.fiap.soat07.clean.infra.rest.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class IdentificacaoDoClienteDTO {
	public static final IdentificacaoDoClienteDTO ANONIMO = new IdentificacaoDoClienteDTO();

	private Long id;
	private String nome;
	private String cpf;

	public boolean isAnonimo() {
		return id == null;
	}

}
