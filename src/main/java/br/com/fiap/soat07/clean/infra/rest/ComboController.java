package br.com.fiap.soat07.clean.infra.rest;

import br.com.fiap.soat07.clean.core.domain.entity.Combo;
import br.com.fiap.soat07.clean.core.domain.entity.Produto;
import br.com.fiap.soat07.clean.core.exception.ComboNotFoundException;
import br.com.fiap.soat07.clean.core.exception.ProdutoNotFoundException;
import br.com.fiap.soat07.clean.infra.rest.dto.ComboDTO;
import br.com.fiap.soat07.clean.infra.rest.dto.CreateComboDTO;
import br.com.fiap.soat07.clean.infra.rest.mapper.ComboMapper;
import br.com.fiap.soat07.clean.infra.service.ComboService;
import br.com.fiap.soat07.clean.infra.service.ProdutoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.ErrorResponse;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/combo")
@RequiredArgsConstructor
@Tag(name = "Combo", description = "Combo")
public class ComboController {

    private final ComboService comboService;
    private final ProdutoService produtoService;
    private final ComboMapper mapper;

    @Operation(
    		operationId = "criar",
    		description = "Criar combo",
    		tags = {"Combo"}    		
    		)
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Created", content = 
              { @Content(mediaType = "application/json", schema = 
                @Schema(implementation = CreateComboDTO.class)) }),
            @ApiResponse(responseCode = "400", description = "Invalid values"), 
            @ApiResponse(responseCode = "404", description = "Combo already exists"),
            @ApiResponse(responseCode = "500", description = "Internal server error", content = 
              { @Content(mediaType = "application/json", schema = 
                @Schema(implementation = ErrorResponse.class)) }) })
    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    @Transactional
    public ResponseEntity<ComboDTO> createCombo(@RequestBody final CreateComboDTO comboDTO) {
    	
    	return ResponseEntity.ok(mapper.toDTO(comboService.getCreateComboUseCase().execute(comboDTO)));
	
    }

    @Operation(
    		operationId = "atualizar",
    		description = "Atualizar combo",
    		tags = {"Combo"}    		
    		)
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Ok", content = 
              { @Content(mediaType = "application/json", array =
                @ArraySchema(schema = @Schema(implementation = Long.class))) }),
            @ApiResponse(responseCode = "400", description = "Invalid ID supplied"), 
            @ApiResponse(responseCode = "404", description = "Combo not found"),
            @ApiResponse(responseCode = "500", description = "Internal server error", content = 
              { @Content(mediaType = "application/json", schema = 
                @Schema(implementation = ErrorResponse.class)) }) })
    @PutMapping(value = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
    @Transactional
    public ResponseEntity<ComboDTO> updateCombo(@PathVariable final Long id, @RequestBody final List<Long> productIds) {
        if (id == null)
            return ResponseEntity.badRequest().build();
        Combo combo = comboService.getSearchComboUseCase().findById(id).orElseThrow(() -> new ComboNotFoundException(id));

        Collection<Produto> produtos = new ArrayList<>();
        for (Long productId : productIds)
            produtos.add(produtoService.getSearchProdutoUseCase().findById(productId).orElseThrow(() -> new ProdutoNotFoundException(productId)));
        return ResponseEntity.ok(mapper.toDTO(comboService.getUpdateComboUseCase().execute(combo, produtos)));
    }

    @Operation(
    		operationId = "deletar",
    		description = "Deletar combo",
    		tags = {"Combo"}    		
    		)
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Deleted", content = 
              { @Content(mediaType = "application/json", schema = 
                @Schema(implementation = ComboDTO.class)) }),
            @ApiResponse(responseCode = "400", description = "Invalid ID supplied"), 
            @ApiResponse(responseCode = "404", description = "Combo not found"),
            @ApiResponse(responseCode = "500", description = "Internal server error", content = 
              { @Content(mediaType = "application/json", schema = 
                @Schema(implementation = ErrorResponse.class)) }) })
    @DeleteMapping(value = "/{id}")
    @Transactional
    public ResponseEntity<?> deleteCombo(@PathVariable final Long id) {
        if (id == null)
            return ResponseEntity.badRequest().build();
        Optional<Combo> comboOp = comboService.getSearchComboUseCase().findById(id);
        if (comboOp.isEmpty())
            return ResponseEntity.ok().build();

        comboService.getDeleteComboUseCase().execute(comboOp.get());
        return ResponseEntity.ok().build();
    }

    @Operation(
    		operationId = "consultarId",
    		description = "Consultar combo",
    		tags = {"Combo"}    		
    		)
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Ok", content = 
              { @Content(mediaType = "application/json", schema = 
                @Schema(implementation = ComboDTO.class)) }),
            @ApiResponse(responseCode = "400", description = "Invalid ID supplied"), 
            @ApiResponse(responseCode = "404", description = "Combo not found"),
            @ApiResponse(responseCode = "500", description = "Internal server error", content = 
              { @Content(mediaType = "application/json", schema = 
                @Schema(implementation = ErrorResponse.class)) }) })
    @GetMapping(value = "/{id}")
    public ResponseEntity<ComboDTO> getCombo(@PathVariable final Long id) {
        if (id == null)
            return ResponseEntity.badRequest().build();
        Combo combo = comboService.getSearchComboUseCase().findById(id).orElseThrow(() -> new ComboNotFoundException(id));
        return ResponseEntity.ok(mapper.toDTO(combo));
    }
    
    @Operation(
    		operationId = "listar",
    		description = "Listar combos",
    		tags = {"Combo"}    		
    		)
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Ok", content = 
              { @Content(mediaType = "application/json", schema = 
                @Schema(implementation = List.class)) }),
            @ApiResponse(responseCode = "400", description = "Invalid ID supplied"), 
            @ApiResponse(responseCode = "404", description = "Combo not found"),
            @ApiResponse(responseCode = "500", description = "Internal server error", content = 
              { @Content(mediaType = "application/json", schema = 
                @Schema(implementation = ErrorResponse.class)) }) })
    @GetMapping
    public ResponseEntity<List<ComboDTO>> getCombos(@RequestParam(required = true, defaultValue = "1") Integer page, @RequestParam(required = true, defaultValue = "10") Integer size) {
        if (page < 1)
            page = 1;

        return ResponseEntity.ok(comboService.getSearchComboUseCase().find(page, size).stream().map(mapper::toDTO).toList());
	
    }

}
