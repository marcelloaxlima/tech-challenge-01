package br.com.fiap.soat07.clean.core.domain.entity;

import jakarta.persistence.Column;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Cliente {
	
	private Long id;
	private String nome;	
	private String cpf;	
	private String codigo;
	private LocalDateTime dataCriacao;
	private LocalDateTime ultimaModificacao;
	private LocalDateTime dataExclusao;

}
