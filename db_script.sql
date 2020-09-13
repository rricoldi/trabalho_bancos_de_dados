create schema trabalho;

create table trabalho.podcast(
	rss_feed char(200),
    nome char(100) not null,
    site char(200) not null,
    constraint pk_podcast PRIMARY KEY(rss_feed)
);

create table trabalho.tags_podcast(
	tag char(30),
    rss_feed char(200),
    constraint pk_tags_podcast PRIMARY KEY(tag, rss_feed),
    constraint fk_podcast foreign key(rss_feed)
		references trabalho.podcast(rss_feed)
        on update cascade on delete cascade
);

create table trabalho.usuario(
	email char(100),
    nome char(100) not null,
    sexo char(100) not null,
    idade int not null,
    senha char(30) not null,
    pais char(50) not null,
    constraint pk_usuario PRIMARY KEY(email)
);

create table trabalho.usuario_esta_inscrito_no_podcast(
	classificacao int not null,
    rss_feed char(200),
    email char(100),
    constraint pk_usuario_podcast PRIMARY KEY(email, rss_feed),
    constraint fk_inscrito_podcast foreign key(rss_feed)
		references trabalho.podcast(rss_feed)
        on update cascade on delete cascade,
	constraint fk_usuario foreign key(email)
		references trabalho.usuario(email)
        on update cascade on delete cascade
);

create table trabalho.comentarios_podcast_usuario(
	comentario text not null,
    rss_feed char(200),
    email char(100),
    constraint pk_usuario_podcast PRIMARY KEY(email, rss_feed),
    constraint fk_comentario_podcast foreign key(rss_feed)
		references trabalho.usuario_esta_inscrito_no_podcast(rss_feed)
        on update cascade on delete cascade,
	constraint fk_comentario_usuario foreign key(email)
		references trabalho.usuario_esta_inscrito_no_podcast(email)
        on update cascade on delete cascade
);

#drop schema trabalho;