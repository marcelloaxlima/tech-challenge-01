package br.com.fiap.soat07.clean.infra.rest;

import java.util.List;
import java.util.Optional;

import br.com.fiap.soat07.clean.core.domain.entity.Cliente;
import br.com.fiap.soat07.clean.infra.service.ClienteService;
import jakarta.transaction.Transactional;
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

import br.com.fiap.soat07.clean.infra.rest.mapper.ClienteMapper;
import br.com.fiap.soat07.clean.infra.rest.dto.ClienteDTO;
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
    private final ClienteService clienteService;
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
    @Transactional
    public ResponseEntity<ClienteDTO> createCliente(@RequestBody final ClienteDTO clienteDTO) {
        Cliente cliente = clienteService.getCreateClienteUseCase().execute(clienteDTO);

        return ResponseEntity.ok(mapper.toDTO(cliente));
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
    @Transactional
    public ResponseEntity<ClienteDTO> updateCliente(@PathVariable final Long id, @RequestBody final ClienteDTO clienteDTO) {
        if (id == null)
            return ResponseEntity.badRequest().build();
        Optional<Cliente> clienteOp = clienteService.getSearchClienteUseCase().findById(id);
        return clienteOp.map(combo -> ResponseEntity.ok(mapper.toDTO(clienteService.getUpdateClienteUseCase().execute(clienteOp.get(), clienteDTO)))).orElseGet(() -> ResponseEntity.notFound().build());
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
    @Transactional
    public ResponseEntity<?> deleteCliente(@PathVariable final Long id) {
        if (id == null)
            return ResponseEntity.badRequest().build();
        Optional<Cliente> clienteOp = clienteService.getSearchClienteUseCase().findById(id);
        if (clienteOp.isEmpty())
            return ResponseEntity.ok().build();

        clienteService.getDeleteClienteUseCase().execute(clienteOp.get());
        return ResponseEntity.ok().build();
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
    public ResponseEntity<ClienteDTO> getCliente(@PathVariable final Long id) {
        if (id == null)
            return ResponseEntity.badRequest().build();
        Optional<Cliente> clienteOp = clienteService.getSearchClienteUseCase().findById(id);
        return clienteOp.map(cliente -> ResponseEntity.ok(mapper.toDTO(cliente))).orElseGet(() -> ResponseEntity.notFound().build());
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
        if (cpf == null || cpf.isEmpty())
            return ResponseEntity.badRequest().build();
        Optional<Cliente> clienteOp = clienteService.getSearchClienteUseCase().findByCpf(cpf);
        return clienteOp.map(cliente -> ResponseEntity.ok(mapper.toDTO(cliente))).orElseGet(() -> ResponseEntity.notFound().build());
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
    public ResponseEntity<List<ClienteDTO>> getClientes(@RequestParam(required = true, defaultValue = "1") Integer page , @RequestParam(required = true, defaultValue = "10") Integer size) {
    	if (page < 1)
            page = 1;

        return ResponseEntity.ok(clienteService.getSearchClienteUseCase().find(page, size).stream().map(mapper::toDTO).toList());

    }

}
