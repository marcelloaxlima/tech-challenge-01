package br.com.fiap.soat07.clean.core.usecase.combo;

import br.com.fiap.soat07.clean.core.domain.entity.Cliente;
import br.com.fiap.soat07.clean.core.domain.entity.Combo;
import br.com.fiap.soat07.clean.core.gateway.ClienteGateway;
import br.com.fiap.soat07.clean.core.gateway.ComboGateway;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
public class DeleteComboUseCase {

    private final ComboGateway comboGateway;

    public DeleteComboUseCase(final ComboGateway comboGateway) {
        this.comboGateway = comboGateway;
    }

    public void execute(final Combo combo) {
        if (combo == null)
            throw new IllegalArgumentException();

        comboGateway.delete(combo);
    }

}
