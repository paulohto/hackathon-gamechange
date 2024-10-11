FROM openjdk:17-alpine

# Expor a porta da aplicação
EXPOSE 8080

# Definir o diretório de trabalho
WORKDIR /app

# Definir variáveis de ambiente
ENV SERVER_PORT=8080
ENV SPRING_CONFIG_MONGODB_SERVER=mongodb
ENV SPRING_CONFIG_MONGODB_URI=mongodb://root:example@mongo:27017/
# ENV SPRING_CONFIG_MONGODB_URI=mongodb://root:example@mongo:27017/gamechange
# ENV SPRING_DATA_MONGODB_URI=mongodb://root:example@mongo:27017/gamechange

# Copiar arquivos do Maven e dependências
COPY .mvn/ .mvn/
COPY mvnw pom.xml ./
RUN ./mvnw dependency:go-offline
# RUN chmod +x mvnw && ./mvnw dependency:go-offline

# Copiar o código-fonte
COPY src ./src

# Comando para rodar a aplicação
CMD ["./mvnw", "spring-boot:run"]
