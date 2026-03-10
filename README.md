# Forum Hub Alura 
## (Challenge 02 de 02 do Oracle ONE G9 (2025-2026)

Status do Projeto: ✔️ Concluído em 10/mar/2026

---

## 🔹 Tópicos

- [Descrição do projeto](#descrição-do-projeto)
- [Funcionalidades](#funcionalidades)
- [Deploy da Aplicação / Layout](#deploy-da-aplicação--layout)
- [Pré-requisitos](#pré-requisitos)
- [Como rodar a aplicação](#como-rodar-a-aplicação)
- [Como rodar os testes](#como-rodar-os-testes)
- [Casos de Uso](#casos-de-uso)
- [JSON de exemplo](#json-💾)
- [Iniciando/Configurando banco de dados](#iniciandoconfigurando-banco-de-dados)
- [Linguagens, dependências e libs utilizadas 📚](#linguagens-dependencias-e-libs-utilizadas-📚)

---

## Descrição do projeto

O ForumHub é uma API REST desenvolvida em Java Spring Boot que permite gerenciar tópicos de um fórum, com autenticação segura baseada em JWT (JSON Web Token).  
A aplicação suporta operações de criação, consulta, atualização e exclusão de tópicos, protegendo rotas sensíveis com tokens e garantindo que apenas usuários autenticados possam acessar determinadas funcionalidades.

O projeto faz parte do desafio 02 da Trilha Java Spring Framework da Alura, e tem foco em boas práticas de segurança, arquitetura limpa e desenvolvimento de APIs REST.

---

## Funcionalidades

✔️ Cadastro de usuários  
✔️ Autenticação via JWT (/auth/login)  
✔️ Criação, consulta, atualização e exclusão de tópicos  
✔️ Validação de tokens para acesso a rotas protegidas  
✔️ Senha criptografada com BCrypt

---

## Deploy da Aplicação / Layout

Ainda não há deploy público.


---

## Pré-requisitos

⚠️ Ter instalado na máquina:

- Java 17+
- Maven 3.8+
- PostgreSQL
- Postman ou Insomnia (para testar a API)

---

## Como rodar a aplicação

No terminal, clone o projeto:

git clone https://github.com/johnny-dll/Alura_ONE_G9_Trilha_Java_Spring_Framework_-_Challenge_02_de_02.git
cd forumhub

Compile e rode a aplicação:

mvn clean install
mvn spring-boot:run

A API ficará disponível em: http://localhost:8080  
Teste login: POST /auth/login com JSON:

{
"email": "usuario@teste.com",
"senha": "senha123"
}

---

## Como rodar os testes

mvn test

Todos os testes unitários e de integração estão configurados com JUnit 5 e Spring Boot Test  
Os testes cobrem autenticação, geração de token e CRUD de tópicos

---

## Casos de Uso

Um usuário faz login, recebe o JWT e usa no header Authorization: Bearer <token> para acessar rotas protegidas  
Exemplo: criar um novo tópico:

POST /topicos
Headers: Authorization: Bearer <token>
Body:
{
"titulo": "Dúvida sobre Spring Boot",
"mensagem": "Como faço para atualizar um tópico?",
"cursoId": 1
}

Usuário não autenticado recebe 401 Unauthorized

---

## JSON 💾

Exemplo de tópico:

{
"id": 1,
"titulo": "Dúvida sobre Spring Boot",
"mensagem": "Como faço para atualizar um tópico?",
"dataCriacao": "2026-03-10T05:00:00",
"status": "NAO_RESPONDIDO",
"autor": "João Paulo",
"curso": "Spring Boot"
}

---

## Iniciando/Configurando banco de dados

O projeto utiliza PostgreSQL como banco de dados.

Antes de rodar a aplicação, configure o arquivo `application.properties` com suas credenciais:

spring.datasource.url=jdbc:postgresql://localhost:5432/forumhub
spring.datasource.username=seu_usuario
spring.datasource.password=sua_senha
spring.jpa.hibernate.ddl-auto=update

Certifique-se de que o banco `forumhub` exista no PostgreSQL.  
Após a configuração, ao rodar a aplicação, as tabelas serão criadas automaticamente pelo Hibernate.

---

## Linguagens, dependências e libs utilizadas 📚

Java 17  
Spring Boot 3.x  
Spring Security (com JWT)  
Spring Data JPA  
PostgreSQL  
Auth0 Java JWT  
Lombok (opcional)  
JUnit 5 para testes  
Postman / Insomnia para testes da API


## 👤 Autor

João Paulo Llorca

GitHub: https://github.com/johnny-dll  
LinkedIn: https://www.linkedin.com/in/joaopaulozllorca/