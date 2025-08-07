# Sistema de Gestão de Doações para Bazar

Este projeto é um sistema web desenvolvido para a disciplina de Desenvolvimento Web 2, com o objetivo de gerenciar o registro de doações de produtos apreendidos por órgãos fiscalizadores para entidades filantrópicas (órgãos donatários) que realizam bazares para arrecadar fundos.

O sistema foi construído utilizando uma arquitetura baseada em Spring Boot com persistência de dados via JDBC puro.

## Funcionalidades Implementadas

- **CRUD de Órgãos Fiscalizadores:** Cadastro, leitura, atualização e exclusão de órgãos que realizam as doações (ex: Receita Federal).
- **CRUD de Órgãos Donatários:** Cadastro, leitura, atualização e exclusão de entidades filantrópicas que recebem as doações (ex: bazares beneficentes).
- **CRUD de Produtos:** Cadastro, leitura, atualização e exclusão dos produtos que podem compor um lote de doação.
- **Gestão de Lotes:**
    - Cadastro de um Lote de doação, associando um órgão fiscalizador, um órgão donatário e uma lista de produtos disponíveis.
    - Listagem de todos os lotes cadastrados.
    - Visualização detalhada de cada lote (observação e lista de produtos) através de um modal interativo.
- **Filtros Dinâmicos:**
    - Filtragem da lista de lotes por Órgão Fiscalizador.
    - Filtragem da lista de lotes por Órgão Donatário.

## Tecnologias Utilizadas

- **Backend:** Java 17, Spring Boot, Spring Web
- **Frontend:** Thymeleaf, HTML5, CSS3, Bootstrap 5
- **Persistência:** JDBC API puro
- **Banco de Dados:** MySQL 8.0
- **Build e Dependências:** Apache Maven
- **Ambiente:** Docker

## Pré-requisitos para Execução

- JDK 17 (ou superior)
- Apache Maven 3.8+
- Docker Desktop (com suporte a WSL 2, se estiver no Windows)


## Estrutura do Projeto

O projeto segue a arquitetura padrão do Spring Boot, com a seguinte divisão de pacotes:

- `com.luxkao.bazar.controllers`: Contém os Controllers (que gerenciam as requisições web), os Converters e a classe principal da aplicação.
- `com.luxkao.bazar.model.entities`: Contém as classes de entidade (POJOs) que modelam os dados do negócio.
- `com.luxkao.bazar.model.repositories`: Contém as classes responsáveis pela persistência de dados (camada de acesso a dados), utilizando JDBC e a classe `RepositoryFacade`.
- `src/main/resources/templates`: Contém as views HTML renderizadas pelo Thymeleaf.
- `src/main/resources/static`: Contém os arquivos estáticos, como o arquivo CSS customizado.

## Como Executar o Projeto

1.  **Clone o repositório:**
    ```bash
    git clone https://github.com/luxkao/SistemaBazar.git
    cd SistemaBazar
    ```

2.  **Inicie o Banco de Dados com Docker:**
    Abra um terminal e execute o comando abaixo para iniciar um container Docker com o banco de dados MySQL já configurado.
    ```bash
    docker run --name bazar-mysql -e MYSQL_ROOT_PASSWORD=root -e MYSQL_DATABASE=bazar_db -p 3306:3306 -d mysql:8.0
    ```

3.  **Crie as Tabelas:**
    Conecte-se ao banco de dados `bazar_db` (usando um cliente como DBeaver, MySQL Workbench, etc.) e execute o script encontrado em `src/main/resources/sql/schema.sql` para criar todas as tabelas necessárias.

4.  **Execute a Aplicação Spring Boot:**
    Ainda no terminal, na raiz do projeto, execute o comando Maven para iniciar a aplicação.
    ```bash
    mvn spring-boot:run
    ```

5.  **Acesse a Aplicação:**
    Abra seu navegador e acesse: [http://localhost:8080/lotes](http://localhost:8080/lotes)

---