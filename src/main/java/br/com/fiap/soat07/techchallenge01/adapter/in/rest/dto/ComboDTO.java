package br.com.fiap.soat07.techchallenge01.adapter.in.rest.dto;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class ComboDTO {
	
	private long id;	
	private String nome;	
	private ClienteDTO cliente;	
	private List<ProdutoDTO> produtos;

}
