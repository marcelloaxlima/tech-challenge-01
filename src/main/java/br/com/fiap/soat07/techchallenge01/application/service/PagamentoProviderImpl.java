package br.com.fiap.soat07.techchallenge01.application.service;

import org.springframework.stereotype.Component;

import br.com.fiap.soat07.techchallenge01.application.domain.entity.Pagamento;
import br.com.fiap.soat07.techchallenge01.application.domain.enumeration.PagamentoStatusEnum;
import br.com.fiap.soat07.techchallenge01.application.domain.enumeration.PedidoStatusEnum;
import br.com.fiap.soat07.techchallenge01.application.exception.PedidoNotFoundException;
import br.com.fiap.soat07.techchallenge01.application.ports.out.pagamento.SistemaPagamento;
import br.com.fiap.soat07.techchallenge01.application.ports.in.PagamentoUseCase;
import br.com.fiap.soat07.techchallenge01.application.ports.out.persistence.PedidoRepository;
import br.com.fiap.soat07.techchallenge01.adapter.out.persistence.mysql.model.PedidoModel;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class PagamentoProviderImpl implements PagamentoUseCase {

	private final PedidoRepository pedidoRepository;

	private final SistemaPagamento sistemaPagamento;
	
	@Override
	public Pagamento executar(Pagamento pagamento) {
		PedidoModel pedidoModel = pedidoRepository.findById(pagamento.getPedidoId()).orElseThrow(() -> new PedidoNotFoundException(pagamento.getPedidoId()));
				
		try {			
			PagamentoStatusEnum status = sistemaPagamento.executar(pagamento);
			pagamento.setStatus(status);
			pedidoModel.setStatus(status.equals(PagamentoStatusEnum.PAGO) ? PedidoStatusEnum.PAGO : pedidoModel.getStatus());
			pedidoRepository.save(pedidoModel);
			return pagamento;
		}catch(Exception e) {
			pagamento.setStatus(PagamentoStatusEnum.NAO_CONCLUIDO);
			return pagamento;
		}
	}

}
