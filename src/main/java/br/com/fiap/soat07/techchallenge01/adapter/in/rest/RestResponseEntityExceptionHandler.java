package br.com.fiap.soat07.techchallenge01.adapter.in.rest;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import br.com.fiap.soat07.techchallenge01.adapter.in.rest.dto.ErroTemplateDTO;
import br.com.fiap.soat07.techchallenge01.application.exception.BusinessException;
import br.com.fiap.soat07.techchallenge01.application.exception.ResourceNotFoundException;
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
		String bodyOfResponse = buildError(ex, HttpStatus.NOT_FOUND);
		
		return handleExceptionInternal(ex, bodyOfResponse, new HttpHeaders(), HttpStatus.NOT_FOUND, request);
	}

	@ExceptionHandler(value = { BusinessException.class })
	protected ResponseEntity<Object> handleBusinessException(RuntimeException ex, WebRequest request) {
		String bodyOfResponse = buildError(ex, HttpStatus.valueOf(422));
		
		return handleExceptionInternal(ex, bodyOfResponse, new HttpHeaders(), HttpStatus.valueOf(422), request);
	}
	
	@ExceptionHandler(value = { RuntimeException.class })
	protected ResponseEntity<Object> handleGenericException(RuntimeException ex, WebRequest request) {
		String bodyOfResponse = buildError(ex, HttpStatus.INTERNAL_SERVER_ERROR);
		
		return handleExceptionInternal(ex, bodyOfResponse, new HttpHeaders(), HttpStatus.INTERNAL_SERVER_ERROR, request);
	}
	
	
	
	
	
	private String buildError(RuntimeException ex, HttpStatus httpStatus) {
		ErroTemplateDTO errorTemplate = ErroTemplateDTO.builder()
				.message(ex.getMessage())
				.httpStatus(httpStatus)
				.build();
		String bodyOfResponse = String.format ("{%s}", ex.getMessage());

		 try {
			bodyOfResponse = new ObjectMapper().writeValueAsString(errorTemplate);
		} catch (JsonProcessingException e) {
			log.warn("Error parsing ErroTemplate");
		}
		return bodyOfResponse;
	}

}