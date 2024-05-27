CREATE DATABASE IF NOT EXISTS mydb;
USE mydb;

CREATE TABLE clientes (
    id BIGINT NOT NULL AUTO_INCREMENT,
    codigo VARCHAR(255),
    cpf VARCHAR(255),
    data_criacao DATETIME(6) NOT NULL,
    nome VARCHAR(255),
    ultima_modificacao DATETIME(6) NOT NULL,
    PRIMARY KEY (id)
) ENGINE=InnoDB;

CREATE TABLE combo_produtos (
    comboid BIGINT NOT NULL,
    produtoid BIGINT NOT NULL,
    PRIMARY KEY (comboid, produtoid)
) ENGINE=InnoDB;

CREATE TABLE combos (
    id BIGINT NOT NULL AUTO_INCREMENT,
    data_criacao DATETIME(6) NOT NULL,
    nome VARCHAR(255),
    ultima_modificacao DATETIME(6) NOT NULL,
    cliente_id BIGINT,
    PRIMARY KEY (id)
) ENGINE=InnoDB;

CREATE TABLE pedido_produtos (
    pedidoid BIGINT NOT NULL,
    produtoid BIGINT NOT NULL
) ENGINE=InnoDB;

CREATE TABLE pedidos (
    id BIGINT NOT NULL AUTO_INCREMENT,
    codigo VARCHAR(255),
    data_criacao DATETIME(6) NOT NULL,
    nome_cliente VARCHAR(255),
    status ENUM('INICIADO','PAGO','PREPARO','PRONTO','FINALIZADO'),
    ultima_modificacao DATETIME(6) NOT NULL,
    valor DECIMAL(10,2),
    PRIMARY KEY (id)
) ENGINE=InnoDB;

CREATE TABLE produtos (
    id BIGINT NOT NULL AUTO_INCREMENT,
    codigo VARCHAR(255),
    data_criacao DATETIME(6) NOT NULL,
    data_exclusao DATETIME(6),
    nome VARCHAR(255),
    tipo_produto ENUM('LANCHE','ACOMPANHAMENTO','BEBIDA','SOBREMESA'),
    ultima_modificacao DATETIME(6) NOT NULL,
    valor DECIMAL(10,2),
    PRIMARY KEY (id)
) ENGINE=InnoDB;

ALTER TABLE clientes ADD CONSTRAINT UK_7wflw78ibh162cmq12ii6ffly UNIQUE (cpf);
ALTER TABLE produtos ADD CONSTRAINT UK_pk2k37y05kgqceufn556j55w3 UNIQUE (codigo);
ALTER TABLE produtos ADD CONSTRAINT UK_68les18ejq8cjyxw9snrbtd7t UNIQUE (nome);
ALTER TABLE combo_produtos ADD CONSTRAINT FKd73mjma0bpqofc5xl813kv6sj FOREIGN KEY (produtoid) REFERENCES produtos (id);
ALTER TABLE combo_produtos ADD CONSTRAINT FKh1c032u7k7uo92vk4f4ct2eqv FOREIGN KEY (comboid) REFERENCES combos (id);
ALTER TABLE combos ADD CONSTRAINT FK4apeeplru304fy0mq364qx4r8 FOREIGN KEY (cliente_id) REFERENCES clientes (id);