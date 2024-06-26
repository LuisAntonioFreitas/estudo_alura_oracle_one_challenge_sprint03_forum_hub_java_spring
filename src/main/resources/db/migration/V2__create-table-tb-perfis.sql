create table tb_perfis(

    id bigint not null unique auto_increment,
    uuid varchar(50) not null unique,

    nome varchar(255) not null unique,

    created_at timestamp default current_timestamp,
    updated_at timestamp default current_timestamp on update current_timestamp,

    primary key(id),

    key `idx_uuid` (`uuid`)
);