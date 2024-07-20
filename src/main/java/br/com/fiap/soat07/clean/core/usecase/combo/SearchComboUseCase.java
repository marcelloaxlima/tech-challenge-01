package br.com.fiap.soat07.clean.core.usecase.combo;

import br.com.fiap.soat07.clean.core.domain.entity.Combo;
import br.com.fiap.soat07.clean.core.domain.entity.Pedido;
import br.com.fiap.soat07.clean.core.domain.entity.Produto;
import br.com.fiap.soat07.clean.core.domain.enumeration.TipoProdutoEnum;
import br.com.fiap.soat07.clean.core.gateway.ComboGateway;
import br.com.fiap.soat07.clean.core.gateway.ProdutoGateway;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.Optional;

@Component
public class SearchComboUseCase {

	private final ComboGateway comboGateway;


	public SearchComboUseCase(ComboGateway comboGateway) {
		this.comboGateway = comboGateway;
	}


	/**
	 * Get by id
	 * @param id {@link Long}
	 * @return {@link Optional<Combo>}
	 */
	public Optional<Combo> findById(Long id) {
		if (id == null)
			throw new IllegalArgumentException("Obrigatório informar código do produto");

		return comboGateway.findById(id);
	}


	public Collection<Combo> find(int pageNumber, int pageSize) {
		return comboGateway.find(pageNumber, pageSize);
	}


}
