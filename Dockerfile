FROM openjdk:17-alpine

# Expor a porta da aplicação
EXPOSE 8080

# Definir o diretório de trabalho
WORKDIR /app

# Copiar os arquivos necessários para compilar o projeto
COPY .mvn/ .mvn/
COPY mvnw pom.xml ./

# Baixar as dependências do Maven
RUN ./mvnw dependency:go-offline

# Copiar o código-fonte
COPY src ./src

# Compilar o projeto e criar o arquivo JAR
RUN ./mvnw clean package -DskipTests

# Definir variáveis de ambiente
ENV SPRING_CONFIG_MONGODB_URI=mongodb://root:example@mongo:27017/gamechangedb

# Copiar o arquivo JAR gerado para o diretório de trabalho
COPY target/*.jar app.jar

# Comando para rodar a aplicação usando o JAR
ENTRYPOINT ["java", "-jar", "/app/app.jar"]
