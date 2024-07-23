package br.com.fiap.soat07.clean.infra.rest;

import br.com.fiap.soat07.clean.core.domain.entity.Pagamento;
import br.com.fiap.soat07.clean.core.domain.entity.Pedido;
import br.com.fiap.soat07.clean.core.domain.enumeration.PagamentoStatusEnum;
import br.com.fiap.soat07.clean.core.exception.ComboNotFoundException;
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
@RequestMapping("/pagamento")
@RequiredArgsConstructor
@Tag(name = "Pagamento", description = "Pagamento")
public class PagamentoController {

    private final PagamentoService pagamentoService;
    private final PedidoService pedidoService;
    private final PagamentoMapper mapper;

//    @Operation(
//    		operationId = "executar",
//    		description = "Executar pagamento",
//    		tags = {"Pagamento"}
//    		)
//    @ApiResponses(value = {
//            @ApiResponse(responseCode = "201", description = "Created", content =
//              { @Content(mediaType = "application/json", schema =
//                @Schema(implementation = PagamentoDTO.class)) }),
//            @ApiResponse(responseCode = "400", description = "Invalid values"),
//            @ApiResponse(responseCode = "404", description = "Produto already exists"),
//            @ApiResponse(responseCode = "500", description = "Internal server error", content =
//              { @Content(mediaType = "application/json", schema =
//                @Schema(implementation = ErrorResponse.class)) }) })
//    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
//    public ResponseEntity<PagamentoDTO> createPagamento(@RequestBody final PagamentoDTO pagamentoDTO) {
//
//    	return ResponseEntity.ok(mapper.toDTO(pagamentoService.getPagamentoUseCase().executar(mapper.toDomain(pagamentoDTO))));
//
//    }

    @Operation(
            operationId = "criar",
            description = "Criar pagamento",
            tags = {"Pedido"}
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Created", content =
                    { @Content(mediaType = "application/json", schema =
                    @Schema(implementation = PagamentoDTO.class)) }),
            @ApiResponse(responseCode = "400", description = "Invalid values"),
            @ApiResponse(responseCode = "404", description = "Produto already exists"),
            @ApiResponse(responseCode = "500", description = "Internal server error", content =
                    { @Content(mediaType = "application/json", schema =
                    @Schema(implementation = ErrorResponse.class)) }) })
    @PostMapping(value="/{id}/pagamento", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    @Transactional
    public ResponseEntity<PagamentoDTO> createPagamento(@PathParam("id") final Long id, @RequestBody final PagamentoDTO pagamentoDTO) {
        if (id == null)
            return ResponseEntity.badRequest().build();
        if (pagamentoDTO == null)
            return ResponseEntity.badRequest().build();
        if (pagamentoDTO.getProvedorServico() == null)
            return ResponseEntity.badRequest().build();
        if (pagamentoDTO.getMetodoPagamento() == null)
            return ResponseEntity.badRequest().build();

        Pedido pedido = pedidoService.getSearchPedidoUseCase().findById(id).orElseThrow(() -> new PedidoNotFoundException(id));
        Pagamento resultado = pagamentoService.getCreatePagamentoUseCase().executar(pedido, pagamentoDTO.getProvedorServico(), pagamentoDTO.getMetodoPagamento());

        return ResponseEntity.ok(mapper.toDTO(resultado));
    }


    @Operation(
            operationId = "consultar situação",
            description = "Consultar situação do pedido",
            tags = {"Pedido"}
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
    public ResponseEntity<PagamentoStatusEnum> consultarSituacaoDoPedido(@PathParam("id") final Long id) {

        if (id == null)
            return ResponseEntity.badRequest().build();
        Pedido pedido = pedidoService.getSearchPedidoUseCase().findById(id).orElseThrow(() -> new PedidoNotFoundException(id));

        PagamentoStatusEnum resultado = pagamentoService.getRetornoGatewayPagamentoUseCase().executar(pedido);
        return ResponseEntity.ok(resultado);
    }

}
