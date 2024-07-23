package br.com.fiap.soat07.clean.infra.rest;

import br.com.fiap.soat07.clean.core.domain.entity.Produto;
import br.com.fiap.soat07.clean.core.domain.enumeration.TipoProdutoEnum;
import br.com.fiap.soat07.clean.infra.rest.dto.ProdutoDTO;
import br.com.fiap.soat07.clean.infra.rest.mapper.ProdutoMapper;
import br.com.fiap.soat07.clean.infra.service.ProdutoService;
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

import java.util.Collection;
import java.util.List;
import java.util.Optional;

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
    @Transactional
    public ResponseEntity<ProdutoDTO> createProduto(@RequestBody final ProdutoDTO produtoDTO) {

    	return ResponseEntity.ok(mapper.toDTO(produtoService.getCreateProdutoUseCase().execute(produtoDTO)));
	
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
    @Transactional
    public ResponseEntity<ProdutoDTO> updateProduto(@PathVariable final Long id, @RequestBody final ProdutoDTO produtoDTO) {
        if (id == null)
            return ResponseEntity.badRequest().build();
        Optional<Produto> produtoOp = produtoService.getSearchProdutoUseCase().findById(id);
        return produtoOp.map(produto -> ResponseEntity.ok(mapper.toDTO(produtoService.getUpdateProdutoUseCase().execute(produto, produtoDTO)))).orElseGet(() -> ResponseEntity.notFound().build());

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
    @Transactional
    public ResponseEntity<?> deleteProduto(@PathVariable final Long id) {
        if (id == null)
            return ResponseEntity.badRequest().build();
        Optional<Produto> produtoOp = produtoService.getSearchProdutoUseCase().findById(id);
        if (produtoOp.isEmpty())
            return ResponseEntity.ok().build();

        Produto produto = produtoOp.get();
        if (produto.isExcluido())
            return ResponseEntity.ok().build();

        produtoService.getDeleteProdutoUseCase().execute(produtoOp.get());
    	return ResponseEntity.ok().build();
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
        if (id == null)
            return ResponseEntity.badRequest().build();
        Optional<Produto> produtoOp = produtoService.getSearchProdutoUseCase().findById(id);
        return produtoOp.map(produto -> ResponseEntity.ok(mapper.toDTO(produto))).orElseGet(() -> ResponseEntity.notFound().build());

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
    public ResponseEntity<List<ProdutoDTO>> getProdutos(@RequestParam(required = true, defaultValue = "1") Integer page,
    		@RequestParam(required = true, defaultValue = "10") Integer size,
    		@RequestParam(required = false) TipoProdutoEnum tipoProduto) {
        if (page < 1)
            page = 1;

        Pageable pageable = PageRequest.of(page, size);
        Collection<Produto> result = produtoService.getSearchProdutoUseCase().find(tipoProduto, page, size);
    	return ResponseEntity.ok(result.stream().map(model -> mapper.toDTO(model)).toList());
    }

}
