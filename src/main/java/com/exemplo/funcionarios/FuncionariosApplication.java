package com.exemplo.funcionarios;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Classe principal que inicia a aplicação Spring Boot.
 * A anotação @SpringBootApplication combina várias anotações,
 * incluindo a configuração automática e a varredura de componentes.
 */
@SpringBootApplication
public class FuncionariosApplication {

    public static void main(String[] args) {
        SpringApplication.run(FuncionariosApplication.class, args);
    }

}

