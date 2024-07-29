package br.com.fiap.soat07.clean.core.usecase.cliente;

import br.com.fiap.soat07.clean.core.domain.entity.Cliente;
import br.com.fiap.soat07.clean.core.domain.entity.Combo;
import br.com.fiap.soat07.clean.core.gateway.ClienteGateway;
import br.com.fiap.soat07.clean.core.gateway.ComboGateway;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.Optional;

@Component
public class SearchClienteUseCase {

	private final ClienteGateway clienteGateway;


	public SearchClienteUseCase(ClienteGateway clienteGateway) {
		this.clienteGateway = clienteGateway;
	}


	/**
	 * Get by id
	 * @param id {@link Long}
	 * @return {@link Optional<Cliente>}
	 */
	public Optional<Cliente> findById(Long id) {
		if (id == null)
			throw new IllegalArgumentException("Obrigatório informar código do cliente");

		return clienteGateway.findById(id);
	}

	/**
	 * Get by Cpf
	 * @param cpf {@link String}
	 * @return {@link Optional<Cliente>}
	 */
	public Optional<Cliente> findByCpf(String cpf) {
		if (cpf == null || cpf.isEmpty())
			throw new IllegalArgumentException("Obrigatório informar o cpf do cliente");

		return clienteGateway.findByCpf(cpf);
	}

	/**
	 * Get by Código
	 * @param codigo {@link String}
	 * @return {@link Optional<Cliente>}
	 */
	public Optional<Cliente> findByCodigo(String codigo) {
		if (codigo == null || codigo.isEmpty())
			throw new IllegalArgumentException("Obrigatório informar o código do cliente");

		return clienteGateway.findByCodigo(codigo);
	}

	public Collection<Cliente> find(int pageNumber, int pageSize) {
		return clienteGateway.find(pageNumber, pageSize);
	}


}
