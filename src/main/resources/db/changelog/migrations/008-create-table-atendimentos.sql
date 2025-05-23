CREATE TABLE IF NOT EXISTS ATENDIMENTOS (
    ID        UUID PRIMARY KEY DEFAULT GEN_RANDOM_UUID(),
    ID_PROFISSIONAL       UUID         NOT NULL,
    ID_SERVICO            UUID         NOT NULL,
    DH_INICIO             TIMESTAMP    NOT NULL,
    DH_FIM                TIMESTAMP    NOT NULL,
    ID_CLIENTE            UUID,
    NOME_CLIENTE          VARCHAR(300) NOT NULL,
    ID_STATUS_ATENDIMENTO VARCHAR(4)       DEFAULT 'AG',
    ID_STATUS_PAGAMENTO   VARCHAR(4)       DEFAULT 'EA',
    ID_METODO_PAGAMENTO   VARCHAR(4)       DEFAULT 'DN',
    DH_CRIACAO            TIMESTAMP        DEFAULT NOW(),
    DH_ALTERACAO          TIMESTAMP        DEFAULT NOW(),

    -- Garante que um profissional não possa ter dois atendimentos no mesmo horário
    UNIQUE (ID_PROFISSIONAL, DH_INICIO)
);

COMMENT ON TABLE ATENDIMENTOS IS 'REGISTRO DOS ATENDIMENTOS CADASTRADOS NO SISTEMA';
COMMENT ON COLUMN ATENDIMENTOS.ID IS 'IDENTIFICADOR ÚNICO';
COMMENT ON COLUMN ATENDIMENTOS.ID_PROFISSIONAL IS 'ID DO PROFISSIONAL QUE IRÁ REALIZAR O ATENDIMENTO';
COMMENT ON COLUMN ATENDIMENTOS.ID_SERVICO IS 'ID DO SERVIÇO QUE SERÁ REALIZADO';
COMMENT ON COLUMN ATENDIMENTOS.DH_INICIO IS 'DATA E HORA DO INÍCIO DO ATENDIMENTO';
COMMENT ON COLUMN ATENDIMENTOS.DH_FIM IS 'DATA E HORA DO FINAL DO ATENDIMENTO';
COMMENT ON COLUMN ATENDIMENTOS.ID_CLIENTE IS 'ID DO CLIENTE SE CADASTRADO NO SISTEMA';
COMMENT ON COLUMN ATENDIMENTOS.NOME_CLIENTE IS 'NOME DO CLIENTE';
COMMENT ON COLUMN ATENDIMENTOS.ID_STATUS_ATENDIMENTO IS 'ID DO STATUS DO ATENDIMENTO';
COMMENT ON COLUMN ATENDIMENTOS.ID_STATUS_PAGAMENTO IS 'ID DO STATUS DO PAGAMENTO';
COMMENT ON COLUMN ATENDIMENTOS.ID_METODO_PAGAMENTO IS 'ID DO MÉTODO DO PAGAMENTO';
COMMENT ON COLUMN ATENDIMENTOS.DH_CRIACAO IS 'DATA E HORA DA CRIAÇÃO';
COMMENT ON COLUMN ATENDIMENTOS.DH_ALTERACAO IS 'DATA E HORA DA ÚLTIMA ALTERAÇÃO';
