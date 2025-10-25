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
---

# 🧩 Pipeline CI/CD – Funcionários App

Este documento explica como funciona a pipeline de **Integração Contínua (CI)** e **Entrega Contínua (CD)** do projeto **Gestão de Funcionários com Spring Boot**, configurada com **GitHub Actions**.

---

## ⚙️ 1. Pré-requisitos

Antes de configurar a pipeline, você precisa garantir que:

- Possui uma conta no [Docker Hub](https://hub.docker.com);
- O repositório da aplicação está hospedado no GitHub;
- Há um repositório separado contendo os manifestos Kubernetes (ex: `funcionarios-k8s`);

---

## 🔑 2. Criar Token no Docker Hub

1. Acesse o [Docker Hub](https://hub.docker.com/settings/security);
2. Vá em **Security > New Access Token**;
3. Escolha um nome (ex: `github-actions`);
4. Copie o token gerado — ele será usado apenas uma vez.

---

## 🔐 3. Configurar Secrets no GitHub

No repositório **da aplicação (Spring Boot)**:

1. Vá em **Settings → Secrets and variables → Actions → New repository secret**;
2. Adicione os seguintes secrets:

| Nome | Descrição |
|------|------------|
| `DOCKERHUB_USERNAME` | Seu nome de usuário no Docker Hub |
| `DOCKERHUB_TOKEN` | Token gerado no Docker Hub |
| `TOKEN_GITHUB` | Token de acesso pessoal do GitHub com permissão para commit/push no repositório `funcionarios-k8s` |

> 💡 Para gerar o `TOKEN_GITHUB`, acesse [https://github.com/settings/tokens](https://github.com/settings/tokens) → “Personal access tokens (classic)”.

Como as seguintes pemissões:

<img width="863" height="377" alt="image" src="https://github.com/user-attachments/assets/15139949-e226-4d0e-a96f-f27c0430edfb" />

---

## 🧱 4. Estrutura da Pipeline

A pipeline é definida no arquivo `.github/workflows/build.yml` e contém **dois jobs principais**:

### 🏗️ Job 1 — Build
Responsável por compilar, testar e gerar a imagem Docker.

Etapas principais:
1. **Checkout do código**
2. **Configuração do JDK 17**
3. **Build e Testes com Maven**
4. **Login no Docker Hub**
5. **Build e Push da imagem Docker**  
   A imagem é enviada com duas tags:
   - `latest`
   - O hash curto do commit (`SHA`)

### 🚀 Job 2 — Deploy
Executado automaticamente após o build bem-sucedido.  
Atualiza o repositório de manifestos Kubernetes com a nova tag da imagem.

Etapas principais:
1. **Clona o repositório `funcionarios-k8s`**
2. **Instala o Kustomize**
3. **Atualiza a imagem no `kustomization.yaml`**
4. **Faz commit e push com a nova tag**

---

## 📘 Exemplo de Arquivo `build.yml`

```yaml
name: Build do Projeto Spring Boot

on:
  push:
    branches: [ "main" ]
  workflow_dispatch:

jobs:
  build:
    runs-on: ubuntu-latest
    outputs:
      image_sha: ${{ steps.generate_tag.outputs.sha }} 

    steps:
      - uses: actions/checkout@v4

      - name: Configurar JDK 17
        uses: actions/setup-java@v4
        with:
          java-version: '17'
          distribution: 'temurin'
          cache: maven

      - name: Compilar e testar
        run: mvn -B clean package test

      - name: Generate tag
        id: generate_tag
        run: |
          SHA=$(echo $GITHUB_SHA | head -c7)
          echo "sha=$SHA" >> $GITHUB_OUTPUT

      - name: Login no Docker Hub
        uses: docker/login-action@v3
        with:
          username: ${{ secrets.DOCKERHUB_USERNAME }}
          password: ${{ secrets.DOCKERHUB_TOKEN }}
      
      - name: Build e Push da Imagem
        uses: docker/build-push-action@v5
        with:
          push: true
          tags: |
            amakusashirou/funcionarios:${{ steps.generate_tag.outputs.sha }}
            amakusashirou/funcionarios:latest

  deploy:
     name: Deploy
     runs-on: ubuntu-latest
     needs: build

     steps:
      - uses: actions/checkout@v4
        with:
          repository: LuisCarlosJp/funcionarios-k8s
          token: ${{ secrets.TOKEN_GITHUB }}

      - uses: imranismail/setup-kustomize@v2

      - name: Atualizar imagem
        run: |
          cd kubernetes
          kustomize edit set image imagem=amakusashirou/funcionarios:${{ needs.build.outputs.image_sha }}

      - name: Commit e Push
        run: |
          git config --local user.email "action@github.com"
          git config --local user.name "Deploy Action"
          git add .
          git commit -m "Atualizando imagem para nova tag"
          git pull --rebase
          git push
```

---

## ✅ 5. Fluxo Completo

1. Desenvolvedor faz **push** na branch `main`;
2. GitHub Actions dispara o **job de build**;
3. Imagem é construída e enviada ao **Docker Hub**;
4. O **job de deploy** atualiza o repositório Kubernetes;
5. O **Argo CD** detecta a mudança e aplica o novo deploy automaticamente.
