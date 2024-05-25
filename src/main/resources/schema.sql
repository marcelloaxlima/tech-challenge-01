<<<<<<< Updated upstream
create database IF NOT EXISTS mydb
use mydb
create table clientes (id bigint not null auto_increment, codigo varchar(255), cpf varchar(255), data_criacao datetime(6) not null, nome varchar(255), ultima_modificacao datetime(6) not null, primary key (id)) engine=InnoDB
create table combo_pedidos (comboid bigint not null, pedidoid bigint not null, primary key (comboid, pedidoid)) engine=InnoDB
create table combo_produtos (comboid bigint not null, produtoid bigint not null, primary key (comboid, produtoid)) engine=InnoDB
create table combos (id bigint not null auto_increment, data_criacao datetime(6) not null, nome varchar(255), ultima_modificacao datetime(6) not null, cliente_id bigint, primary key (id)) engine=InnoDB
create table pedido_produtos (pedidoid bigint not null, produtoid bigint not null) engine=InnoDB
create table pedidos (id bigint not null auto_increment, codigo varchar(255), data_criacao datetime(6) not null, nome_cliente varchar(255), status tinyint, ultima_modificacao datetime(6) not null, valor decimal(10,2), primary key (id)) engine=InnoDB
create table produtos (id bigint not null auto_increment, codigo varchar(255), data_criacao datetime(6) not null, nome varchar(255), tipo_produto enum ('LANCHE','ACOMPANHAMENTO','BEBIDA','SOBREMESA'), ultima_modificacao datetime(6) not null, valor decimal(10,2), primary key (id)) engine=InnoDB
alter table clientes drop index UK_7wflw78ibh162cmq12ii6ffly
alter table clientes add constraint UK_7wflw78ibh162cmq12ii6ffly unique (cpf)
alter table produtos drop index UK_pk2k37y05kgqceufn556j55w3
alter table produtos add constraint UK_pk2k37y05kgqceufn556j55w3 unique (codigo)
alter table produtos drop index UK_68les18ejq8cjyxw9snrbtd7t
alter table produtos add constraint UK_68les18ejq8cjyxw9snrbtd7t unique (nome)
alter table combo_pedidos add constraint FKshw9fe3oe8chyuo28lb1rpr0t foreign key (pedidoid) references pedidos (id)
alter table combo_pedidos add constraint FKp3ts9to0hh45tcq5yju8ym8ak foreign key (comboid) references combos (id)
alter table combo_produtos add constraint FKd73mjma0bpqofc5xl813kv6sj foreign key (produtoid) references produtos (id)
alter table combo_produtos add constraint FKh1c032u7k7uo92vk4f4ct2eqv foreign key (comboid) references combos (id)
alter table combos add constraint FK4apeeplru304fy0mq364qx4r8 foreign key (cliente_id) references clientes (id)
alter table pedido_produtos add constraint FKmxbuag3gvejqw28rs0mq5uxub foreign key (produtoid) references produtos (id)
alter table pedido_produtos add constraint FK59k0dlxipto1kdpismergyxq8 foreign key (pedidoid) references pedidos (id)
alter table pedidos modify column status enum ('INICIADO','PAGO','PREPARO','PRONTO','FINALIZADO')
=======
-- create database IF NOT EXISTS mydb;

-- USE mydb;

-- create table clientes (id bigint not null auto_increment, codigo varchar(255), cpf varchar(255), data_criacao datetime(6) not null, nome varchar(255), ultima_modificacao datetime(6) not null, primary key (id)) engine=InnoDB;
-- create table combo_produtos (comboid bigint not null, produtoid bigint not null, primary key (comboid, produtoid)) engine=InnoDB;
-- create table combos (id bigint not null auto_increment, data_criacao datetime(6) not null, nome varchar(255), ultima_modificacao datetime(6) not null, cliente_id bigint, primary key (id)) engine=InnoDB;
-- create table pedido_produtos (pedidoid bigint not null, produtoid bigint not null) engine=InnoDB;
-- create table pedidos (id bigint not null auto_increment, codigo varchar(255), data_criacao datetime(6) not null, nome_cliente varchar(255), status enum ('INICIADO','PAGO','PREPARO','PRONTO','FINALIZADO'), ultima_modificacao datetime(6) not null, valor decimal(10,2), primary key (id)) engine=InnoDB;
-- create table produtos (id bigint not null auto_increment, codigo varchar(255), data_criacao datetime(6) not null, data_exclusao datetime(6), nome varchar(255), tipo_produto enum ('LANCHE','ACOMPANHAMENTO','BEBIDA','SOBREMESA'), ultima_modificacao datetime(6) not null, valor decimal(10,2), primary key (id)) engine=InnoDB;

