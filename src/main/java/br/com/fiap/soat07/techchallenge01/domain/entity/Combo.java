package br.com.fiap.soat07.techchallenge01.domain.entity;

import java.time.OffsetDateTime;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Combo {
	
	private long id;	
	private String nome;	
	private Cliente cliente;	
	private List<Produto> produtos;
	private OffsetDateTime dataCriacao;
	private OffsetDateTime ultimaModificacao;

}
