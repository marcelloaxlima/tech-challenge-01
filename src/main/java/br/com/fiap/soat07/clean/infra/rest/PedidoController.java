package br.com.fiap.soat07.clean.infra.rest;

import br.com.fiap.soat07.clean.core.domain.entity.Combo;
import br.com.fiap.soat07.clean.core.domain.entity.Pedido;
import br.com.fiap.soat07.clean.core.domain.enumeration.PedidoStatusEnum;
import br.com.fiap.soat07.clean.core.exception.ComboNotFoundException;
import br.com.fiap.soat07.clean.core.exception.PedidoNotFoundException;
import br.com.fiap.soat07.clean.infra.service.ComboService;
import br.com.fiap.soat07.clean.infra.service.PedidoService;
import br.com.fiap.soat07.clean.infra.rest.dto.PedidoDTO;
import br.com.fiap.soat07.clean.infra.rest.mapper.PedidoMapper;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.ErrorResponse;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/pedido")
@RequiredArgsConstructor
@Tag(name = "Pedido", description = "Pedido")
public class PedidoController {

    private final PedidoService pedidoService;
    private final ComboService comboService;
    private final PedidoMapper mapper;

    @Operation(
    		operationId = "criar",
    		description = "Criar pedido, com base num combo",
    		tags = {"Pedido"}    		
    		)
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Created", content = 
              { @Content(mediaType = "application/json", schema =
              @Schema(implementation = PedidoDTO.class)) }),
            @ApiResponse(responseCode = "400", description = "Invalid ID supplied"),
            @ApiResponse(responseCode = "404", description = "Combo not found"),
            @ApiResponse(responseCode = "500", description = "Internal server error", content = 
              { @Content(mediaType = "application/json", schema = 
                @Schema(implementation = ErrorResponse.class)) }) })
    @PostMapping(value = "/{id}")
    @Transactional
    public ResponseEntity<PedidoDTO> createPedido(@PathVariable final Long id) {

        Combo combo = comboService.getSearchComboUseCase().findById(id).orElseThrow(() -> new ComboNotFoundException(id));
        Pedido pedido = pedidoService.getCreatePedidoUseCase().execute(combo);
    	return ResponseEntity.ok(mapper.toDTO(pedido));
	
    }

    @Operation(
    		operationId = "atualizar",
    		description = "Atualizar pedido",
    		tags = {"Pedido"}    		
    		)
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Ok", content = 
              { @Content(mediaType = "application/json", schema = 
                @Schema(implementation = PedidoDTO.class)) }),
            @ApiResponse(responseCode = "400", description = "Invalid ID supplied"), 
            @ApiResponse(responseCode = "404", description = "Pedido not found"),
            @ApiResponse(responseCode = "500", description = "Internal server error", content = 
              { @Content(mediaType = "application/json", schema = 
                @Schema(implementation = ErrorResponse.class)) }) })
    @PutMapping(value = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
    @Transactional
    public ResponseEntity<PedidoDTO> updatePedido(@PathVariable final Long id, @RequestBody final PedidoDTO pedidoDTO) {
        Optional<Pedido> pedidoOp = pedidoService.getSearchPedidoUseCase().findById(id);
        if (pedidoOp.isEmpty()) {
            return ResponseEntity.badRequest().build();
        }

        Pedido atualizacoes = mapper.toDomain(pedidoDTO);
    	return ResponseEntity.ok(mapper.toDTO(pedidoService.getUpdatePedidoUseCase().execute(pedidoOp.get(), atualizacoes)));
    	
    }
    
    @Operation(
    		operationId = "atualizar status",
    		description = "Atualizar status dopedido",
    		tags = {"Pedido"}    		
    		)
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Ok", content = 
              { @Content(mediaType = "application/json", schema = 
                @Schema(implementation = PedidoDTO.class)) }),
            @ApiResponse(responseCode = "400", description = "Invalid ID supplied"), 
            @ApiResponse(responseCode = "404", description = "Pedido not found"),
            @ApiResponse(responseCode = "500", description = "Internal server error", content = 
              { @Content(mediaType = "application/json", schema = 
                @Schema(implementation = ErrorResponse.class)) }) })
    @PatchMapping(value = "/{id}")
    @Transactional
    public ResponseEntity<PedidoDTO> updatePedidoStatus(@PathVariable final Long id, @RequestParam final PedidoStatusEnum status) {
        if (id == null || status == null)
            return ResponseEntity.badRequest().build();
        Pedido pedido = pedidoService.getSearchPedidoUseCase().findById(id).orElseThrow(() -> new ComboNotFoundException(id));

    	return ResponseEntity.ok(mapper.toDTO(pedidoService.getUpdateStatusPedidoUseCase().execute(pedido, status)));
    }

    @Operation(
    		operationId = "deletar",
    		description = "Deletar pedido",
    		tags = {"Pedido"}    		
    		)
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Deleted", content = 
              { @Content(mediaType = "application/json") }),
            @ApiResponse(responseCode = "400", description = "Invalid ID supplied"), 
            @ApiResponse(responseCode = "404", description = "Pedido not found"),
            @ApiResponse(responseCode = "500", description = "Internal server error", content = 
              { @Content(mediaType = "application/json", schema = 
                @Schema(implementation = ErrorResponse.class)) }) })
    @DeleteMapping(value = "/{id}")
    @Transactional
    public ResponseEntity<?> deletePedido(@PathVariable final Long id) {
        if (id == null)
            return ResponseEntity.badRequest().build();

        Optional<Pedido> pedidoOp = pedidoService.getSearchPedidoUseCase().findById(id);
        if (pedidoOp.isEmpty())
            return ResponseEntity.ok().build();

        pedidoService.getDeletePedidoUseCase().execute(pedidoOp.get());
        return ResponseEntity.ok().build();
    }

    @Operation(
    		operationId = "consultarId",
    		description = "Consultar pedido",
    		tags = {"Pedido"}    		
    		)
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Ok", content = 
              { @Content(mediaType = "application/json", schema = 
                @Schema(implementation = PedidoDTO.class)) }),
            @ApiResponse(responseCode = "400", description = "Invalid ID supplied"), 
            @ApiResponse(responseCode = "404", description = "Pedido not found"),
            @ApiResponse(responseCode = "500", description = "Internal server error", content = 
              { @Content(mediaType = "application/json", schema = 
                @Schema(implementation = ErrorResponse.class)) }) })
    @GetMapping(value = "/{id}")
    public ResponseEntity<PedidoDTO> getPedido(@PathVariable final Long id) {
        if (id == null)
            return ResponseEntity.badRequest().build();
        Pedido pedido = pedidoService.getSearchPedidoUseCase().findById(id).orElseThrow(() -> new PedidoNotFoundException(id));
        return ResponseEntity.ok(mapper.toDTO(pedido));
    }
    
    @Operation(
    		operationId = "listar",
    		description = "Listar pedidos",
    		tags = {"Pedido"}    		
    		)
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Ok", content = 
              { @Content(mediaType = "application/json", schema = 
                @Schema(implementation = List.class)) }),
            @ApiResponse(responseCode = "500", description = "Internal server error", content =
              { @Content(mediaType = "application/json", schema = 
                @Schema(implementation = ErrorResponse.class)) }) })
    @GetMapping
    public ResponseEntity<List<PedidoDTO>> getPedidos(@RequestParam(required = true, defaultValue = "1") Integer page, @RequestParam(required = true, defaultValue = "10") Integer size) {
        if (page < 1)
            page = 1;

        Pageable pageable = PageRequest.of(page, size);
    	return ResponseEntity.ok(pedidoService.getSearchPedidoUseCase().find(page, size).stream().map(mapper::toDTO).toList());
	
    }

}
