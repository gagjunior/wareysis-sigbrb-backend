CREATE EXTENSION IF NOT EXISTS "pgcrypto";

CREATE TABLE IF NOT EXISTS USUARIOS (
    ID               UUID      DEFAULT GEN_RANDOM_UUID() PRIMARY KEY,
    EMAIL            VARCHAR(255) NOT NULL UNIQUE,
    NOME_COMPLETO    VARCHAR(300) NOT NULL,
    CPF              VARCHAR(20) UNIQUE,
    TELEFONE         VARCHAR(20),
    EMAIL_VERIFICADO BOOLEAN   DEFAULT FALSE,
    HABILITADO       BOOLEAN   DEFAULT TRUE,
    ALTERAR_SENHA    BOOLEAN   DEFAULT FALSE,
    DH_CRIACAO       TIMESTAMP DEFAULT NOW(),
    DH_ALTERACAO     TIMESTAMP DEFAULT NOW()
);

CREATE INDEX IDX_CLIENTES_NOME ON USUARIOS(NOME_COMPLETO);

COMMENT ON TABLE USUARIOS IS 'REGISTRO DOS USUÁRIOS CADASTRADOS NO SISTEMA';
COMMENT ON COLUMN USUARIOS.ID IS 'IDENTIFICADOR ÚNICO';
COMMENT ON COLUMN USUARIOS.EMAIL IS 'EMAIL DO USUÁRIO';
COMMENT ON COLUMN USUARIOS.NOME_COMPLETO IS 'NOME COMPLETO';
COMMENT ON COLUMN USUARIOS.CPF IS 'CPF DO CLIENTE';
COMMENT ON COLUMN USUARIOS.TELEFONE IS 'TELEFONE PRINCIPAL';
COMMENT ON COLUMN USUARIOS.EMAIL_VERIFICADO IS 'TRUE = EMAIL VERIFICADO, FALSE = NÃO VERIFICADO';
COMMENT ON COLUMN USUARIOS.HABILITADO IS 'TRUE = HABILITADO, FALSE = DESABILITADO';
COMMENT ON COLUMN USUARIOS.ALTERAR_SENHA IS 'TRUE = DEVE ALTERAR SENHA, FALSE = NÃO PRECISA ALTERAR';
COMMENT ON COLUMN USUARIOS.DH_CRIACAO IS 'DATA E HORA DA CRIAÇÃO';
COMMENT ON COLUMN USUARIOS.DH_ALTERACAO IS 'DATA E HORA DA ULTIMA ALTERAÇÃO';

