CREATE TABLE professor (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    nome VARCHAR(200) NOT NULL,
    salario NUMERIC(38,2) NOT NULL,
    status_professor VARCHAR(255) NOT NULL
);

INSERT INTO professor (nome, salario, status_professor) VALUES ('Batman', 15000.00, 'ATIVO');
INSERT INTO professor (nome, salario, status_professor) VALUES ('Coringa', 25000.00, 'INATIVO');
INSERT INTO professor (nome, salario, status_professor) VALUES ('Robin', 18000.00, 'APOSENTADO');
INSERT INTO professor (nome, salario, status_professor) VALUES ('Mulher Maravilha', 14000.00, 'AFASTADO');