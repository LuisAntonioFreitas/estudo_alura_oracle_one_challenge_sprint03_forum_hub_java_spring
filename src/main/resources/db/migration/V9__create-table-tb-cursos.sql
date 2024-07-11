create table tb_cursos(

    id bigint not null unique auto_increment,
    uuid varchar(50) not null unique,

    nome varchar(255) not null unique,
    categoria varchar(100) not null,

    ativo tinyint not null default 1,

    created_at timestamp default current_timestamp,
    updated_at timestamp default current_timestamp on update current_timestamp,

    primary key(id),

    key `idx_uuid` (`uuid`),
    key `idx_categoria` (`categoria`)
);