package br.com.fiap.soat07.techchallenge01.domain.usecase;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import br.com.fiap.soat07.techchallenge01.domain.entity.Produto;

public interface ProdutoUseCase {
	
	/**
	 * Get pageable
	 * @param pageable {@link Pageable}
	 * @return {@link Page<Produto>}
	 */
	Page<Produto> getPageable(Pageable pageable);
	
	/**
	 * Get by id
	 * @param id {@link Long}
	 * @return {@link Produto}
	 */
	Produto getById(Long id);
	
	/**
	 * Create new
	 * @param cliente {@link Produto}
	 * @return {@link Produto}
	 */
	Produto create(Produto produto);
	
	/**
	 * Update by id
	 * @param id {@link Long}
	 * @param cliente {@link Produto}
	 * @return {@link Produto}
	 */
	Produto update(Long id, Produto produto);
	
	/**
	 * Delete by id
	 * @param id {@link Long}
	 */
	void delete(Long id);

}
