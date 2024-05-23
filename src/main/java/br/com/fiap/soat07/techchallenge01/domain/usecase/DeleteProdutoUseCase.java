package br.com.fiap.soat07.techchallenge01.domain.usecase;

public interface DeleteProdutoUseCase {
	
	/**
	 * Delete by id
	 * Exclusão lógica, atualiza o campo dataExclusao
	 * @param id {@link Long}
	 */
	void delete(Long id);

}
