# Viper Bank API

API RESTful construída em Java 21 com Spring Boot 3.

## Tecnologias

- **Java 21**
- **Spring Boot 3**
- **Spring Data JPA**
- **H2 Database** (desenvolvimento) / **PostgreSQL** (produção)
- **OpenAPI (Swagger)**
- **Lombok**

## Endpoints

| Método | Descrição |
|--------|-----------|
| `GET /users` | Listar todos os usuários |
| `GET /users/{id}` | Buscar usuário por ID |
| `POST /users` | Criar novo usuário |
| `PUT /users/{id}` | Atualizar usuário |
| `DELETE /users/{id}` | Deletar usuário |

## Como rodar

```bash
# Com Maven
mvn spring-boot:run

# Ou buildar e rodar o jar
mvn clean package
java -jar target/api-0.0.1-SNAPSHOT.jar
```

## Documentação (Swagger)

Acesse: `http://localhost:8081/swagger-ui.html`

## Domínio

A API gerencia usuários bancários com as seguintes entidades:
- **User** (nome, conta, features, cartão, notícias)
- **Account** (número, agência, saldo, limite)
- **Card** (número, limite)
- **Feature** (ícone, descrição)
- **News** (ícone, descrição)
