create table tb_respostas(

    id bigint not null unique auto_increment,
    uuid varchar(50) not null unique,

    mensagem mediumtext not null,
    data_criacao timestamp default current_timestamp,
    solucao tinyint not null default 0,

    ativo tinyint not null default 1,

    autor_id bigint not null,
    topico_id bigint not null,

    created_at timestamp default current_timestamp,
    updated_at timestamp default current_timestamp on update current_timestamp,

    primary key(id),

    key `idx_uuid` (`uuid`),

    key `idx_autor_id` (`autor_id`),
    key `idx_topico_id` (`topico_id`),

    constraint `idx_tb_respostas_autor` foreign key (`autor_id`) references `tb_usuarios` (`id`),
    constraint `idx_tb_respostas_topico` foreign key (`topico_id`) references `tb_topicos` (`id`)
);