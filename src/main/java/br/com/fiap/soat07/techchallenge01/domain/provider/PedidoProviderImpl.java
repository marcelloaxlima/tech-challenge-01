package br.com.fiap.soat07.techchallenge01.domain.provider;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

import br.com.fiap.soat07.techchallenge01.domain.entity.Combo;
import br.com.fiap.soat07.techchallenge01.domain.entity.Pedido;
import br.com.fiap.soat07.techchallenge01.domain.entity.Produto;
import br.com.fiap.soat07.techchallenge01.domain.enumeration.PedidoStatusEnum;
import br.com.fiap.soat07.techchallenge01.domain.exception.PedidoNotFoundException;
import br.com.fiap.soat07.techchallenge01.domain.usecase.CreatePedidoUseCase;
import br.com.fiap.soat07.techchallenge01.domain.usecase.PedidoUseCase;
import br.com.fiap.soat07.techchallenge01.infra.repository.ComboRepository;
import br.com.fiap.soat07.techchallenge01.infra.repository.CustomPedidoProdutosRepository;
import br.com.fiap.soat07.techchallenge01.infra.repository.PedidoRepository;
import br.com.fiap.soat07.techchallenge01.infra.repository.mapper.PedidoRepositoryMapper;
import br.com.fiap.soat07.techchallenge01.infra.repository.mapper.ProdutoRepositoryMapper;
import br.com.fiap.soat07.techchallenge01.infra.repository.model.ComboModel;
import br.com.fiap.soat07.techchallenge01.infra.repository.model.PedidoModel;
import br.com.fiap.soat07.techchallenge01.infra.repository.model.ProdutoModel;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class PedidoProviderImpl implements PedidoUseCase, CreatePedidoUseCase {

	private final PedidoRepository pedidoRepository;
	
	private final CustomPedidoProdutosRepository customPedidoProdutosRepository;

	private final ComboRepository comboRepository;

	private final PedidoRepositoryMapper pedidoRepositoryMapper;
	
	private final ProdutoRepositoryMapper produtoRepositoryMapper;


	@Override
	public Page<Pedido> getPageable(Pageable pageable) {
		return new PageImpl<>(pedidoRepository.findAll(pageable).stream().map(pedidoRepositoryMapper::toDomain).toList(), pageable,
				pedidoRepository.findAll(pageable).getNumberOfElements());

	}

	@Override
	public Pedido getById(Long id) {
		Optional<PedidoModel> pedidoModel = this.pedidoRepository.findById(id);
		return pedidoRepositoryMapper.toDomain(pedidoModel.orElseThrow(() -> new PedidoNotFoundException(id)));
	}

	@Override
	@Transactional
	public Pedido create(Pedido pedido) {
		Combo combo = pedido.getCombos().stream().findAny().orElseGet(null);
		if(null != combo){
			pedido.setNomeCliente(combo.getCliente().getNome());
		}
		PedidoModel pedidoModel = pedidoRepositoryMapper.toModel(pedido);
		List<Produto> produtos = combo.getProdutos();
		List<ProdutoModel> produtoModels = produtos.stream().map(produtoRepositoryMapper::toModel).collect(Collectors.toList());
		pedidoModel.getProdutos().addAll(produtoModels);
		return pedidoRepositoryMapper.toDomain(pedidoRepository.save(pedidoModel));
	}

	@Override
	@Transactional
	public Pedido update(Long id, Pedido pedido) {

		this.pedidoRepository.findById(id).orElseThrow(() -> new PedidoNotFoundException(id));
				
		List<Long> produtoIds = customPedidoProdutosRepository.getProdutosByPedidoId(id);
				
		customPedidoProdutosRepository.delete(id, produtoIds);

		PedidoModel pedidoModel = pedidoRepositoryMapper.toModel(pedido);
		List<Produto> produtosTemp = new ArrayList<>();   
		pedido.getCombos().stream().forEach(c -> produtosTemp.addAll(c.getProdutos()));
		produtosTemp.addAll(pedido.getProdutos());
		List<ProdutoModel> produtoModels = produtosTemp.stream().map(produtoRepositoryMapper::toModel).collect(Collectors.toList());
		pedidoModel.setProdutos(produtoModels);
		return pedidoRepositoryMapper.toDomain(pedidoRepository.save(pedidoModel));
	}
	
	@Override
	public Pedido updateStatus(Long id, PedidoStatusEnum status) {
		Optional<PedidoModel> pedidoModelOption = this.pedidoRepository.findById(id);
		PedidoModel pedidoModel = pedidoModelOption.orElseThrow(() -> new PedidoNotFoundException(id));
		
		pedidoModel.setStatus(status);
		
		return pedidoRepositoryMapper.toDomain(
				pedidoRepository.save(pedidoModel));
	}

	@Override
	public void delete(Long id) {
		this.pedidoRepository.findById(id).orElseThrow(() -> new PedidoNotFoundException(id));
		pedidoRepository.deleteById(id);
	}

	@Override
	public Pedido create(Long id) {
		if (id == null)
			throw new IllegalArgumentException("Obrigatório informar o código do Combo");

		ComboModel combo = comboRepository.findById(id).orElseThrow(() -> new PedidoNotFoundException(id));
		PedidoModel pedidoModel = new PedidoModel();
		for(ProdutoModel produto : combo.getProdutos()) {
			pedidoModel.getProdutos().add(produto);
		}

        return pedidoRepositoryMapper.toDomain(pedidoRepository.save(pedidoModel));
	}


}
