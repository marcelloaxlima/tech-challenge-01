package br.com.fiap.soat07.techchallenge01.application.mapper;

import org.mapstruct.Mapper;

import br.com.fiap.soat07.techchallenge01.application.model.dto.PagamentoDTO;
import br.com.fiap.soat07.techchallenge01.domain.entity.Pagamento;

@Mapper (componentModel = "spring")
public interface PagamentoMapper {
	
	/**
	 * To domain mapper
	 * @param Pagamento
	 * @return {@link Pagamento}
	 */
	Pagamento toDomain(PagamentoDTO pagamentoDTO);
	
	/**
	 * To model mapper
	 * @param pagamento
	 * @return {@link PagamentoDTO}
	 */
	PagamentoDTO toDTO(Pagamento pagamento);

}
