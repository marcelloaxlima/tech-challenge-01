package br.com.fiap.soat07.techchallenge01.application.api;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import br.com.fiap.soat07.techchallenge01.application.model.dto.ErroTemplateDTO;
import br.com.fiap.soat07.techchallenge01.domain.exception.ResourceNotFoundException;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ControllerAdvice
public class RestResponseEntityExceptionHandler extends ResponseEntityExceptionHandler {

	@ExceptionHandler(value = { IllegalArgumentException.class, IllegalStateException.class })
	protected ResponseEntity<Object> handleConflict(RuntimeException ex, WebRequest request) {
		String bodyOfResponse = "This should be application specific";
		return handleExceptionInternal(ex, bodyOfResponse, new HttpHeaders(), HttpStatus.CONFLICT, request);
	}

	@ExceptionHandler(value = { ResourceNotFoundException.class })
	protected ResponseEntity<Object> handleNotFound(RuntimeException ex, WebRequest request) {
		ErroTemplateDTO errorTemplate = ErroTemplateDTO.builder()
				.message(ex.getMessage())
				.httpStatus(HttpStatus.NOT_FOUND)
				.build();
		String bodyOfResponse = String.format ("{%s}", ex.getMessage());

		 try {
			bodyOfResponse = new ObjectMapper().writeValueAsString(errorTemplate);
		} catch (JsonProcessingException e) {
			log.warn("Error parsing ErroTemplate");
		}
		
		return handleExceptionInternal(ex, bodyOfResponse, new HttpHeaders(), HttpStatus.NOT_FOUND, request);
	}

}