package br.com.fiap.soat07.clean.infra.repository.mysql.model;

import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.util.List;
import java.util.Objects;

import lombok.*;
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

@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@EntityListeners(AuditingEntityListener.class)
@Table(name = "CLIENTES")
public class ClienteModel {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
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
    private LocalDateTime dataCriacao;

    @LastModifiedDate
    @Column(nullable = false)
    private LocalDateTime ultimaModificacao;

	@Column
	private LocalDateTime dataExclusao;



	@Override
	public String toString() {
		return "ClienteModel{" +
				"cpf='" + cpf + '\'' +
				", nome='" + nome + '\'' +
				", id=" + id +
				'}';
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (!(o instanceof ClienteModel that)) return false;
        return Objects.equals(getNome(), that.getNome()) && Objects.equals(getCpf(), that.getCpf()) && Objects.equals(getCodigo(), that.getCodigo());
	}

	@Override
	public int hashCode() {
		return Objects.hash(getNome(), getCpf(), getCodigo());
	}
}
