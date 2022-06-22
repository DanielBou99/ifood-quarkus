# Quarkus - Projeto Ifood

Esse é um projeto completo de Quarkus onde eu utilizei o README para fazer anotações interessantes que podem me ajudar no futuro.  
**Esse projeto tem foco em aprender o framework Quarkus e não em utilizar as melhores práticas de desenvolvimento.**

## Informações úteis:

Versão do docker local utilizado: 20.10.16.
Swagger Projeto Cadastro: http://localhost:8080/swagger-ui/  
Swagger Projeto Marketplace: http://localhost:8081/swagger-ui/  
Swagger Projeto Pedido: http://localhost:8082/swagger-ui/  
Keycloak: http://localhost:8180/auth/  
Jaeger: http://localhost:16686/search  
Prometeus: http://localhost:9090/graph  
Grafana: http://localhost:3000/login  
ActiveMQ: http://localhost:8161/ - http://localhost:8161/console  
Kafka: http://localhost:9000/  
Kibana: http://localhost:5601/app/kibana/  
ElasticSearch: http://localhost:9200/  

## Projeto Cadastro

- Adicionar dependências:

mvn quarkus:add-extension -Dextensions="jdbc-postgres, orm-panache, resteasy-jsonb, openapi, hibernate-validator"  
mvn quarkus:add-extension -Dextensions="jwt"  
mvn quarkus:add-extension -Dextensions="smallrye-opentracing"  
mvn quarkus:add-extension -Dextensions="metrics"  
mvn quarkus:add-extension -Dextensions="flyway"  

- Subir imagens com docker-compose

docker-compose up  

- Build do prometheus  
docker build -f Dockerfile.prometheus -t prometheus-ifood .  

## Projeto MarketPlace  

- Adicionar dependências:  

mvn quarkus:add-extension -Dextensions="resteasy-multiny,jdbc-postgres,flyway,pg-client"  
mvn quarkus:add-extension -Dextensions="resteasy-jsonb, openapi"  
mvn quarkus:add-extension -Dextensions="amqp"  
mvn quarkus:add-extension -Dextensions="kafka"  

## Projeto Pedido  

mvn quarkus:add-extension -Dextensions="resteasy-jsonb, openapi, mongodb-panache"  
mvn quarkus:add-extension -Dextensions="kafka"  
mvn quarkus:add-extension -Dextensions="gelf"  
