package br.com.fiap.soat07.techchallenge01.application.ports.out.persistence;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import br.com.fiap.soat07.techchallenge01.adapter.out.persistence.mysql.model.ComboModel;

public interface ComboRepository extends JpaRepository<ComboModel, Long> {
	

	@Query(
			  value = "SELECT * FROM COMBOS C WHERE C.id = ?1", 
			  nativeQuery = true)
	Optional<ComboModel> findComboById(Long id);


}
