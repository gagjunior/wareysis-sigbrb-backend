CREATE TABLE IF NOT EXISTS TIPOS_PERFIL_USUARIO (
    ID           VARCHAR(10) PRIMARY KEY,
    NOME         VARCHAR(50) UNIQUE,
    DH_CRIACAO   TIMESTAMP DEFAULT NOW(),
    DH_ALTERACAO TIMESTAMP DEFAULT NOW()
);

COMMENT ON TABLE TIPOS_PERFIL_USUARIO IS 'REGISTRO DOS PERFIS DE USUÁRIOS CADASTRADOS NO SISTEMA';
COMMENT ON COLUMN TIPOS_PERFIL_USUARIO.ID IS 'IDENTIFICADOR ÚNICO';
COMMENT ON COLUMN TIPOS_PERFIL_USUARIO.NOME IS 'NOME ÚNICO PARA O PERFIL';
COMMENT ON COLUMN TIPOS_PERFIL_USUARIO.DH_CRIACAO IS 'DATA E HORA DA CRIAÇÃO';
COMMENT ON COLUMN TIPOS_PERFIL_USUARIO.DH_ALTERACAO IS 'DATA E HORA DA ULTIMA ALTERAÇÃO';

INSERT INTO TIPOS_PERFIL_USUARIO (ID, NOME)
VALUES ('ADM', 'ADMINISTRADOR');

INSERT INTO TIPOS_PERFIL_USUARIO (ID, NOME)
VALUES ('PROF', 'PROFISSIONAL');

INSERT INTO TIPOS_PERFIL_USUARIO (ID, NOME)
VALUES ('CLI', 'CLIENTE');

