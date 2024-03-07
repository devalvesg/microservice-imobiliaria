# Use uma imagem base com Java e Maven para compilar os serviços
FROM maven:3.8.4 AS build

# Define o diretório de trabalho
WORKDIR /app

# Copia todos os arquivos do diretório atual para o diretório de trabalho
COPY . .

WORKDIR /app/imob-back

# Compila o código usando o Maven
RUN mvn clean install -DskipTests

WORKDIR ..

WORKDIR /app/email-sending

RUN mvn clean install -DskipTests

WORKDIR ..
# Configuração da imagem final
FROM openjdk:17-jdk-slim

# Define o diretório de trabalho para a aplicação "imob-back"
WORKDIR /app/imob-back

# Copia o artefato compilado da fase anterior
COPY --from=build /app/imob-back/target/docker-spring-boot.jar app-service.jar

# Expõe a porta necessária para a aplicação
EXPOSE 3000

# Comando de entrada para iniciar a aplicação "imob-back"
ENTRYPOINT ["java", "-jar", "app-service.jar"]


# Define o diretório de trabalho para a aplicação "email-sending"
WORKDIR /app/email-sending

# Copia o artefato compilado da fase anterior
COPY --from=build /app/email-sending/target/email-sending-0.0.1-SNAPSHOT.jar email-service.jar

# Expõe a porta necessária para a aplicação
EXPOSE 3001

# Comando de entrada para iniciar a aplicação "email-sending"
ENTRYPOINT ["java", "-jar", "email-service.jar"]

