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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import br.com.fiap.soat07.techchallenge01.application.mapper.ClienteMapper;
import br.com.fiap.soat07.techchallenge01.application.model.dto.ClienteDTO;
import br.com.fiap.soat07.techchallenge01.domain.usecase.ClienteUseCase;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;

@Tag(name = "Cliente", description = "Cliente")
@RestController
@RequestMapping("/cliente")
@RequiredArgsConstructor
public class ClienteController {

    private final ClienteUseCase clienteUseCase;
    private final ClienteMapper mapper;

    @Operation(
      operationId = "criar",
      description = "Criar cliente",
      tags = {"Cliente"}    		
    )
    
    @ApiResponses(value = {
      @ApiResponse(responseCode = "201", description = "Created", content = 
        { @Content(mediaType = "application/json", schema = 
          @Schema(implementation = ClienteDTO.class)) }),
      @ApiResponse(responseCode = "400", description = "Invalid values"), 
      @ApiResponse(responseCode = "404", description = "Client already exists"),
      @ApiResponse(responseCode = "500", description = "Internal server error", content = 
        { @Content(mediaType = "application/json", schema = 
          @Schema(implementation = ErrorResponse.class)) }) })

    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ClienteDTO> createCliente(@RequestBody final ClienteDTO clienteDTO) {
    	
    	return ResponseEntity.ok(mapper.toDTO(clienteUseCase.create(mapper.toDomain(clienteDTO))));
	
    }

    @Operation(
      operationId = "atualizar",
      description = "Atualizar cliente",
      tags = {"Cliente"}    		
    )
    
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Ok", content = 
              { @Content(mediaType = "application/json", schema = 
                @Schema(implementation = ClienteDTO.class)) }),
            @ApiResponse(responseCode = "400", description = "Invalid ID supplied"), 
            @ApiResponse(responseCode = "404", description = "Client not found"),
            @ApiResponse(responseCode = "500", description = "Internal server error", content = 
              { @Content(mediaType = "application/json", schema = 
                @Schema(implementation = ErrorResponse.class)) }) })

    @PutMapping(value = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ClienteDTO> updateCliente(@PathVariable final Long id, @RequestBody final ClienteDTO clienteDTO) {
    	
    	return ResponseEntity.ok(mapper.toDTO(clienteUseCase.update(id, mapper.toDomain(clienteDTO))));
    	
    }


    @Operation(
      operationId = "deletar",
      description = "Deletar cliente",
    	tags = {"Cliente"}    		
    )
    
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Deleted", content = 
              { @Content(mediaType = "application/json", schema = 
                @Schema(implementation = ClienteDTO.class)) }),
            @ApiResponse(responseCode = "400", description = "Invalid ID supplied"), 
            @ApiResponse(responseCode = "404", description = "Client not found"),
            @ApiResponse(responseCode = "500", description = "Internal server error", content = 
              { @Content(mediaType = "application/json", schema = 
                @Schema(implementation = ErrorResponse.class)) }) })

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<?> deleteCliente(@PathVariable final Long id) {
    	clienteUseCase.delete(id);
    	return new ResponseEntity<>(HttpStatus.OK);
    }

    @Operation(
      operationId = "consultarId",
      description = "Consultar cliente",
      tags = {"Cliente"}    		
    )
    
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Ok", content = 
              { @Content(mediaType = "application/json", schema = 
                @Schema(implementation = ClienteDTO.class)) }),
            @ApiResponse(responseCode = "400", description = "Invalid ID supplied"), 
            @ApiResponse(responseCode = "404", description = "Client not found"),
            @ApiResponse(responseCode = "500", description = "Internal server error", content = 
              { @Content(mediaType = "application/json", schema = 
                @Schema(implementation = ErrorResponse.class)) }) })

    @GetMapping(value = "/{id}")
    public ResponseEntity<ClienteDTO> getCliente(@PathVariable final long id) {
    	return ResponseEntity.ok(mapper.toDTO(clienteUseCase.getById(id)));
    }
    
    @Operation(
      operationId = "consultarCpf",
      description = "Consultar cliente pelo CPF",
      tags = {"Cliente"}    		
    )
    
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Ok", content = 
              { @Content(mediaType = "application/json", schema = 
                @Schema(implementation = ClienteDTO.class)) }),
            @ApiResponse(responseCode = "400", description = "Invalid ID supplied"), 
            @ApiResponse(responseCode = "404", description = "Client not found"),
            @ApiResponse(responseCode = "500", description = "Internal server error", content = 
              { @Content(mediaType = "application/json", schema = 
                @Schema(implementation = ErrorResponse.class)) }) })

    @GetMapping(value = "/cpf/{cpf}")
    public ResponseEntity<ClienteDTO> getClienteByCpf(@PathVariable("cpf") String cpf) {
    	return ResponseEntity.ok(mapper.toDTO(clienteUseCase.getByCpf(cpf)));
    }
    
    @Operation(
      operationId = "listar",
      description = "Listar clientes",
      tags = {"Cliente"}    		
    )
    
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Ok", content = 
              { @Content(mediaType = "application/json", schema = 
                @Schema(implementation = List.class)) }),
            @ApiResponse(responseCode = "400", description = "Invalid ID supplied"), 
            @ApiResponse(responseCode = "404", description = "Client not found"),
            @ApiResponse(responseCode = "500", description = "Internal server error", content = 
              { @Content(mediaType = "application/json", schema = 
                @Schema(implementation = ErrorResponse.class)) }) })

    @GetMapping
    public ResponseEntity<List<ClienteDTO>> getClientes(@RequestParam(required = true, defaultValue = "0") Integer page , @RequestParam(required = true, defaultValue = "10") Integer size) {
    	
    	Pageable pageable = PageRequest.of(page, size);
    	return ResponseEntity.ok(clienteUseCase.getPageable(pageable).stream().map(mapper::toDTO).toList());
	
    }

}
