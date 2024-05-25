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
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.fiap.soat07.techchallenge01.application.mapper.ComboMapper;
import br.com.fiap.soat07.techchallenge01.application.model.dto.ComboDTO;
import br.com.fiap.soat07.techchallenge01.domain.usecase.ComboUseCase;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/combo")
@RequiredArgsConstructor
@Tag(name = "Combo", description = "Combo")
public class ComboController {

    private final ComboUseCase comboUseCase;

    private final ComboMapper mapper;

    @Operation(
    		operationId = "criar",
    		description = "Criar combo",
    		tags = {"Combo"}    		
    		)
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Created", content = 
              { @Content(mediaType = "application/json", schema = 
                @Schema(implementation = ComboDTO.class)) }),
            @ApiResponse(responseCode = "400", description = "Invalid values"), 
            @ApiResponse(responseCode = "404", description = "Combo already exists"),
            @ApiResponse(responseCode = "500", description = "Internal server error", content = 
              { @Content(mediaType = "application/json", schema = 
                @Schema(implementation = ErrorResponse.class)) }) })
    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ComboDTO> createCombo(@RequestBody final ComboDTO comboDTO) {
    	
    	return ResponseEntity.ok(mapper.toDTO(comboUseCase.create(mapper.toDomain(comboDTO))));
	
    }

    @Operation(
    		operationId = "atualizar",
    		description = "Atualizar combo",
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
    @PutMapping(value = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ComboDTO> updateCombo(@PathVariable final long id, @RequestBody final ComboDTO comboDTO) {
    	
    	return ResponseEntity.ok(mapper.toDTO(comboUseCase.update(id, mapper.toDomain(comboDTO))));
    	
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
    public ResponseEntity<?> deleteCombo(@PathVariable final long id) {
    	comboUseCase.delete(id);
    	return new ResponseEntity<>(HttpStatus.OK);
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
    public ResponseEntity<ComboDTO> getCombo(@PathVariable final long id) {
    	
    	return ResponseEntity.ok(mapper.toDTO(comboUseCase.getById(id)));
	
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
    public ResponseEntity<List<ComboDTO>> getCombos(Integer page, Integer size) {
    	
    	Pageable pageable = PageRequest.of(page, size);
    	return ResponseEntity.ok(comboUseCase.getPageable(pageable).stream().map(mapper::toDTO).toList());
	
    }

}
