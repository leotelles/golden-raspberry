# Golden Raspberry Awards

### Tecnologias utilizadas:
* Java 8
* SpringBoot 2.7.7
* Banco de dados H2
* Maven

## Executando o projeto:

Para inicializar o projeto, execute o script `mvnw`, presente na pasta raíz do projeto, com o parâmetro `spring-boot:run`:

```bash
./mvnw spring-boot:run
```

## Executando os testes:

Para rodar os testes de integração, execute o script `mvnw`, presente na pasta raíz do projeto, com o parâmetro `test`:

```bash
./mvnw test
```

## Para utilizar a API:

```
http://localhost:8080/golden-raspberry-awards/min-max-interval
```

> Obs: o arquivo `movielist.csv`, presente na pasta `src/main/resources` do projeto, é carregado e persistido automaticamente após a inicialização da aplicação.
