
# Projeto CRUD Empresa-Fornecedor

Este é um projeto CRUD que gerencia o relacionamento entre empresas e fornecedores em que um fornecedor trabalhar pra mais de uma empresa e uma empresa pode ter mais de um fornecedor. O sistema foi desenvolvido utilizando as seguintes tecnologias:

- **Frontend**: Vue.js
- **Backend**: Spring Boot
- **Banco de Dados**: PostgreSQL

## Tecnologias Utilizadas

- **Front-end**: Vue.js, Axios, Ag-Grid, Vue Router;
- **Back-end**: Spring Boot, JPA, Hibernate, Lombook.

## Como Executar o Projeto

### Pré-requisitos
- Node.js e npm instalados (para o frontend).
- Java JDK 11 ou superior (para o backend).
- PostgreSQL instalado e configurado.

### Passos para Execução

1. **Clone o repositório**:
   ```bash
   git clone https://github.com/ElMigu17/PSAccenture.git
   cd PSAccenture
   ```

2. **Banco de Dados**:
   - Importe o arquivo fornempre-schema.sql no pgAdmin

3. **Backend**:
   - Abra o projeto Spring Boot na sua IDE.
   - Configure o arquivo `application.properties` com as credenciais do seu banco de dados PostgreSQL.
   - Execute a aplicação Spring Boot.

4. **Frontend**:
   - Navegue até a pasta do frontend:
     ```bash
     cd frontend
     ```
   - Instale as dependências:
     ```bash
     npm install
     ```
   - Execute o servidor de desenvolvimento:
     ```bash
     npm run serve
     ```

5. **Acesse a aplicação**:
   - Abra o navegador e acesse `http://localhost:5173` (ou a porta configurada no frontend).
