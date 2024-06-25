# Use uma imagem base do OpenJDK
FROM openjdk:17-jdk-slim

# Defina o diretório de trabalho no contêiner
WORKDIR /app

# Copie o arquivo JAR da sua aplicação para o contêiner
COPY build/libs/microsservicos-0.0.1-SNAPSHOT-plain.jar /app/ServicoAssinaturasValidas.jar

# Comando para executar a aplicação
ENTRYPOINT ["java", "-jar", "/app/ServicoAssinaturasValidas.jar"]