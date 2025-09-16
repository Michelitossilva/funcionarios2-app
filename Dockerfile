# Fase 1: Build - Usar uma imagem Maven com Java 17 para compilar o projeto
FROM maven:3.9.6-eclipse-temurin-17-focal AS build

# Define o diretório de trabalho dentro do container
WORKDIR /app

# Copia o pom.xml e baixa as dependências
COPY pom.xml .
RUN mvn dependency:go-offline

# Copia o restante do projeto (src, resources, etc.)
COPY . .

# Compila o projeto e gera o .jar
RUN mvn clean package -DskipTests

# Fase 2: Run - Usar uma imagem leve com Java 17 para executar a aplicação
FROM eclipse-temurin:17-jre-focal

# Define o diretório de trabalho
WORKDIR /app

# Copia o .jar gerado da fase de build
COPY --from=build /app/target/funcionarios-spring-boot-0.0.1-SNAPSHOT.jar app.jar

# Expõe a porta 8080
EXPOSE 8080

# Comando para iniciar a aplicação
ENTRYPOINT ["java", "-jar", "app.jar"]
