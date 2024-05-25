package br.com.fiap.soat07.techchallenge01.infra.config;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

import javax.sql.DataSource;

import org.springframework.boot.autoconfigure.batch.BatchProperties.Jdbc;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

import lombok.Setter;

@Setter
@Configuration
@ConfigurationProperties(prefix =  "application.database")
@EntityScan("br.com.fiap.soat07.techchallenge01")
@EnableJpaRepositories("br.com.fiap.soat07.techchallenge01")
@EnableTransactionManagement
public class DatabaseConfig {

	private String url;	
	private String driverClassName;	
	private String userName;
	private String password;
	
	
	
	@Bean
	public DataSource dataSource() {
		HikariConfig hikariConfig = new HikariConfig();
		hikariConfig.setDriverClassName(driverClassName);
		hikariConfig.setJdbcUrl(url);
		hikariConfig.setUsername(getUsername());
		hikariConfig.setPassword(getPassword());
		return new HikariDataSource(hikariConfig);
	}

	@Bean
	@DependsOn("dataSource")
	public JdbcTemplate jdbcTemplate(DataSource dataSource) {
		JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
		
		jdbcTemplate.update("""
			INSERT INTO produtos (id,codigo,data_criacao,nome,tipo_produto,ultima_modificacao,data_exclusao,valor) VALUES
			(1,"LAN01","2024-05-20 04:35:49.465472","Hamburger","LANCHE","2024-05-20 05:03:51.524812",null,5.50);
		""");
		return new JdbcTemplate(dataSource);
	}
	
	private String getUsername() {
		
		return readFromFile(userName);
	}
	
	private String getPassword() {
		return readFromFile(password);
	}
	
	private String readFromFile(String filename) {
        StringBuilder password = new StringBuilder();
        try (BufferedReader br = new BufferedReader(new FileReader(filename))) {
            String line;
            while ((line = br.readLine()) != null) {
                password.append(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return password.toString();
    }
}
