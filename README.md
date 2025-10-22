# Gestão de Funcionários com Spring Boot
![Build Status](https://github.com/Michelitossilva/funcionarios-app/actions/workflows/build.yml/badge.svg?branch=main)


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

## 🟢 Pipeline de Build e Testes (GitHub Actions)

O projeto possui uma **pipeline automatizada** que roda sempre que você faz push na branch principal ou executa manualmente no GitHub.

## 📌 Objetivo da Pipeline
A pipeline automatiza tarefas importantes para manter o projeto saudável:

1. Compilar o projeto Java com Maven  
2. Executar testes unitários  
3. Verificar codificação UTF-8 nos arquivos de configuração
> Essa automação garante que você não precisa executar tudo manualmente e evita erros básicos.

---
## 🛠️ Pré-requisitos
Antes de configurar a pipeline, você precisa ter:

- Um repositório no **GitHub**  
- Código do projeto hospedado nesse repositório  
- Java 17 instalado (para reproduzir localmente)  
- Maven instalado (para reproduzir localmente)  

---
## ⚙️ Configuração da Pipeline no GitHub

1. **Criar a pasta de workflows**
No seu repositório, crie a pasta: .github/workflows
2. **Criar o arquivo da pipeline**
Dentro de `.github/workflows`, crie um arquivo chamado: build.yml

3. **Adicionar o conteúdo da pipeline**
Copie e cole o seguinte código YAML no `build.yml`:

```yaml
name: Build do Projeto Spring Boot

on:
  push:
    branches: [ "main" ]
  workflow_dispatch: # permite rodar manualmente

jobs:
  build:
    runs-on: ubuntu-latest

    steps:
      # 1️⃣ Baixa o código do repositório
      - name: Checkout do código
        uses: actions/checkout@v4

      # 2️⃣ Configura Java 17 e Maven
      - name: Configurar JDK 17
        uses: actions/setup-java@v4
        with:
          java-version: '17'
          distribution: 'temurin'
          cache: maven

      # 3️⃣ Verifica codificação UTF-8
      - name: Verificar codificação dos arquivos
        run: |
          file src/main/resources/application.properties || echo "Arquivo não encontrado"
          iconv -f UTF-8 -t UTF-8 src/main/resources/application.properties > /dev/null || echo "Codificação inválida"

      # 4️⃣ Compila o projeto
      - name: Compilar com Maven
        run: mvn -B clean package --file pom.xml

      # 5️⃣ Executa os testes
      - name: Executar testes
        run: mvn test
```
**Reproduzir a Pipeline Localmente**
# Compilar o projeto
mvn clean package

# Rodar os testes
mvn test

## 🔗 Ver Pipeline no GitHub

Depois de subir o arquivo `build.yml` no repositório:

1. Acesse **Actions** no seu repositório do GitHub.  
2. Você verá a pipeline rodando automaticamente a cada push na branch `main`.  
3. Também é possível rodar manualmente clicando em **Run workflow**.

---

## 💡 Dicas para Iniciantes

- Sempre teste localmente antes de subir alterações no GitHub.  
- A pipeline é uma forma de **automatizar tarefas repetitivas**.  
- Você pode adicionar novas etapas depois, como envio de **relatórios de teste** ou **deploy automático**.
