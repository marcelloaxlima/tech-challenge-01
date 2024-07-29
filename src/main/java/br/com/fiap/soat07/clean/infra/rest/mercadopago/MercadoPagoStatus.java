package br.com.fiap.soat07.clean.infra.rest.mercadopago;

import br.com.fiap.soat07.clean.core.domain.enumeration.PagamentoStatusEnum;

import java.util.Optional;

public enum MercadoPagoStatus {
    PENDENTE("Pendente", PagamentoStatusEnum.NAO_CONCLUIDO),
    REJEITADO("Rejeitado", PagamentoStatusEnum.RECUSADO),
    APROVADO("Aprovado", PagamentoStatusEnum.PAGO);

    private final String description;
    private final PagamentoStatusEnum pagamentoStatus;

    private MercadoPagoStatus(String description, PagamentoStatusEnum pagamentoStatus) {
        this.description = description;
        this.pagamentoStatus = pagamentoStatus;
    }

    public String getDescription() {
        return description;
    }

    public PagamentoStatusEnum getPagamentoStatus() {
        return pagamentoStatus;
    }

    public static Optional<MercadoPagoStatus> parse(String value) {
        if (value == null || value.isEmpty())
            return Optional.empty();

        for (MercadoPagoStatus v : values())
            if (v.description.equalsIgnoreCase(value))
                return Optional.of(v);

        return Optional.empty();
    }

}
