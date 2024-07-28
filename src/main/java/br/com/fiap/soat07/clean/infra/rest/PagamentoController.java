package br.com.fiap.soat07.clean.infra.rest;

import br.com.fiap.soat07.clean.core.domain.entity.Pagamento;
import br.com.fiap.soat07.clean.core.domain.entity.Pedido;
import br.com.fiap.soat07.clean.core.domain.enumeration.MetodoPagamentoEnum;
import br.com.fiap.soat07.clean.core.domain.enumeration.PagamentoStatusEnum;
import br.com.fiap.soat07.clean.core.domain.enumeration.ProvedorPagamentoEnum;
import br.com.fiap.soat07.clean.core.exception.ComboNotFoundException;
import br.com.fiap.soat07.clean.core.exception.PagamentoNotFoundException;
import br.com.fiap.soat07.clean.core.exception.PedidoNotFoundException;
import br.com.fiap.soat07.clean.infra.service.PagamentoService;
import br.com.fiap.soat07.clean.infra.service.PedidoService;
import jakarta.transaction.Transactional;
import jakarta.websocket.server.PathParam;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.ErrorResponse;
import org.springframework.web.bind.annotation.*;

import br.com.fiap.soat07.clean.infra.rest.mapper.PagamentoMapper;
import br.com.fiap.soat07.clean.infra.rest.dto.PagamentoDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;

import java.util.Optional;

@RestController
@RequestMapping("/pedido")
@RequiredArgsConstructor
@Tag(name = "Pagamento", description = "Pagamento")
public class PagamentoController {

    private final PagamentoService pagamentoService;
    private final PedidoService pedidoService;
    private final PagamentoMapper mapper;

    @Operation(
            operationId = "criar",
            description = "Criar pagamento",
            tags = {"Pagamento"}
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Created", content =
                    { @Content(mediaType = "application/json", schema =
                    @Schema(implementation = PagamentoDTO.class)) }),
            @ApiResponse(responseCode = "400", description = "Invalid values"),
            @ApiResponse(responseCode = "500", description = "Internal server error", content =
                    { @Content(mediaType = "application/json", schema =
                    @Schema(implementation = ErrorResponse.class)) }) })
    @PostMapping(value="/{id}/pagamento")
    @Transactional
    public ResponseEntity<PagamentoDTO> createPagamento(@PathVariable final Long id) {
        if (id == null)
            return ResponseEntity.badRequest().build();

        Pedido pedido = pedidoService.getSearchPedidoUseCase().findById(id).orElseThrow(() -> new PedidoNotFoundException(id));
        Pagamento resultado = pagamentoService.getCreatePagamentoUseCase().executar(pedido, ProvedorPagamentoEnum.MERCADO_PAGO, MetodoPagamentoEnum.QRCODE);

        return ResponseEntity.ok(mapper.toDTO(resultado));
    }


    @Operation(
            operationId = "consultar situação",
            description = "Consultar situação do pedido",
            tags = {"Pagamento"}
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
    public ResponseEntity<PagamentoStatusEnum> consultarSituacaoDoPedido(@PathVariable final Long id) {

        if (id == null)
            return ResponseEntity.badRequest().build();
        Pedido pedido = pedidoService.getSearchPedidoUseCase().findById(id).orElseThrow(() -> new PedidoNotFoundException(id));
        Pagamento pagamento = pagamentoService.getSearchPagamentoUseCase().findByPedido(pedido).orElseThrow(() -> new PagamentoNotFoundException(""));

        PagamentoStatusEnum resultado = pagamentoService.getRetornoGatewayPagamentoUseCase().executar(pagamento);
        return ResponseEntity.ok(resultado);
    }


    @Operation(
            operationId = "QRCode para pagamento",
            description = "Criar QRCOde para pagamento",
            tags = {"Pagamento"}
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
    @GetMapping(value="/{id}/qrcode", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> qrcode(@PathVariable final Long id) {

        if (id == null)
            return ResponseEntity.badRequest().build();
        Pedido pedido = pedidoService.getSearchPedidoUseCase().findById(id).orElseThrow(() -> new PedidoNotFoundException(id));
        Pagamento pagamento = pagamentoService.getSearchPagamentoUseCase().findByPedido(pedido).orElseThrow(() -> new PagamentoNotFoundException("Não existem informações de pagamento registradas para esse pedido"));

        if (PagamentoStatusEnum.PAGO.equals(pagamento.getStatus()))
            return ResponseEntity.badRequest().body("Pagamento já processado");

        if (PagamentoStatusEnum.RECUSADO.equals(pagamento.getStatus()))
            return ResponseEntity.badRequest().body("Pagamento recusado");

        String qrCode = pagamentoService.getRetornoGatewayPagamentoUseCase().qrcode(pagamento);
        return ResponseEntity.ok(qrCode);
    }

}
