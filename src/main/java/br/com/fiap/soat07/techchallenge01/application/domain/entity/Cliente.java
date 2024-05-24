package br.com.fiap.soat07.techchallenge01.application.domain.entity;

import java.time.OffsetDateTime;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Cliente {
	
	private long id;	
	private String nome;	
	private String cpf;	
	private String codigo;
	private OffsetDateTime dataCriacao;
	private OffsetDateTime ultimaModificacao;

}
