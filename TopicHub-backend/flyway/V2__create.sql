
create sequence article_id_seq
    as integer
    start with 1;

create sequence "articlePart_article_seq"
    as integer;

create sequence hub_id_seq
    as integer
    start with 1;

create sequence article_hub_seq
    as integer
    start with 1;

create table if not exists hub
(
    name varchar,
    id   integer default nextval('hub_id_seq'::regclass) not null
    constraint hub_pkey
    primary key
    );

alter sequence hub_id_seq owned by hub.id;

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
    password varchar not null,
    state    boolean
);

create table if not exists article
(
    id        integer default nextval('article_id_seq'::regclass) not null
    constraint article_pkey
    primary key,
    theme     varchar                                             not null,
    keywords  varchar,
    created   timestamp,
    hub       integer default nextval('article_hub_seq'::regclass)
    constraint hub_fk
    references hub
    on update set null on delete set null,
    status    varchar,
    author_id uuid
    constraint author_fk
    references author
    on update cascade on delete cascade
    );

alter sequence article_id_seq owned by article.id;

alter sequence article_hub_seq owned by article.hub;

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
    constraint articlepart_pkey
    primary key,
    created bigint
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
    constraint user_role_pkey
    primary key,
    userid uuid    not null
    constraint user_fk
    references author
    on update cascade on delete cascade,
    role   integer not null
    constraint role_fk
    references role
    on update cascade on delete cascade
);

create table if not exists likes
(
    id         uuid    not null
    constraint likes_pkey
    primary key,
    user_id    uuid    not null,
    article_id serial,
    state      integer not null
);

create table if not exists sessions
(
    id        uuid not null
    constraint sessions_pkey
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
    references author
    on update cascade on delete cascade,
    img    oid
);

create table if not exists complaint_article
(
    id      uuid    not null
    constraint complaint_article_pk
    primary key,
    title   varchar,
    body    varchar,
    author  uuid
    constraint author___fk
    references author
    on update cascade on delete cascade,
    article integer not null
    constraint article___fk
    references article
    on update cascade on delete cascade
);

create table if not exists complaint_comment
(
    id      uuid not null
    constraint complaint_comment_pk
    primary key,
    title   varchar,
    body    varchar,
    author  uuid
    constraint author___fk
    references author
    on update cascade on delete cascade,
    comment uuid not null
    constraint comment___fk
    references comment
);


insert into role (id, name)
values (1, 'ADMIN');
insert into role (id, name)
values (2, 'USER');
insert into author (id, login, email, password,state)
values ('a904e8b8-9da8-4535-b402-9be0b78b2981', 'admin','admin@mail.ru','j8H6ZRy0j0wQZ+GUNmagU6lMlh0Dr9C+CVtEXubJ+L4=',false);
insert into user_role (id, userid, role)
values ('5a34fa56-e294-4602-8e7f-24e7a7832c2c', 'a904e8b8-9da8-4535-b402-9be0b78b2981',2);
insert into user_role (id, userid, role) values ('c5086606-b949-4426-a340-2626f19f5ef2', 'a904e8b8-9da8-4535-b402-9be0b78b2981',1);
insert into hub (name)
values ('Программирование');
insert into hub (name)
values ('Наука');
insert into hub (name)
values ('Спорт');

insert into article (theme, keywords, created, hub, status, author_id)
values (
        'Влияние искусственного интеллекта на общество: возможности и вызовы',
        'ИИ|Умный город',
        '2024-12-19 11:36:45.321343',
        1,
        null,
        'a904e8b8-9da8-4535-b402-9be0b78b2981'
);

insert into articlepart (value, article, type, name, id, uuid)
values (
        'Введение',
    1,
    'chapter',
    'Заголовок',
    3,
    uuid_generate_v4()
    );

insert into articlepart (value, article, type, name, id, uuid)
values (
    'Краткий обзор развития искусственного интеллекта (ИИ). Основные области применения ИИ в современном мире.',
    1,
    'paragraph',
    'Aбзац',
    1,
    uuid_generate_v4()
    );

insert into articlepart (value, article, type, name, id, uuid)
values (
    'Основная часть:',
    1,
    'chapter',
    'Заголовок',
    3,
    uuid_generate_v4()
    );

insert into articlepart (value, article, type, name, id, uuid)
values (
    'Преимущества ИИ:',
    1,
    'chapter',
    'Заголовок',
    3,
    uuid_generate_v4()
    );
insert into articlepart (value, article, type, name, id, uuid)
values (
    'Повышение эффективности и продуктивности в различных секторах (медицина, транспорт, производство).
Новые возможности для научных исследований и технологических инноваций.
Улучшение качества жизни за счет умных технологий (умные дома, персональные ассистенты).',
    1,
    'paragraph',
    'Aбзац',
    1,
    uuid_generate_v4()
    );
insert into articlepart (value, article, type, name, id, uuid)
values (
    'https://content.timeweb.com/assets/2ee2c8e4-93ec-4e4e-a9c3-dc74e12e63c3?width=860&height=573',
    1,
    'img',
    'Изображение',
    4,
    uuid_generate_v4()
    );

insert into articlepart (value, article, type, name, id, uuid)
values (
    'Вызовы и риски:',
    1,
    'chapter',
    'Заголовок',
    3,
    uuid_generate_v4()
    );
insert into articlepart (value, article, type, name, id, uuid)
values (
    'Вопросы безопасности и конфиденциальности данных.
Потеря рабочих мест из-за автоматизации.
Этические и правовые аспекты использования ИИ.',
    1,
    'paragraph',
    'Aбзац',
    1,
    uuid_generate_v4()
    );
insert into articlepart (value, article, type, name, id, uuid)
values (
    'Социальное влияние:',
    1,
    'chapter',
    'Заголовок',
    3,
    uuid_generate_v4()
    );
insert into articlepart (value, article, type, name, id, uuid)
values (
    'Изменения в социальной структуре и образе жизни людей.
Влияние на образование и требования к профессиональным навыкам.
Вопросы неравенства и доступности технологий.',
    1,
    'paragraph',
    'Aбзац',
    1,
    uuid_generate_v4()
    );
insert into articlepart (value, article, type, name, id, uuid)
values (
    'Заключение:',
    1,
    'chapter',
    'Заголовок',
    3,
    uuid_generate_v4()
    );
insert into articlepart (value, article, type, name, id, uuid)
values (
    'Перспективы развития ИИ в будущем.
Необходимость балансировки между преимуществами и рисками использования ИИ.
Роль общества и правительства в формировании безопасного и справедливого использования ИИ.',
    1,
    'paragraph',
    'Aбзац',
    1,
    uuid_generate_v4()
    );