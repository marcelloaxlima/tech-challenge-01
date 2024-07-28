package br.com.fiap.soat07.clean.infra.rest.mercadopago;

import br.com.fiap.soat07.clean.core.domain.entity.Pagamento;
import br.com.fiap.soat07.clean.core.domain.entity.Pedido;
import br.com.fiap.soat07.clean.core.domain.enumeration.PagamentoStatusEnum;
import br.com.fiap.soat07.clean.core.domain.enumeration.ProvedorPagamentoEnum;
import br.com.fiap.soat07.clean.core.exception.PagamentoNotFoundException;
import br.com.fiap.soat07.clean.core.exception.PedidoNotFoundException;
import br.com.fiap.soat07.clean.infra.rest.dto.PagamentoDTO;
import br.com.fiap.soat07.clean.infra.rest.mapper.PagamentoMapper;
import br.com.fiap.soat07.clean.infra.service.PagamentoService;
import br.com.fiap.soat07.clean.infra.service.PedidoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.transaction.Transactional;
import jakarta.websocket.server.PathParam;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.ErrorResponse;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/pagamento/mercadopago/")
@RequiredArgsConstructor
@Tag(name = "MercadoPago", description = "Endpoints específicos para o Mercado Pago")
public class MercadoPagoController {

    private final PagamentoService pagamentoService;
    private final PedidoService pedidoService;
    private final PagamentoMapper mapper;


    private String extractId(MercadoPagoPagamentoDTO pagamentoDTO) {
        return pagamentoDTO.getId().toString();
    }

    private MercadoPagoStatus extractPagamentoStatus(MercadoPagoPagamentoDTO pagamentoDTO) {
        return MercadoPagoStatus.parse(pagamentoDTO.getStatus()).orElseThrow(() -> new IllegalStateException("Status: "+pagamentoDTO.getStatus()+" não encontrado"));
    }


    @Operation(
            operationId = "webhook",
            description = "webhook do MercadoPago"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Status not changed"),
            @ApiResponse(responseCode = "400", description = "Invalid values"),
            @ApiResponse(responseCode = "500", description = "Internal server error", content =
                    { @Content(mediaType = "application/json", schema =
                    @Schema(implementation = ErrorResponse.class)) }) })
    @PostMapping(value="/webhook", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    @Transactional
    public ResponseEntity<PagamentoDTO> webhook(@RequestBody final MercadoPagoPagamentoDTO pagamentoDTO) {

        String id = extractId(pagamentoDTO);
        Pagamento pagamento = pagamentoService.getSearchPagamentoUseCase().findByProvedor(ProvedorPagamentoEnum.MERCADO_PAGO, id).orElseThrow(() -> new PagamentoNotFoundException(id));
        Pagamento finalPagamento = pagamento;
        Pedido pedido = pedidoService.getSearchPedidoUseCase().findById(pagamento.getPedidoId()).orElseThrow(() -> new PedidoNotFoundException(finalPagamento.getPedidoId()));

        PagamentoStatusEnum status = pagamento.getStatus();
        PagamentoStatusEnum statusAtual = extractPagamentoStatus(pagamentoDTO).getPagamentoStatus();
        if (status.equals(statusAtual))
            return ResponseEntity.noContent().build();

        pagamento = pagamentoService.getUpdatePagamentoUseCase().executar(pedido, pagamento, statusAtual);
        return ResponseEntity.ok(mapper.toDTO(pagamento));
    }


    @Operation(
            operationId = "consultar situação",
            description = "Consultar situação do pedido no MercadoPago"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Consulta", content =
                    { @Content(mediaType = "application/json", schema =
                    @Schema(implementation = String.class)) }),
            @ApiResponse(responseCode = "400", description = "Invalid values"),
            @ApiResponse(responseCode = "404", description = "Pedido not found"),
            @ApiResponse(responseCode = "500", description = "Internal server error", content =
                    { @Content(mediaType = "application/json", schema =
                    @Schema(implementation = ErrorResponse.class)) }) })
    @GetMapping(value="/{id}/situacao", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<PagamentoStatusEnum> consultarSituacaoDoPedido(@PathParam("id") final Long pedidoId) {

        if (pedidoId == null)
            return ResponseEntity.badRequest().build();
        Pedido pedido = pedidoService.getSearchPedidoUseCase().findById(pedidoId).orElseThrow(() -> new PedidoNotFoundException(pedidoId));
        Pagamento pagamento = pagamentoService.getSearchPagamentoUseCase().findByPedido(pedido).orElseThrow(() -> new PagamentoNotFoundException(""));


        PagamentoStatusEnum resultado = pagamentoService.getRetornoGatewayPagamentoUseCase().executar(pagamento);
        return ResponseEntity.ok(resultado);
    }

}
