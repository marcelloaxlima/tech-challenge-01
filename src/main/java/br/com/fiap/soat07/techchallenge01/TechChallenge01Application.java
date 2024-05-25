package br.com.fiap.soat07.techchallenge01;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;

@SpringBootApplication
@OpenAPIDefinition(info = @Info(title = "Lanchonete Self Service API", version = "1.0", description = "Sistema de Self Service para Lanchonete"))
public class TechChallenge01Application {

	public static void main(String[] args) {
		SpringApplication.run(TechChallenge01Application.class, args);
	}

}
