# Quarkus - Projeto Ifood

Esse é um projeto completo de Quarkus onde eu utilizei o README para fazer anotações interessantes que podem me ajudar no futuro.
**Esse projeto tem foco em aprender o framework Quarkus e não em utilizar as melhores práticas de desenvolvimento.**

## Informações úteis:

Versão do docker local utilizado: 17.06.2-ce.  
Swagger: http://localhost:8080/swagger-ui/  
Keycloak: http://localhost:8180/auth/  
Jaeger: http://localhost:16686/search  

## Projeto Cadastro

- Adicionar dependências:

mvn quarkus:add-extension -Dextensions="jdbc-postgres, orm-panache, resteasy-jsonb, openapi, hibernate-validator"  
mvn quarkus:add-extension -Dextensions="jwt"  
mvn quarkus:add-extension -Dextensions="smallrye-opentracing"  

- Subir imagens com docker-compose

docker-compose up