-- alter table clientes drop index UK_7wflw78ibh162cmq12ii6ffly;
-- alter table clientes add constraint UK_7wflw78ibh162cmq12ii6ffly unique (cpf);
-- alter table produtos drop index UK_pk2k37y05kgqceufn556j55w3;
-- alter table produtos add constraint UK_pk2k37y05kgqceufn556j55w3 unique (codigo);
-- alter table produtos drop index UK_68les18ejq8cjyxw9snrbtd7t;
-- alter table produtos add constraint UK_68les18ejq8cjyxw9snrbtd7t unique (nome);
-- alter table combo_produtos add constraint FKd73mjma0bpqofc5xl813kv6sj foreign key (produtoid) references produtos (id);
-- alter table combo_produtos add constraint FKh1c032u7k7uo92vk4f4ct2eqv foreign key (comboid) references combos (id);
-- alter table combos add constraint FK4apeeplru304fy0mq364qx4r8 foreign key (cliente_id) references clientes (id);
-- alter table pedido_produtos add constraint FKmxbuag3gvejqw28rs0mq5uxub foreign key (produtoid) references produtos (id);
-- alter table pedido_produtos add constraint FK59k0dlxipto1kdpismergyxq8 foreign key (pedidoid) references pedidos (id);

-- INSERT INTO clientes (id,codigo,cpf,data_criacao,nome,ultima_modificacao) VALUES
-- (1,"001",12345678901,"2024-05-20 04:28:47.199275","Marcello","2024-05-20 04:30:49.203609"),
-- (2,"002",12345678902,"2024-05-20 04:31:55.115051","Matheus","2024-05-20 04:31:55.115051"),
-- (3,"003",12345678903,"2024-05-20 04:32:23.916552","Fabio","2024-05-20 04:32:23.916552"),
-- (4,"004",12345678904,"2024-05-20 04:32:40.768593","Eduardo","2024-05-20 04:32:40.768593");

-- INSERT INTO produtos (id,codigo,data_criacao,nome,tipo_produto,ultima_modificacao,valor) VALUES
-- (1,LAN01,"2024-05-20 04:35:49.465472","Hamburger","LANCHE","2024-05-20 05:03:51.524812",5.50),
-- (2,LAN02,"2024-05-20 04:36:37.007693","X-Hamburger","LANCHE","2024-05-20 04:36:37.007693",6.50),
-- (3,ACO01,"2024-05-20 04:37:21.025117","Batata frita","ACOMPANHAMENTO","2024-05-20 05:03:51.507303",4.00),
-- (4,ACO02,"2024-05-20 04:37:38.028388","Mandioca frita","ACOMPANHAMENTO","2024-05-20 04:37:38.028388",4.00),
-- (5,BEB01,"2024-05-20 04:38:18.347290","Coca Cola","BEBIDA","2024-05-20 05:03:51.524812",1.00),
-- (6,BEB02,"2024-05-20 04:38:45.564562","Guaraná","BEBIDA","2024-05-20 04:38:45.564562",1.00),
-- (7,BEB03,"2024-05-20 04:39:10.212640","Suco de laranja","BEBIDA","2024-05-20 04:39:10.212640",1.50),
-- (8,SOB01,"2024-05-20 04:39:36.275862","Pudim","SOBREMESA","2024-05-20 05:03:51.523812",1.50),
-- (9,SOB02,"2024-05-20 04:40:02.972659","Torta de maçã","SOBREMESA","2024-05-20 04:40:02.972659",2.00),
-- (10,SOB03,"2024-05-20 04:40:14.965064","Torta de banana","SOBREMESA","2024-05-20 04:40:14.965064",2.00);
>>>>>>> Stashed changes
