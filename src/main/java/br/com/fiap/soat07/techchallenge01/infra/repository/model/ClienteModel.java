package br.com.fiap.soat07.techchallenge01.infra.repository.model;

import java.time.OffsetDateTime;
import java.util.List;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EntityListeners(AuditingEntityListener.class)
@Table(name = "CLIENTES")
public class ClienteModel {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	
	@Column
	private String nome;
	
	@Column(unique=true)
	private String cpf;	
	
	@Column
	private String codigo;
	
	@OneToMany(mappedBy = "cliente", cascade = CascadeType.ALL)
	private List<ComboModel> combos;
	
	@CreatedDate
    @Column(nullable = false, updatable = false)
    private OffsetDateTime dataCriacao;

    @LastModifiedDate
    @Column(nullable = false)
    private OffsetDateTime ultimaModificacao;
	

}
