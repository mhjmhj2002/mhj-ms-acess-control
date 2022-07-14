# mhj-ms-acess-control

Access Control:
Projeto para controle de acesso para micro serviços, tecnologia usada:

- Java 11
- Spring boot 2.1.5.RELEASE
- spring cloud
- spring security
- JPA
- Mysql
- Jackson
- Lombok
- Mapstruct
Pendentes:
- Swagger 
- Junit

Banco de dados: 
Criar BD SQL e adicionar os dados da conexão nas variaveis de ambiente (application.properties),o start do projeto cria as tabelas.

Start do projeto (realizar o mvn install primeiro):
- No Eclipse pode ser criada uma run configuration ou dar start na classe principal MhjMsAcessControlApplication.java
- passando as vars de ambiente fora do arquivo de properties:
Adicionar as variaveis de ambiente em run configurations->enviroments
- via comando: 
mvn spring-boot:run
- passando as vars de ambiente fora do arquivo de properties:
mvn spring-boot:run -Dspring-boot.run.arguments=--PASS=<senha-do-bd>,--USER=<usuario-do-bd>,--URL=jdbc:mysql://<ip-do-bd>/<nome-do-bd>?useTimezone=true&serverTimezone=UTC

Acessar o banco depois do projeto ter cirado as tabelas e adicionar:
role: exemplo root
user: exemplo user@gmail.com
user_role: incluir a PK do user e do role.

Exemplo de request para buscar token:
curl --location --request POST 'http://localhost:8080/api/signin/' \
--header 'Content-Type: application/json' \
--data-raw '{
    "username":"username",
    "password":"password"
}'

