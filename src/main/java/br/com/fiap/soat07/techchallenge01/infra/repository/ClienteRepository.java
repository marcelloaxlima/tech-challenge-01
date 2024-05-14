package br.com.fiap.soat07.techchallenge01.infra.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.fiap.soat07.techchallenge01.infra.repository.model.ClienteModel;

public interface ClienteRepository extends JpaRepository<ClienteModel, Long> {
	


}
