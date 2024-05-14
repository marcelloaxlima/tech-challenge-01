package br.com.fiap.soat07.techchallenge01.domain.provider;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

import br.com.fiap.soat07.techchallenge01.domain.entity.Pedido;
import br.com.fiap.soat07.techchallenge01.domain.provider.mapper.PedidoRepositoryMapper;
import br.com.fiap.soat07.techchallenge01.domain.usecase.PedidoUseCase;
import br.com.fiap.soat07.techchallenge01.infra.repository.PedidoRepository;
import br.com.fiap.soat07.techchallenge01.infra.repository.model.PedidoModel;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class PedidoProviderImpl implements PedidoUseCase {

	private final PedidoRepository repository;

	private final PedidoRepositoryMapper mapper;

	@Override
	public Page<Pedido> getPageable(Pageable pageable) {
		return new PageImpl<>(repository.findAll(pageable).stream().map(mapper::toDomain).toList(), pageable,
				repository.findAll(pageable).getNumberOfElements());

	}

	// TODO Criate specific exception
	@Override
	public Pedido getById(Long id) {
		Optional<PedidoModel> pedidoModel = this.repository.findById(id);
		return mapper.toDomain(pedidoModel.orElseThrow(() -> new RuntimeException()));
	}

	@Override
	public Pedido create(Pedido pedido) {
		return mapper.toDomain(repository.save(mapper.toModel(pedido)));
	}

	@Override
	public Pedido update(Long id, Pedido pedidoAtualizado) {
		Pedido pedido = getById(id);
		return mapper.toDomain(
				repository.save(PedidoModel.builder().id(pedido.getId()).nome(pedidoAtualizado.getNome()).build()));
	}

	@Override
	public void delete(Long id) {
		repository.deleteById(id);
	}

}
