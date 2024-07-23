package br.com.fiap.soat07.clean.infra.rest.dto;

import br.com.fiap.soat07.clean.core.domain.value.CPF;
import br.com.fiap.soat07.clean.infra.rest.presenter.CPFDeserializer;
import br.com.fiap.soat07.clean.infra.rest.presenter.CPFSerializer;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class ClienteDTO {
	
	private Long id;
	private String nome;	
	private String cpf;
	private String codigo;

}
