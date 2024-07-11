create table tb_topicos(

    id bigint not null unique auto_increment,
    uuid varchar(50) not null unique,

    titulo varchar(255) not null unique,
    mensagem mediumtext not null,
    data_criacao timestamp default current_timestamp,
    status varchar(100) not null,

    ativo tinyint not null default 1,

    autor_id bigint not null,
    curso_id bigint not null,

    created_at timestamp default current_timestamp,
    updated_at timestamp default current_timestamp on update current_timestamp,

    primary key(id),

    key `idx_uuid` (`uuid`),
    key `idx_status` (`status`),

    key `idx_autor_id` (`autor_id`),
    key `idx_curso_id` (`curso_id`),

    constraint `idx_autor` foreign key (`autor_id`) references `tb_usuarios` (`id`),
    constraint `idx_curso` foreign key (`curso_id`) references `tb_cursos` (`id`)
);