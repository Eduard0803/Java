# 🔐 API de Autenticação com Spring Boot

API REST desenvolvida com **Spring Boot** para autenticação e gerenciamento de usuários, utilizando **JWT (JSON Web Token)** para controle de acesso seguro a endpoints protegidos.

---

## 📌 Sobre o projeto

Esta aplicação tem como objetivo fornecer uma base sólida de autenticação para sistemas backend, permitindo:

* Registro de usuários
* Autenticação via login
* Geração de token JWT
* Proteção de rotas com Spring Security
* Controle de acesso baseado em token

A API segue boas práticas de organização em camadas e pode ser utilizada como base para sistemas maiores ou microsserviços.

---

## ⚙️ Funcionalidades

* 🔐 Autenticação de usuários com JWT
* 👤 Cadastro e gerenciamento de usuários
* 🛡️ Proteção de endpoints com Spring Security
* 📄 Documentação da API com Swagger
* 🔄 Validação de requisições via filtros de segurança

---

## 🧱 Arquitetura

O projeto está organizado nas seguintes camadas:

* **Controller** → Exposição dos endpoints REST
* **DTO** → Transferência de dados entre cliente e API
* **Entity** → Representação das tabelas no banco
* **Repository** → Acesso aos dados com JPA
* **Security** → Configurações de autenticação e autorização
* **Util** → Geração e validação de tokens JWT

---

## 🛠️ Tecnologias utilizadas

* Java
* Spring Boot
* Spring Security
* Spring Data JPA
* JWT (JSON Web Token)
* MySQL
* Swagger / OpenAPI

---

## 🔄 Fluxo de autenticação

1. O usuário realiza login com suas credenciais
2. A API valida os dados e gera um token JWT
3. O cliente envia o token no header das requisições:

```
Authorization: Bearer <token>
```

4. O token é validado por um filtro de segurança
5. Acesso liberado aos endpoints protegidos

---

## 🚀 Como executar o projeto

1. Clone o repositório
2. Configure o banco de dados MySQL no `application.properties`
3. Execute a aplicação:

```
./mvnw spring-boot:run
```

4. Acesse a documentação Swagger:

```
http://localhost:8080/swagger-ui.html
```

---

## 💡 Possíveis melhorias

* Implementação de camada de Service
* Tratamento global de exceções
* Criptografia de senhas com BCrypt
* Refresh Token
* Controle de roles/perfis de usuário
