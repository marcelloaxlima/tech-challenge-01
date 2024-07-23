package br.com.fiap.soat07.clean.core.usecase.combo;

import br.com.fiap.soat07.clean.core.domain.entity.Cliente;
import br.com.fiap.soat07.clean.core.domain.entity.Combo;
import br.com.fiap.soat07.clean.core.domain.entity.Produto;
import br.com.fiap.soat07.clean.core.exception.ClienteNotFoundException;
import br.com.fiap.soat07.clean.core.exception.ProdutoDuplicadoComboException;
import br.com.fiap.soat07.clean.core.exception.ProdutoNotFoundException;
import br.com.fiap.soat07.clean.core.gateway.ClienteGateway;
import br.com.fiap.soat07.clean.core.gateway.ComboGateway;
import br.com.fiap.soat07.clean.core.gateway.ProdutoGateway;
import br.com.fiap.soat07.clean.infra.rest.dto.CreateComboDTO;
import br.com.fiap.soat07.clean.infra.rest.dto.ProdutoDTO;
import br.com.fiap.soat07.clean.infra.rest.mapper.ProdutoMapper;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static br.com.fiap.soat07.clean.Utils.hasProdutoDuplicates;

@Component
public class CreateComboUseCase {

    private final ComboGateway comboGateway;
    private final ClienteGateway clienteGateway;
    private final ProdutoGateway produtoGateway;
//    private final ProdutoMapper produtoMapper;

    public CreateComboUseCase(final ComboGateway comboGateway, final ClienteGateway clienteGateway, final ProdutoGateway produtoGateway) {
        this.comboGateway = comboGateway;
        this.clienteGateway = clienteGateway;
        this.produtoGateway = produtoGateway;
//        this.produtoMapper = produtoMapper;
    }

//    public Combo execute(final ComboDTO comboDTO) {
//        if (comboDTO == null)
//            throw new IllegalArgumentException();
//
//        if (comboDTO.getProdutos() != null && hasDuplicates(comboDTO.getProdutos().stream().map(Produto::getTipoProduto).toList()))
//            throw new ProdutoDuplicadoComboException();
//
//        comboGateway.save(combo);
//        return combo;
//    }

    public Combo execute(final CreateComboDTO createComboDTO) {
        if (createComboDTO == null)
            throw new IllegalArgumentException();

        if (createComboDTO.getCliente() == null)
            throw new IllegalArgumentException("Cliente não informado");

        List<Produto> produtos = new ArrayList<>();
        if (createComboDTO.getProdutos() != null)
            for (ProdutoDTO dto : createComboDTO.getProdutos())
                produtos.add(produtoGateway.findById(dto.getId()).orElseThrow(() -> new ProdutoNotFoundException(dto.getId())));

        if (hasProdutoDuplicates(produtos))
            throw new ProdutoDuplicadoComboException();


        Cliente cliente = null;
        if (createComboDTO.getCliente().getId() != null && createComboDTO.getCliente().getId() != 0) {
            cliente = clienteGateway.findById(createComboDTO.getCliente().getId()).orElseThrow(() -> new ClienteNotFoundException(createComboDTO.getCliente().getId()));
        } else {
            if (createComboDTO.getCliente().getCpf() != null && !createComboDTO.getCliente().getCpf().isEmpty()) {
                Optional<Cliente> optional = clienteGateway.findByCpf(createComboDTO.getCliente().getCpf());
                if (optional.isPresent()) {
                    cliente = optional.get();
                } else {
                    cliente = new Cliente();
                    cliente.setNome(createComboDTO.getCliente().getNome());
                    cliente.setCpf(createComboDTO.getCliente().getCpf());
                }
            } else {
                cliente = new Cliente();
                cliente.setNome(createComboDTO.getCliente().getNome());
                cliente.setCpf(createComboDTO.getCliente().getCpf());
            }
        }

        cliente = clienteGateway.save(cliente);
        Combo combo = new Combo();
        combo.setCliente(cliente);
        for (Produto p : produtos)
            combo.getProdutos().add(p);

        comboGateway.save(combo);
        return combo;
    }

}
