# Viper Bank API

API RESTful construida em Java 17 com Spring Boot 3.

## Tecnologias

- **Java 17**
- **Spring Boot 3**
- **Spring Data JPA**
- **H2 Database** (desenvolvimento) / **PostgreSQL** (producao)
- **OpenAPI (Swagger)**
- **Lombok**

## Endpoints

| Metodo | Descricao |
|--------|-----------|
| `GET /users` | Listar todos os usuarios |
| `GET /users/{id}` | Buscar usuario por ID |
| `POST /users` | Criar novo usuario |
| `PUT /users/{id}` | Atualizar usuario |
| `DELETE /users/{id}` | Deletar usuario |

## Como rodar

```bash
# Com Maven
mvn spring-boot:run

# Ou buildar e rodar o jar
mvn clean package
java -jar target/api-0.0.1-SNAPSHOT.jar
```

## Documentacao (Swagger)

Acesse: `http://localhost:8080/swagger-ui.html`

## Dominio

A API gerencia usuarios bancarios com as seguintes entidades:
- **User** (nome, conta, features, cartao, noticias)
- **Account** (numero, agencia, saldo, limite)
- **Card** (numero, limite)
- **Feature** (icone, descricao)
- **News** (icone, descricao)
