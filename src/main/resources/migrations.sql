-- liquibase formatted sql

-- changeset Timor:1
create table token (
    value  char(128) not null,
    userId int8 not null,
    primary key (value)
);

create table "user" (
    id       bigserial not null,
    email    varchar(320) not null,
    hashedPassword varchar(100) not null,
    username varchar(100) not null,
    primary key (id)
);

alter table token
    add constraint FKljiaxlt4bg9emxw74wog1awjl
        foreign key (userId)
            references "user";
-- rollback DROP TABLE token; DROP TABLE "user";