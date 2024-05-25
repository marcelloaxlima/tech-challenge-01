package br.com.fiap.soat07.techchallenge01.application.ports.out.persistence;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.fiap.soat07.techchallenge01.adapter.out.persistence.mysql.model.PedidoModel;

public interface PedidoRepository extends JpaRepository<PedidoModel, Long> {
	

}
