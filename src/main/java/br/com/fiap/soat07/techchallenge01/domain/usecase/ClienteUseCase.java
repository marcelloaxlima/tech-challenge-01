package br.com.fiap.soat07.techchallenge01.domain.usecase;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import br.com.fiap.soat07.techchallenge01.domain.entity.Cliente;

public interface ClienteUseCase {
	
	/**
	 * Get pageable
	 * @param pageable {@link Pageable}
	 * @return {@link Page<Cliente>}
	 */
	Page<Cliente> getPageable(Pageable pageable);
	
	/**
	 * Get by id
	 * @param id {@link Long}
	 * @return {@link Cliente}
	 */
	Cliente getById(Long id);
	
	/**
	 * Create new
	 * @param cliente {@link Cliente}
	 * @return {@link Cliente}
	 */
	Cliente create(Cliente cliente);
	
	/**
	 * Update by id
	 * @param id {@link Long}
	 * @param cliente {@link Cliente}
	 * @return {@link Cliente}
	 */
	Cliente update(Long id, Cliente cliente);
	
	/**
	 * Delete by id
	 * @param id {@link Long}
	 */
	void delete(Long id);

}
