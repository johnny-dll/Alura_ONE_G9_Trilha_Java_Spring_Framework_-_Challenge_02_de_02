-- V5__add_soft_delete_to_topicos_and_edicoes.sql
-- Adiciona soft delete em TopicoEdicao e ativo em Topico

-- ================================
-- 1️⃣ Alterar tabela topico_edicoes
-- ================================
ALTER TABLE topico_edicoes
    ADD COLUMN soft_delete BOOLEAN NOT NULL DEFAULT FALSE;

-- ================================
-- 2️⃣ Alterar tabela topicos
-- ================================
ALTER TABLE topicos
    ADD COLUMN ativo BOOLEAN NOT NULL DEFAULT TRUE;