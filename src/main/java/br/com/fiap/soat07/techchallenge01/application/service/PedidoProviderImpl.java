package br.com.fiap.soat07.techchallenge01.application.service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

import br.com.fiap.soat07.techchallenge01.application.domain.entity.Combo;
import br.com.fiap.soat07.techchallenge01.application.domain.entity.Pedido;
import br.com.fiap.soat07.techchallenge01.application.domain.entity.Produto;
import br.com.fiap.soat07.techchallenge01.application.domain.enumeration.PedidoStatusEnum;
import br.com.fiap.soat07.techchallenge01.application.exception.ComboNotFoundException;
import br.com.fiap.soat07.techchallenge01.application.exception.PedidoNotFoundException;
import br.com.fiap.soat07.techchallenge01.application.ports.in.CreatePedidoUseCase;
import br.com.fiap.soat07.techchallenge01.application.ports.in.PedidoUseCase;
import br.com.fiap.soat07.techchallenge01.application.ports.out.persistence.ComboRepository;
import br.com.fiap.soat07.techchallenge01.application.ports.out.persistence.CustomComboProdutosRepository;
import br.com.fiap.soat07.techchallenge01.application.ports.out.persistence.CustomPedidoProdutosRepository;
import br.com.fiap.soat07.techchallenge01.application.ports.out.persistence.PedidoRepository;
import br.com.fiap.soat07.techchallenge01.adapter.out.persistence.mysql.mapper.PedidoRepositoryMapper;
import br.com.fiap.soat07.techchallenge01.adapter.out.persistence.mysql.mapper.ProdutoRepositoryMapper;
import br.com.fiap.soat07.techchallenge01.adapter.out.persistence.mysql.model.ClienteModel;
import br.com.fiap.soat07.techchallenge01.adapter.out.persistence.mysql.model.ComboModel;
import br.com.fiap.soat07.techchallenge01.adapter.out.persistence.mysql.model.PedidoModel;
import br.com.fiap.soat07.techchallenge01.adapter.out.persistence.mysql.model.ProdutoModel;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class PedidoProviderImpl implements PedidoUseCase, CreatePedidoUseCase {

	private final PedidoRepository pedidoRepository;
	private final CustomPedidoProdutosRepository customPedidoProdutosRepository;
	private final CustomComboProdutosRepository customComboProdutosRepository;
	private final ComboRepository comboRepository;
	private final PedidoRepositoryMapper pedidoRepositoryMapper;
	private final ProdutoRepositoryMapper produtoRepositoryMapper;

	@Override
	public Page<Pedido> getPageable(Pageable pageable) {
		return new PageImpl<>(
			pedidoRepository.findAll(pageable)
				.stream()
				.map(pedidoRepositoryMapper::toDomain)
				.toList(), 
			pageable,
			pedidoRepository.findAll(pageable).getNumberOfElements()
		);
	}

	@Override
	public Pedido getById(Long id) {
		Optional<PedidoModel> pedidoModel = this.pedidoRepository.findById(id);
		return pedidoRepositoryMapper.toDomain(pedidoModel.orElseThrow(() -> new PedidoNotFoundException(id)));
	}

	@Override
	@Transactional
	public Pedido create(Pedido pedido) {
		List<Long> comboIds = pedido.getCombos().stream().map(Combo::getId).collect(Collectors.toList());

		List<ComboModel> combos = comboIds.stream()
			.map(comboId -> comboRepository.findById(comboId).orElseThrow(() -> new ComboNotFoundException(comboId)))
			.collect(Collectors.toList());

		List<ProdutoModel> produtos = comboIds.stream()
			.map(comboId -> customComboProdutosRepository.getProdutosByComboId(comboId))
			.flatMap(List::stream)
			.collect(Collectors.toList());

		String comboId = null;
		ClienteModel cliente = null;
		
		for (ComboModel combo : combos) {
			cliente = combo.getCliente();
			comboId = String.valueOf(combo.getId());
		}

		BigDecimal valorPedido = produtos.stream().map(ProdutoModel::getValor).reduce(BigDecimal.ZERO, BigDecimal::add);
		PedidoModel pedidoModel = pedidoRepositoryMapper.toModel(pedido);

		if (cliente != null && !cliente.getNome().isBlank()) {
			pedidoModel.setNomeCliente(cliente.getNome());
		}

		pedidoModel.setProdutos(produtos);
		pedidoModel.setStatus(PedidoStatusEnum.INICIADO);
		pedidoModel.setCodigo(comboId);
		pedidoModel.setValor(valorPedido);

		PedidoModel pedidoCriado = pedidoRepository.save(pedidoModel);

		return pedidoRepositoryMapper.toDomain(pedidoCriado);
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
