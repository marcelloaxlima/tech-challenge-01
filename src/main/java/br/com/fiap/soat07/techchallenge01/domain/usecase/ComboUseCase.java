package br.com.fiap.soat07.techchallenge01.domain.usecase;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import br.com.fiap.soat07.techchallenge01.domain.entity.Combo;

public interface ComboUseCase {
	
	/**
	 * Get pageable
	 * @param pageable {@link Pageable}
	 * @return {@link Page<Combo>}
	 */
	Page<Combo> getPageable(Pageable pageable);
	
	/**
	 * Get by id
	 * @param id {@link Long}
	 * @return {@link Combo}
	 */
	Combo getById(long id);
	
	/**
	 * Create new
	 * @param cliente {@link Combo}
	 * @return {@link Combo}
	 */
	Combo create(Combo combo);
	
	/**
	 * Update by id
	 * @param id {@link Long}
	 * @param cliente {@link Combo}
	 * @return {@link Combo}
	 */
	Combo update(long id, Combo combo);
	
	/**
	 * Delete by id
	 * @param id {@link Long}
	 */
	void delete(long id);

}
