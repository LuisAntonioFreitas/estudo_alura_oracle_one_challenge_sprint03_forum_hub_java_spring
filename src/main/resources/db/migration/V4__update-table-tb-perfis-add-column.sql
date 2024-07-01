alter table tb_perfis add column ativo tinyint NOT NULL DEFAULT 1;
update tb_perfis set ativo = 1;