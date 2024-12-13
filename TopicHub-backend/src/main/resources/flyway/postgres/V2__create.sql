create sequence "articlePart_article_seq"
    as integer;

create table if not exists hub
(
    name varchar,
    id   serial
    primary key
);

create table if not exists author
(
    id       uuid    not null
    constraint user_pkey
    primary key,
    login    varchar not null
    constraint login_uniq
    unique,
    email    varchar not null
    constraint email_uniq
    unique,
    password varchar not null
);

create table if not exists article
(
    id        serial
    primary key,
    theme     varchar not null,
    likes     bigint  not null,
    dislikes  bigint  not null,
    keywords  varchar,
    created   timestamp,
    hub       serial
    constraint hub_fk
    references hub,
    status    varchar,
    author_id uuid
    constraint author_fk
    references author
    on update cascade on delete cascade
);

create table if not exists articlepart
(
    value   varchar                                                        not null,
    article integer default nextval('"articlePart_article_seq"'::regclass) not null
    constraint articlepart_article_id_fk
    references article
    on update cascade on delete cascade,
    type    varchar                                                        not null,
    name    varchar,
    id      integer,
    uuid    uuid                                                           not null
    primary key
    );

alter sequence "articlePart_article_seq" owned by articlepart.article;

create table if not exists role
(
    id   integer not null
    constraint "Role_pkey"
    primary key,
    name varchar not null
);

create table if not exists user_role
(
    id     uuid    not null
    primary key,
    userid uuid    not null
    constraint user_fk
    references author
    on update cascade on delete cascade,
    role   integer not null
    constraint role_fk
    references role
);

create table if not exists likes
(
    id         uuid    not null
    primary key,
    user_id    uuid    not null,
    article_id serial,
    state      integer not null
);

create table if not exists sessions
(
    id        uuid not null
    primary key,
    user_id   uuid not null
    constraint user_fk
    references author
    on update cascade on delete cascade,
    expiresat timestamp
);

create table if not exists subscription
(
    id       uuid not null
    constraint subscription_pk
    primary key,
    author   uuid
    constraint subscription_author_id_fk
    references author
    on update cascade on delete cascade,
    follower uuid
    constraint subscription_author_id_fk2
    references author
    on update cascade on delete cascade,
    constraint unique_field1_field2
    unique (author, follower)
    );

create table if not exists comment
(
    id             uuid      not null
    constraint comment_pk
    primary key,
    message        varchar   not null,
    parent_comment uuid
    constraint parent___fk
    references comment,
    article        integer
    constraint article_fk
    references article
    on update cascade on delete cascade,
    author         uuid
    constraint comment_author_id_fk
    references author
    on update cascade on delete cascade,
    created        timestamp not null
);

create table if not exists bookmark
(
    id      uuid    not null
    constraint bookmarks_pk
    primary key,
    article integer not null
    constraint bookmarks_article_id_fk
    references article
    on update cascade on delete cascade,
    author  uuid    not null
    constraint bookmarks_author_id_fk
    references author
    on update cascade on delete cascade,
    constraint bookmark_pk
    unique (author, article)
    );

create table if not exists image
(
    id     uuid not null
    constraint image_pk
    primary key,
    author uuid not null
    constraint image_pk2
    unique
    constraint image_author_id_fk
    references author,
    img    oid
);

