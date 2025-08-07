-- Script para criação do schema completo do banco de dados 'bazar_db'
-- A ordem de criação respeita as dependências de chaves estrangeiras (Foreign Keys).

-- 1. Tabela para os órgãos que fiscalizam e doam os produtos
CREATE TABLE orgao_fiscalizador (
                                    id INT PRIMARY KEY AUTO_INCREMENT,
                                    nome VARCHAR(100) NOT NULL,
                                    descricao TEXT
);

-- 2. Tabela para as entidades que recebem as doações
CREATE TABLE orgao_donatario (
                                 id INT PRIMARY KEY AUTO_INCREMENT,
                                 nome VARCHAR(100) NOT NULL,
                                 endereco VARCHAR(255),
                                 telefone VARCHAR(20),
                                 horarioFuncionamento VARCHAR(100),
                                 descricao TEXT
);

-- 3. Tabela para os produtos individuais
CREATE TABLE produto (
                         codigo INT PRIMARY KEY AUTO_INCREMENT,
                         nome VARCHAR(150) NOT NULL,
                         descricao TEXT,
                         id_lote INT NULL
);

-- 4. Tabela central que representa um lote de doação
CREATE TABLE lote (
                      id INT PRIMARY KEY AUTO_INCREMENT,
                      data_entrega DATE NOT NULL,
                      observacao TEXT,
                      id_orgao_fiscalizador INT,
                      id_orgao_donatario INT,
                      FOREIGN KEY (id_orgao_fiscalizador) REFERENCES orgao_fiscalizador(id),
                      FOREIGN KEY (id_orgao_donatario) REFERENCES orgao_donatario(id)
);

-- 5. Agora que a tabela 'lote' existe, adicionamos a restrição de chave estrangeira à tabela 'produto'
ALTER TABLE produto ADD CONSTRAINT fk_produto_lote FOREIGN KEY (id_lote) REFERENCES lote(id);

