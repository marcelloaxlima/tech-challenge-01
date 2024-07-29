package br.com.fiap.soat07.clean.core.usecase.combo;

import br.com.fiap.soat07.clean.Utils;
import br.com.fiap.soat07.clean.core.domain.entity.Combo;
import br.com.fiap.soat07.clean.core.domain.entity.Produto;
import br.com.fiap.soat07.clean.core.exception.ProdutoDuplicadoComboException;
import br.com.fiap.soat07.clean.core.gateway.ComboGateway;
import org.springframework.stereotype.Component;

import java.util.Collection;

import static br.com.fiap.soat07.clean.Utils.hasProdutoDuplicates;

@Component
public class UpdateComboUseCase {

    private final ComboGateway comboGateway;

    public UpdateComboUseCase(ComboGateway comboGateway) {
        this.comboGateway = comboGateway;
    }

    public Combo execute(Combo combo, Collection<Produto> produtos) {

        for (Produto produto : produtos)
            combo.getProdutos().add(produto);

        if (hasProdutoDuplicates(produtos))
            throw new ProdutoDuplicadoComboException();

        combo.setUltimaModificacao(Utils.now());

        comboGateway.save(combo);
        return combo;
    }

}
