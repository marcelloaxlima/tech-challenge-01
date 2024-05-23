package br.com.fiap.soat07.techchallenge01.domain.provider;

import java.util.Optional;

import org.springframework.stereotype.Component;

import br.com.fiap.soat07.techchallenge01.domain.entity.Pagamento;
import br.com.fiap.soat07.techchallenge01.domain.enumeration.PagamentoStatusEnum;
import br.com.fiap.soat07.techchallenge01.domain.enumeration.PedidoStatusEnum;
import br.com.fiap.soat07.techchallenge01.domain.port.outbound.SistemaPagamento;
import br.com.fiap.soat07.techchallenge01.domain.usecase.PagamentoUseCase;
import br.com.fiap.soat07.techchallenge01.infra.repository.PedidoRepository;
import br.com.fiap.soat07.techchallenge01.infra.repository.model.PedidoModel;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class PagamentoProviderImpl implements PagamentoUseCase {

	private final PedidoRepository pedidoRepository;

	private final SistemaPagamento sistemaPagamento;
	
	@Override
	public Pagamento executar(Pagamento pagamento) {
		Optional<PedidoModel> pedidoModelOption = this.pedidoRepository.findById(pagamento.getPedidoId());
		PedidoModel pedidoModel = pedidoModelOption.orElseThrow(() -> new RuntimeException());
				
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
