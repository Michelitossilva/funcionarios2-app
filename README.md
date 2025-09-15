# Gestão de Funcionários com Spring Boot
![Build](https://github.com/michelito/funcionarios2-app/actions/workflows/build.yml/badge.svg)


<!-- Badge do GitHub Actions irá aparecer aqui -->

## 🚀 Sobre o Projeto

Este projeto é uma aplicação web completa para a gestão de funcionários, desenvolvida com o moderno ecossistema **Spring Boot**. A aplicação representa a migração de uma arquitetura tradicional Jakarta EE (com WildFly) para uma abordagem mais leve, autónoma e pronta para a nuvem.

O objetivo é demonstrar a construção de uma aplicação robusta com Spring Boot, implementando todas as operações CRUD (Create, Read, Update, Delete) e seguindo as melhores práticas de desenvolvimento, como Injeção de Dependências e a separação de camadas.

## 🛠️ Tecnologias Utilizadas

* **Linguagem:** Java 17
* **Framework Principal:** Spring Boot 3
* **Acesso a Dados:** Spring Data JPA (com Hibernate)
* **Camada Web:** Spring MVC
* **Motor de Templates:** Thymeleaf
* **Banco de Dados:** PostgreSQL 17
* **Ferramenta de Build:** Apache Maven
* **Empacotamento:** Docker

## 🏛️ Arquitetura

A aplicação segue uma arquitetura de camadas clássica e bem definida:

* **`@Controller` (Camada Web):** Recebe as requisições HTTP do navegador e interage com a camada de serviço.
* **`@Service` (Camada de Serviço):** Contém a lógica de negócio da aplicação.
* **`@Repository` (Camada de Dados):** Abstrai o acesso ao banco de dados, utilizando o poder do Spring Data JPA.
* **`@Entity` (Modelo):** Representa os dados da aplicação e o seu mapeamento para as tabelas do banco de dados.

## ⚙️ Como Executar o Projeto

### Pré-requisitos
* JDK 17
* Apache Maven 3.6+
* PostgreSQL 17 a correr localmente.

### 1. Configuração do Banco de Dados
1.  Crie um banco de dados no PostgreSQL chamado `funcionarios`.
2.  Abra o ficheiro `src/main/resources/application.properties` e atualize as propriedades `spring.datasource.username` e `spring.datasource.password` com as suas credenciais.

### 2. Executar a Aplicação
1.  Navegue até à pasta raiz do projeto.
2.  Execute o seguinte comando Maven para iniciar a aplicação:
    ```bash
    mvn spring-boot:run
    ```
3.  Alternativamente, pode executar o método `main` da classe `FuncionariosApplication.java` diretamente na sua IDE.

### 3. Aceder à Aplicação
Abra o seu navegador e aceda ao seguinte URL:
**http://localhost:8080**