package br.com.fiap.soat07.techchallenge01.application.ports.in;

import br.com.fiap.soat07.techchallenge01.application.domain.entity.Produto;
import br.com.fiap.soat07.techchallenge01.application.domain.enumeration.TipoProdutoEnum;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface SearchProdutoUseCase {

	/**
	 * Get by id
	 * @param id {@link Long}
	 * @return {@link Produto}
	 */
	Produto getById(Long id);

	/**
	 * Get pageable
	 * @param pageable {@link Pageable}
	 * @return {@link Page <Produto>}
	 */
	Page<Produto> search(Pageable pageable);

	/**
	 * Get pageable
	 * @param pageable {@link Pageable}
	 * @return {@link Page <Produto>}
	 */
	Page<Produto> search(TipoProdutoEnum tipo, Pageable pageable);

}
