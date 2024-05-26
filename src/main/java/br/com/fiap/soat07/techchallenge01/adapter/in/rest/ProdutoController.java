package br.com.fiap.soat07.techchallenge01.adapter.in.rest;

import java.util.List;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.ErrorResponse;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import br.com.fiap.soat07.techchallenge01.adapter.in.rest.dto.ProdutoDTO;
import br.com.fiap.soat07.techchallenge01.adapter.in.rest.dto.mapper.ProdutoMapper;
import br.com.fiap.soat07.techchallenge01.application.domain.enumeration.TipoProdutoEnum;
import br.com.fiap.soat07.techchallenge01.application.service.ProdutoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/produto")
@RequiredArgsConstructor
@Tag(name = "Produto", description = "Produto")
public class ProdutoController {

    private final ProdutoService produtoService;

    private final ProdutoMapper mapper;

    @Operation(
    		operationId = "criar",
    		description = "Criar produto",
    		tags = {"Produto"}    		
    		)
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Created", content = 
              { @Content(mediaType = "application/json", schema = 
                @Schema(implementation = ProdutoDTO.class)) }),
            @ApiResponse(responseCode = "400", description = "Invalid values"), 
            @ApiResponse(responseCode = "404", description = "Produto already exists"),
            @ApiResponse(responseCode = "500", description = "Internal server error", content = 
              { @Content(mediaType = "application/json", schema = 
                @Schema(implementation = ErrorResponse.class)) }) })
    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ProdutoDTO> createProduto(@RequestBody final ProdutoDTO produtoDTO) {
    	
    	return ResponseEntity.ok(mapper.toDTO(produtoService.create(mapper.toDomain(produtoDTO))));
	
    }

    @Operation(
    		operationId = "atualizar",
    		description = "Atualizar produto",
    		tags = {"Produto"}    		
    		)
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Ok", content = 
              { @Content(mediaType = "application/json", schema = 
                @Schema(implementation = ProdutoDTO.class)) }),
            @ApiResponse(responseCode = "400", description = "Invalid ID supplied"), 
            @ApiResponse(responseCode = "404", description = "Produto not found"),
            @ApiResponse(responseCode = "500", description = "Internal server error", content = 
              { @Content(mediaType = "application/json", schema = 
                @Schema(implementation = ErrorResponse.class)) }) })
    @PutMapping(value = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ProdutoDTO> updateProduto(@PathVariable final Long id, @RequestBody final ProdutoDTO produtoDTO) {
    	
    	return ResponseEntity.ok(mapper.toDTO(produtoService.update(id, mapper.toDomain(produtoDTO))));
    	
    }

    @Operation(
    		operationId = "deletar",
    		description = "Deletar produto",
    		tags = {"Produto"}    		
    		)
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Deleted", content = 
              { @Content(mediaType = "application/json", schema = 
                @Schema(implementation = ProdutoDTO.class)) }),
            @ApiResponse(responseCode = "400", description = "Invalid ID supplied"), 
            @ApiResponse(responseCode = "404", description = "Produto not found"),
            @ApiResponse(responseCode = "500", description = "Internal server error", content = 
              { @Content(mediaType = "application/json", schema = 
                @Schema(implementation = ErrorResponse.class)) }) })
    @DeleteMapping(value = "/{id}")
    public ResponseEntity<?> deleteProduto(@PathVariable final Long id) {
    	produtoService.delete(id);
    	return new ResponseEntity<>(HttpStatus.OK);
    }

    @Operation(
    		operationId = "consultarId",
    		description = "Consultar produto",
    		tags = {"Produto"}    		
    		)
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Ok", content = 
              { @Content(mediaType = "application/json", schema = 
                @Schema(implementation = ProdutoDTO.class)) }),
            @ApiResponse(responseCode = "400", description = "Invalid ID supplied"), 
            @ApiResponse(responseCode = "404", description = "Produto not found"),
            @ApiResponse(responseCode = "500", description = "Internal server error", content = 
              { @Content(mediaType = "application/json", schema = 
                @Schema(implementation = ErrorResponse.class)) }) })
    @GetMapping(value = "/{id}")
    public ResponseEntity<ProdutoDTO> getProduto(@PathVariable final Long id) {
    	
    	return ResponseEntity.ok(mapper.toDTO(produtoService.getById(id)));
	
    }
    
    @Operation(
    		operationId = "listar",
    		description = "Listar produtos",
    		tags = {"Produto"}    		
    		)
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Ok", content = 
              { @Content(mediaType = "application/json", schema = 
                @Schema(implementation = List.class)) }),
            @ApiResponse(responseCode = "400", description = "Invalid ID supplied"), 
            @ApiResponse(responseCode = "404", description = "Produto not found"),
            @ApiResponse(responseCode = "500", description = "Internal server error", content = 
              { @Content(mediaType = "application/json", schema = 
                @Schema(implementation = ErrorResponse.class)) }) })
    @GetMapping
    public ResponseEntity<List<ProdutoDTO>> getProdutos(@RequestParam(required = true, defaultValue = "0") Integer page, 
    		@RequestParam(required = true, defaultValue = "10") Integer size,
    		@RequestParam(required = false) TipoProdutoEnum tipoProduto) {
    	
    	Pageable pageable = PageRequest.of(page, size);
    	return ResponseEntity.ok(produtoService.search(tipoProduto, pageable).stream().map(mapper::toDTO).toList());
    	
	
    }

}
