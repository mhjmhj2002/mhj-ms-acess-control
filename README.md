# mhj-ms-acess-control

Access Control:
Criar BD SQL e adicionar a conexao nas variaveis de ambiente (application.properties), o projeto cria as tabelas.

Acessar o banco depois de criadas as tabelas e adicionar:
role:
user:
user_role:

Request example: 
curl --location --request POST 'http://localhost:8080/api/signin/' \
--header 'Content-Type: application/json' \
--data-raw '{
    "username":"username",
    "password":"password"
}'

