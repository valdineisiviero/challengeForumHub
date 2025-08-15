# API ForumHub

![Java](https://img.shields.io/badge/Java-17+-orange)
![Spring Boot](https://img.shields.io/badge/Spring_Boot-3.x-green)
![Maven](https://img.shields.io/badge/Maven-4.0.0-blue)

## 📄 Descrição do Projeto

**ForumHub** é uma API RESTful simples, desenvolvida como parte de um desafio de programação. O objetivo é simular o back-end de um fórum de discussões, permitindo que os usuários criem, leiam, atualizem e deletem tópicos.

Esta versão do projeto utiliza um **banco de dados em memória** para armazenar os tópicos, o que significa que todos os dados serão perdidos quando a aplicação for reiniciada. É uma implementação focada na lógica de negócio e na estrutura da API.

---

## ✨ Funcionalidades

A API oferece as seguintes funcionalidades (CRUD completo):

-   ✅ **Criar** um novo tópico.
-   ✅ **Listar** todos os tópicos cadastrados.
-   ✅ **Detalhar** um tópico específico por ID.
-   ✅ **Atualizar** as informações de um tópico existente.
-   ✅ **Excluir** um tópico.

---

## 🛠️ Tecnologias Utilizadas

-   **Java 17**: Utilizando recursos modernos da linguagem, como `records` para DTOs.
-   **Spring Boot 3**: Framework principal para a criação da API REST, gerenciamento de dependências e servidor web embutido.
-   **Maven**: Ferramenta de gerenciamento de projetos e dependências.

---

## 🚀 Como Executar o Projeto

Para executar este projeto localmente, você precisará ter o Java (JDK 17 ou superior) e o Maven instalados.

1.  **Clone o repositório:**
    ```bash
    git clone [https://github.com/valdineisiviero/challengeForumHub.git](https://github.com/valdineisiviero/challengeForumHub.git)
    ```

2.  **Navegue até a pasta do projeto:**
    ```bash
    cd challengeForumHub/forumhub
    ```

3.  **Execute a aplicação com o Maven:**
    ```bash
    mvn spring-boot:run
    ```

4.  A API estará disponível em `http://localhost:8080`.

---

## 📚 Endpoints da API

A seguir estão detalhados os endpoints disponíveis e como interagir com eles.

### 1. Criar um Novo Tópico

-   **Método:** `POST`
-   **URL:** `/topico`
-   **Descrição:** Cria um novo tópico no fórum.
-   **Corpo da Requisição (JSON):**
    ```json
    {
      "titulo": "Dúvida sobre Spring Boot",
      "mensagem": "Como posso configurar um banco de dados H2?",
      "autor": "Ana da Silva",
      "curso": "Java e Spring"
    }
    ```
-   **Resposta de Sucesso (Código `201 Created`):**
    ```json
    {
        "id": 1,
        "titulo": "Dúvida sobre Spring Boot",
        "mensagem": "Como posso configurar um banco de dados H2?",
        "dataCriacao": "2025-08-14T23:29:33.123456",
        "status": "NAO_RESPONDIDO",
        "autor": "Ana da Silva",
        "curso": "Java e Spring"
    }
    ```

### 2. Listar Todos os Tópicos

-   **Método:** `GET`
-   **URL:** `/topico`
-   **Descrição:** Retorna uma lista com os principais dados de todos os tópicos.
-   **Resposta de Sucesso (Código `200 OK`):**
    ```json
    [
        {
            "id": 1,
            "titulo": "Dúvida sobre Spring Boot",
            "autor": "Ana da Silva",
            "curso": "Java e Spring",
            "status": "NAO_RESPONDIDO"
        }
    ]
    ```

### 3. Detalhar um Tópico Específico

-   **Método:** `GET`
-   **URL:** `/topico/{id}`
-   **Descrição:** Busca e retorna todos os detalhes de um tópico pelo seu `id`.
-   **Resposta de Sucesso (Código `200 OK`):**
    ```json
    {
        "id": 1,
        "titulo": "Dúvida sobre Spring Boot",
        "mensagem": "Como posso configurar um banco de dados H2?",
        "dataCriacao": "2025-08-14T23:29:33.123456",
        "status": "NAO_RESPONDIDO",
        "autor": "Ana da Silva",
        "curso": "Java e Spring"
    }
    ```
-   **Resposta de Erro (Código `404 Not Found`):** Se o tópico com o ID informado não for encontrado.

### 4. Atualizar um Tópico

-   **Método:** `PUT`
-   **URL:** `/topico/{id}`
-   **Descrição:** Atualiza o título, mensagem ou status de um tópico existente.
-   **Corpo da Requisição (JSON):** (Envie apenas os campos que deseja alterar)
    ```json
    {
      "titulo": "Dúvida sobre Spring [Resolvido]",
      "status": "FECHADO"
    }
    ```
-   **Resposta de Sucesso (Código `200 OK`):** Retorna o tópico com os dados atualizados.

### 5. Excluir um Tópico

-   **Método:** `DELETE`
-   **URL:** `/topico/{id}`
-   **Descrição:** Remove um tópico do sistema.
-   **Resposta de Sucesso (Código `204 No Content`):** Retorna uma resposta vazia, indicando que o recurso foi removido com sucesso.
-   **Resposta de Erro (Código `404 Not Found`):** Se o tópico com o ID informado não for encontrado.

---

## ✒️ Autor

**Valdinei Siviero**

-   GitHub: [@valdineisiviero](https://github.com/valdineisiviero)
