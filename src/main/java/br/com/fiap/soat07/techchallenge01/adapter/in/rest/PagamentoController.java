package br.com.fiap.soat07.techchallenge01.adapter.in.rest;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.ErrorResponse;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.fiap.soat07.techchallenge01.adapter.in.rest.dto.mapper.PagamentoMapper;
import br.com.fiap.soat07.techchallenge01.adapter.in.rest.dto.PagamentoDTO;
import br.com.fiap.soat07.techchallenge01.application.ports.in.PagamentoUseCase;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/pagamento")
@RequiredArgsConstructor
@Tag(name = "Pagamento", description = "Pagamento")
public class PagamentoController {

    private final PagamentoUseCase pagamentoUseCase;

    private final PagamentoMapper mapper;

    @Operation(
    		operationId = "executar",
    		description = "Executar pagamento",
    		tags = {"Pagamento"}    		
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
    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<PagamentoDTO> createProduto(@RequestBody final PagamentoDTO pagamentoDTO) {
    	
    	return ResponseEntity.ok(mapper.toDTO(pagamentoUseCase.executar(mapper.toDomain(pagamentoDTO))));
	
    }

}
