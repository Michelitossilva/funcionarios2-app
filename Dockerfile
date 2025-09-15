# Fase 1: Build - Usar uma imagem Maven com Java 17 para compilar o projeto
FROM maven:3.9.6-eclipse-temurin-17-focal AS build

# Define o diretório de trabalho dentro do contentor
WORKDIR /app

# Copia o ficheiro pom.xml primeiro para aproveitar o cache de dependências do Docker
COPY pom.xml .

# Descarrega todas as dependências do projeto
RUN mvn dependency:go-offline

# Copia o resto do código-fonte
COPY src ./src

# Compila o projeto e gera o ficheiro .jar executável
# O -DskipTests acelera o build ao não executar os testes
RUN mvn package -DskipTests

# Fase 2: Run - Usar uma imagem leve apenas com Java 17 para executar a aplicação
FROM eclipse-temurin:17-jre-focal

# Define o diretório de trabalho
WORKDIR /app

# Copia o ficheiro .jar compilado da fase de build para a fase de execução
COPY --from=build /app/target/*.jar app.jar

# Expõe a porta 8080 para que o mundo exterior possa aceder à nossa aplicação
EXPOSE 8080

# O comando para iniciar a aplicação quando o contentor arrancar
ENTRYPOINT ["java", "-jar", "app.jar"]
