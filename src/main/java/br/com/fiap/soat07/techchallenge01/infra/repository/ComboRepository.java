package br.com.fiap.soat07.techchallenge01.infra.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import br.com.fiap.soat07.techchallenge01.infra.repository.model.ComboModel;

public interface ComboRepository extends JpaRepository<ComboModel, Long> {
	
	@Override
	@Query(
			  value = "SELECT * FROM COMBOS C WHERE C.id = ?1", 
			  nativeQuery = true)
	Optional<ComboModel> findById(Long id);


}
