CREATE TABLE topico_edicoes (
                                id BIGSERIAL PRIMARY KEY,
                                topico_id BIGINT NOT NULL,
                                titulo_antigo VARCHAR(255),
                                mensagem_antiga TEXT,
                                autor_antigo VARCHAR(255),
                                curso_antigo VARCHAR(255),
                                data_edicao TIMESTAMP NOT NULL,

                                CONSTRAINT fk_topico
                                    FOREIGN KEY (topico_id)
                                        REFERENCES topicos(id)
);