package br.com.fiap.soat07.techchallenge01.domain.provider;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

import br.com.fiap.soat07.techchallenge01.domain.entity.Cliente;
import br.com.fiap.soat07.techchallenge01.domain.provider.mapper.ClienteRepositoryMapper;
import br.com.fiap.soat07.techchallenge01.domain.usecase.ClienteUseCase;
import br.com.fiap.soat07.techchallenge01.infra.repository.ClienteRepository;
import br.com.fiap.soat07.techchallenge01.infra.repository.model.ClienteModel;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class ClienteProviderImpl implements ClienteUseCase {

	private final ClienteRepository repository;

	private final ClienteRepositoryMapper mapper;

	@Override
	public Page<Cliente> getPageable(Pageable pageable) {
		return new PageImpl<>(repository.findAll(pageable).stream().map(mapper::toDomain).toList(), pageable,
				repository.findAll(pageable).getNumberOfElements());

	}

	// TODO Criate specific exception
	@Override
	public Cliente getById(Long id) {
		Optional<ClienteModel> clienteModel = this.repository.findById(id);
		return mapper.toDomain(clienteModel.orElseThrow(() -> new RuntimeException()));
	}

	@Override
	public Cliente create(Cliente cliente) {
		return mapper.toDomain(repository.save(mapper.toModel(cliente)));
	}

	@Override
	public Cliente update(Long id, Cliente clienteAtualizado) {
		
		Cliente cliente = getById(id);
		
		if(null != cliente) {
			ClienteModel clienteModel = ClienteModel.builder()
					.id(clienteAtualizado.getId())
					.nome(clienteAtualizado.getNome())
					.codigo(clienteAtualizado.getCodigo())
					.cpf(clienteAtualizado.getCpf())
					.build();
			return mapper.toDomain(
					repository.save(clienteModel));
		}
		throw new RuntimeException();
	}

	@Override
	public void delete(Long id) {
		repository.deleteById(id);
	}

}
