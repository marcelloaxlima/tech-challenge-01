package br.com.fiap.soat07.clean.infra.rest.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import br.com.fiap.soat07.clean.core.usecase.cliente.CreateClienteUseCase;
import br.com.fiap.soat07.clean.infra.repository.mysql.ClienteRepository;

@Configuration
public class Config {

    @Bean
    public CreateClienteUseCase getUserUseCase(ClienteRepository repository) {
        CreateClienteUseCase useCase = new CreateClienteUseCase(repository);
        return useCase;
    }
}