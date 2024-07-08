insert into tb_perfis (uuid, nome)
values ('7dace711-2811-4c61-a9d8-a25783289946', 'Administrador');

insert into tb_usuarios (uuid, nome, login, email, senha, perfil_id)
values ('9f802c9e-0d7c-4ca7-9f29-bbd02a7fc14a',
        'admin', 'admin', 'admin@admin.com',
        '$2a$10$sCWOwebSFu3gdDyDmqORJebURvWU6Ry4E9WVyOC4IXKAh/YdqIVji', 1);