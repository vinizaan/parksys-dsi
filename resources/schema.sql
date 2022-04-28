/*
Armazene neste arquivo o _schema_ do seu banco de dados (CREATE TABLE, etc.).

*/
DROP DATABASE IF EXISTS projetodsi;
CREATE DATABASE IF NOT EXISTS projetodsi;

USE projetodsi;

DROP TABLE IF EXISTS configuracao;
DROP TABLE IF EXISTS entrada_saida;
DROP TABLE IF EXISTS pagamento_mensalista;
DROP TABLE IF EXISTS veiculos_mensalista;
DROP TABLE IF EXISTS mensalista;


CREATE TABLE IF NOT EXISTS mensalista (
    cpf CHAR(14) NOT NULL,
    nome VARCHAR(35),
	telefone CHAR (15),
	saldo_atual INTEGER,
	obs VARCHAR(150),
    CONSTRAINT pk_mensalista PRIMARY KEY (cpf)
);

CREATE TABLE IF NOT EXISTS veiculos_mensalista (
    cpf CHAR(14) NOT NULL,
    placa VARCHAR(10) NOT NULL,
	descricao_veiculo  VARCHAR(40),
	UNIQUE KEY uk_placa (placa),
    CONSTRAINT fk_vc_mensalista FOREIGN KEY (cpf) REFERENCES mensalista(cpf)
);

CREATE TABLE IF NOT EXISTS pagamento_mensalista (
    cpf CHAR(14) NOT NULL,
    data_pagamento DATETIME,
    valor_pago DECIMAL(6,2),
    CONSTRAINT fk_pgto_mensalista FOREIGN KEY (cpf) REFERENCES mensalista(cpf)
);

CREATE TABLE IF NOT EXISTS entrada_saida (
    descricao_veiculo  VARCHAR(40),
    placa VARCHAR(10) NOT NULL,
	mensalista BOOLEAN,
    data_hora_entrada DATETIME,
	data_hora_saida DATETIME,
	tempo_permanencia INTEGER,
	preco_total DECIMAL(6,2)
);


CREATE TABLE IF NOT EXISTS configuracao (
    duracao_bloco  INT NOT NULL,
    preco_bloco DECIMAL(6,2) NOT NULL,
    desconto_mensalista DECIMAL(2,2) NOT NULL,
    qtd_vagas INT NOT NULL,
    minimo_horas INT NOT NULL
);


DESCRIBE mensalista;
DESCRIBE veiculos_mensalista;
DESCRIBE pagamento_mensalista;
DESCRIBE entrada_saida;
DESCRIBE configuracao;