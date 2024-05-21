package br.com.fiap.soat07.techchallenge01.domain.provider;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

import br.com.fiap.soat07.techchallenge01.domain.entity.Combo;
import br.com.fiap.soat07.techchallenge01.domain.entity.Pedido;
import br.com.fiap.soat07.techchallenge01.domain.enumeration.PedidoStatusEnum;
import br.com.fiap.soat07.techchallenge01.domain.provider.mapper.ComboRepositoryMapper;
import br.com.fiap.soat07.techchallenge01.domain.provider.mapper.PedidoRepositoryMapper;
import br.com.fiap.soat07.techchallenge01.domain.provider.mapper.ProdutoRepositoryMapper;
import br.com.fiap.soat07.techchallenge01.domain.usecase.PedidoUseCase;
import br.com.fiap.soat07.techchallenge01.infra.repository.PedidoRepository;
import br.com.fiap.soat07.techchallenge01.infra.repository.model.ComboModel;
import br.com.fiap.soat07.techchallenge01.infra.repository.model.PedidoModel;
import br.com.fiap.soat07.techchallenge01.infra.repository.model.ProdutoModel;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class PedidoProviderImpl implements PedidoUseCase {

	private final PedidoRepository repository;

	private final PedidoRepositoryMapper pedidoRepositoryMapper;
	
	private final ComboRepositoryMapper comboRepositoryMapper;
	
	private final ProdutoRepositoryMapper produtoRepositoryMapper;

	@Override
	public Page<Pedido> getPageable(Pageable pageable) {
		return new PageImpl<>(repository.findAll(pageable).stream().map(pedidoRepositoryMapper::toDomain).toList(), pageable,
				repository.findAll(pageable).getNumberOfElements());

	}

	// TODO Criate specific exception
	@Override
	public Pedido getById(Long id) {
		Optional<PedidoModel> pedidoModel = this.repository.findById(id);
		return pedidoRepositoryMapper.toDomain(pedidoModel.orElseThrow(() -> new RuntimeException()));
	}

	@Override
	public Pedido create(Pedido pedido) {
		Combo combo = pedido.getCombos().stream().findAny().orElseGet(null);
		if(null != combo){
			pedido.setNomeCliente(combo.getCliente().getNome());
		}
	
		return pedidoRepositoryMapper.toDomain(repository.save(pedidoRepositoryMapper.toModel(pedido)));
	}

	@Override
	public Pedido update(Long id, Pedido pedidoAtualizado) {
		Optional<PedidoModel> pedidoModelOption = this.repository.findById(id);
		PedidoModel pedidoModelAtual = pedidoModelOption.orElseThrow(() -> new RuntimeException());
		
		List<ComboModel> comboModels = pedidoAtualizado.getCombos().stream().map(comboRepositoryMapper::toModel).toList();
		List<ProdutoModel> produtoModels = pedidoAtualizado.getProdutos().stream().map(produtoRepositoryMapper::toModel).toList();
		
		PedidoModel pedidoModelAtualizado = PedidoModel.builder()
				.codigo(pedidoAtualizado.getCodigo())
				.id(pedidoAtualizado.getId())
				.combos(comboModels)
				.dataCriacao(pedidoModelAtual.getDataCriacao())
				.nomeCliente(pedidoAtualizado.getNomeCliente())
				.status(pedidoAtualizado.getStatus())
				.produtos(produtoModels)
				.build();
	
		
		return pedidoRepositoryMapper.toDomain(
				repository.save(pedidoModelAtualizado));
	}
	
	@Override
	public Pedido updateStatus(Long id, PedidoStatusEnum status) {
		Optional<PedidoModel> pedidoModelOption = this.repository.findById(id);
		PedidoModel pedidoModel = pedidoModelOption.orElseThrow(() -> new RuntimeException());
		
		pedidoModel.setStatus(status);
		
		return pedidoRepositoryMapper.toDomain(
				repository.save(pedidoModel));
	}

	@Override
	public void delete(Long id) {
		repository.deleteById(id);
	}

}
