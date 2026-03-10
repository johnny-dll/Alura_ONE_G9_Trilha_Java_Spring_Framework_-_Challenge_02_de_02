-- V4__ajustar_colunas_topico_edicoes.sql

-- Ajusta colunas para tipos corretos
ALTER TABLE topico_edicoes
    ALTER COLUMN titulo_antigo SET NOT NULL,
ALTER COLUMN mensagem_antiga SET NOT NULL;

-- Altera autor_antigo e curso_antigo para BIGINT
ALTER TABLE topico_edicoes
ALTER COLUMN autor_antigo TYPE BIGINT USING autor_antigo::BIGINT,
    ALTER COLUMN curso_antigo TYPE BIGINT USING curso_antigo::BIGINT;

-- Cria foreign keys para autor_antigo e curso_antigo
ALTER TABLE topico_edicoes
    ADD CONSTRAINT fk_topico_edicao_autor FOREIGN KEY (autor_antigo) REFERENCES usuarios(id);

ALTER TABLE topico_edicoes
    ADD CONSTRAINT fk_topico_edicao_curso FOREIGN KEY (curso_antigo) REFERENCES cursos(id);