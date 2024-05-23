package br.com.fiap.soat07.techchallenge01.application.api;

import java.util.List;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.ErrorResponse;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import br.com.fiap.soat07.techchallenge01.application.mapper.PedidoMapper;
import br.com.fiap.soat07.techchallenge01.application.model.dto.PedidoDTO;
import br.com.fiap.soat07.techchallenge01.domain.enumeration.PedidoStatusEnum;
import br.com.fiap.soat07.techchallenge01.domain.usecase.PedidoUseCase;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/pedido")
@RequiredArgsConstructor
@Tag(name = "Pedido", description = "Pedido")
public class PedidoController {

    private final PedidoUseCase pedidoUseCase;

    private final PedidoMapper mapper;

    @Operation(
    		operationId = "criar",
    		description = "Criar pedido",
    		tags = {"Pedido"}    		
    		)
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Created", content = 
              { @Content(mediaType = "application/json", schema = 
                @Schema(implementation = PedidoDTO.class)) }),
            @ApiResponse(responseCode = "400", description = "Invalid values"), 
            @ApiResponse(responseCode = "404", description = "Pedido already exists"),
            @ApiResponse(responseCode = "500", description = "Internal server error", content = 
              { @Content(mediaType = "application/json", schema = 
                @Schema(implementation = ErrorResponse.class)) }) })
    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<PedidoDTO> createPedido(@RequestBody final PedidoDTO pedidoDTO) {
    	
    	return ResponseEntity.ok(mapper.toDTO(pedidoUseCase.create(mapper.toDomain(pedidoDTO))));
	
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
    public ResponseEntity<PedidoDTO> updatePedido(@PathVariable final Long id, @RequestBody final PedidoDTO pedidoDTO) {
    	
    	return ResponseEntity.ok(mapper.toDTO(pedidoUseCase.update(id, mapper.toDomain(pedidoDTO))));
    	
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
    public ResponseEntity<PedidoDTO> updatePedidoStatus(@PathVariable final Long id, @RequestParam final PedidoStatusEnum status) {
    	
    	return ResponseEntity.ok(mapper.toDTO(pedidoUseCase.updateStatus(id, status)));
    	
    }

    @Operation(
    		operationId = "deletar",
    		description = "Deletar pedido",
    		tags = {"Pedido"}    		
    		)
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Deleted", content = 
              { @Content(mediaType = "application/json", schema = 
                @Schema(implementation = PedidoDTO.class)) }),
            @ApiResponse(responseCode = "400", description = "Invalid ID supplied"), 
            @ApiResponse(responseCode = "404", description = "Pedido not found"),
            @ApiResponse(responseCode = "500", description = "Internal server error", content = 
              { @Content(mediaType = "application/json", schema = 
                @Schema(implementation = ErrorResponse.class)) }) })
    @DeleteMapping(value = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> deletePedido(@PathVariable final Long id) {
    	pedidoUseCase.delete(id);
    	return new ResponseEntity<>(HttpStatus.OK);
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
    	
    	return ResponseEntity.ok(mapper.toDTO(pedidoUseCase.getById(id)));
	
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
            @ApiResponse(responseCode = "400", description = "Invalid ID supplied"), 
            @ApiResponse(responseCode = "404", description = "Pedido not found"),
            @ApiResponse(responseCode = "500", description = "Internal server error", content = 
              { @Content(mediaType = "application/json", schema = 
                @Schema(implementation = ErrorResponse.class)) }) })
    @GetMapping
    public ResponseEntity<List<PedidoDTO>> getPedidos(@RequestParam(required = true, defaultValue = "0") Integer page, @RequestParam(required = true, defaultValue = "10") Integer size) {
    	
    	Pageable pageable = PageRequest.of(page, size);
    	return ResponseEntity.ok(pedidoUseCase.getPageable(pageable).stream().map(mapper::toDTO).toList());
	
    }

}
