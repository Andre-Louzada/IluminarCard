# Estágio 1: Build (Compilação do Java)
FROM maven:3.9-eclipse-temurin-17-alpine AS build
WORKDIR /app
# Copia o arquivo de configuração do Maven e as dependências primeiro (cache)
COPY pom.xml .
RUN mvn dependency:go-offline

# Copia o código fonte (que já inclui o seu front-end em static)
COPY src ./src
# Gera o arquivo .jar do Spring Boot
RUN mvn clean package -DskipTests

# Estágio 2: Runtime (Execução)
FROM eclipse-temurin:17-jre-alpine
WORKDIR /app
# Copia apenas o arquivo final compilado do estágio anterior
COPY --from=build /app/target/*.jar app.jar

EXPOSE 8080
ENTRYPOINT ["java", "-jar", "app.jar"]