package br.com.fiap.soat07.techchallenge01.domain.enumeration;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public enum PedidoStatusEnum {
	
	INICIADO(0), PAGO(1), PREPARO(2), PRONTO(3), FINALIZADO(4);
	
	private int step;
	
	PedidoStatusEnum(int step){
		this.step = step;
	}

}
