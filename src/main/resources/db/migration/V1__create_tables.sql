CREATE TABLE usuarios (
                          id BIGSERIAL PRIMARY KEY,
                          nome VARCHAR(100) NOT NULL,
                          email VARCHAR(100) NOT NULL UNIQUE,
                          senha VARCHAR(255) NOT NULL
);

CREATE TABLE cursos (
                        id BIGSERIAL PRIMARY KEY,
                        nome VARCHAR(100) NOT NULL,
                        categoria VARCHAR(100) NOT NULL
);

CREATE TABLE topicos (
                         id BIGSERIAL PRIMARY KEY,
                         titulo VARCHAR(200) NOT NULL,
                         mensagem TEXT NOT NULL,
                         data_criacao TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
                         status VARCHAR(50) NOT NULL,
                         autor_id BIGINT NOT NULL,
                         curso_id BIGINT NOT NULL,

                         CONSTRAINT fk_topico_autor
                             FOREIGN KEY (autor_id)
                                 REFERENCES usuarios(id),

                         CONSTRAINT fk_topico_curso
                             FOREIGN KEY (curso_id)
                                 REFERENCES cursos(id)
);

CREATE TABLE respostas (
                           id BIGSERIAL PRIMARY KEY,
                           mensagem TEXT NOT NULL,
                           data_criacao TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
                           autor_id BIGINT NOT NULL,
                           topico_id BIGINT NOT NULL,
                           solucao BOOLEAN DEFAULT FALSE,

                           CONSTRAINT fk_resposta_autor
                               FOREIGN KEY (autor_id)
                                   REFERENCES usuarios(id),

                           CONSTRAINT fk_resposta_topico
                               FOREIGN KEY (topico_id)
                                   REFERENCES topicos(id)
);