package br.com.fiap.soat07.techchallenge01.adapter.out.persistence.config;

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

	/*
	@Bean
	@DependsOn("dataSource")
	public JdbcTemplate jdbcTemplate(DataSource dataSource) {
		JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
		
		jdbcTemplate.update("""
			INSERT INTO produtos (id,codigo,data_criacao,nome,tipo_produto,ultima_modificacao,valor) VALUES
			(1,"LAN01","2024-05-20 04:35:49.465472","Hamburger","LANCHE","2024-05-20 05:03:51.524812",5.50),
			(2,"LAN02","2024-05-20 04:36:37.007693","X-Hamburger","LANCHE","2024-05-20 04:36:37.007693",6.50),
			(3,"ACO01","2024-05-20 04:37:21.025117","Batata frita","ACOMPANHAMENTO","2024-05-20 05:03:51.507303",4.00),
			(4,"ACO02","2024-05-20 04:37:38.028388","Mandioca frita","ACOMPANHAMENTO","2024-05-20 04:37:38.028388",4.00),
			(5,"BEB01","2024-05-20 04:38:18.347290","Coca Cola","BEBIDA","2024-05-20 05:03:51.524812",1.00),
			(6,"BEB02","2024-05-20 04:38:45.564562","Guaraná","BEBIDA","2024-05-20 04:38:45.564562",1.00),
			(7,"BEB03","2024-05-20 04:39:10.212640","Suco de laranja","BEBIDA","2024-05-20 04:39:10.212640",1.50),
			(8,"SOB01","2024-05-20 04:39:36.275862","Pudim","SOBREMESA","2024-05-20 05:03:51.523812",1.50),
			(9,"SOB02","2024-05-20 04:40:02.972659","Torta de maçã","SOBREMESA","2024-05-20 04:40:02.972659",2.00),
			(10,"SOB03","2024-05-20 04:40:14.965064","Torta de banana","SOBREMESA","2024-05-20 04:40:14.965064",2.00);
		""");
		return new JdbcTemplate(dataSource);
	}
	*/
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
