package br.com.fiap.soat07.clean.core.usecase.combo;

import br.com.fiap.soat07.clean.core.domain.entity.Combo;
import br.com.fiap.soat07.clean.core.domain.entity.Produto;
import br.com.fiap.soat07.clean.core.domain.enumeration.TipoProdutoEnum;
import br.com.fiap.soat07.clean.core.gateway.ComboGateway;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.Optional;

@Component
public class AddProdutoToComboUseCase {

    private final ComboGateway comboGateway;

    public AddProdutoToComboUseCase(final ComboGateway comboGateway) {
        this.comboGateway = comboGateway;
    }

    public void execute(final Combo combo, final Produto produto) {
        if (combo == null)
            throw new IllegalArgumentException();
        if (produto == null)
            throw new IllegalArgumentException();

        Optional<TipoProdutoEnum> optional = combo.getProdutos().stream()
                .map(Produto::getTipoProduto)
                .filter(t -> t.equals(produto.getTipoProduto()))
                .findFirst();
        if (optional.isPresent())
            throw new IllegalArgumentException("Já existe um produto do tipo: "+produto.getTipoProduto()+" no combo");

        combo.getProdutos().add(produto);
        BigDecimal valorCombo = combo.getProdutos().stream().map(Produto::getValor).reduce(BigDecimal.ZERO, BigDecimal::add);


        comboGateway.save(combo);
    }

}
