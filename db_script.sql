create schema trabalho;

create table trabalho.podcast(
    id varchar(40),
	rss_feed varchar(200) not null unique,
    nome varchar(100) not null,
    site varchar(200) not null,
  	email varchar(100),
    constraint pk_podcast PRIMARY KEY(id)
);

create table trabalho.tags_podcast(
	tag varchar(30),
    pod_id varchar(40),
    constraint pk_tags_podcast PRIMARY KEY(tag, pod_id),
    constraint fk_podcast foreign key(pod_id)
		references trabalho.podcast(id)
        on update cascade on delete cascade
);

create table trabalho.usuario(
    id varchar(40),
	email varchar(100) not null unique,
    nome varchar(100) not null,
    sexo varchar(100) not null,
    idade int not null,
    senha varchar(32) not null,
    pais varchar(50) not null,
    constraint pk_usuario PRIMARY KEY(id)
);

create table trabalho.usuario_esta_inscrito_no_podcast(
	classificacao int not null,
    pod_id varchar(40) unique,
    usr_id varchar(40) unique,
    constraint pk_usuario_podcast PRIMARY KEY(usr_id, pod_id),
    constraint fk_inscrito_podcast foreign key(pod_id)
		references trabalho.podcast(id)
        on update cascade on delete cascade,
	constraint fk_usuario_inscrito foreign key(usr_id)
		references trabalho.usuario(id)
        on update cascade on delete cascade
);

create table trabalho.comentarios_podcast_usuario(
    id varchar(40),
	comentario text not null,
    pod_id varchar(40),
    usr_id varchar(40),
    constraint pk_comentario_usuario_podcast PRIMARY KEY(id),
    constraint fk_comentario_podcast foreign key(pod_id)
		references trabalho.usuario_esta_inscrito_no_podcast(pod_id)
        on update cascade on delete cascade,
	constraint fk_comentario_usuario foreign key(usr_id)
		references trabalho.usuario_esta_inscrito_no_podcast(usr_id)
        on update cascade on delete cascade
);

create table trabalho.episodio(
    id varchar(40),
    pod_id varchar(40),
    curtidas int default 0,
    constraint pk_episodio PRIMARY KEY(id, pod_id),
    constraint fk_episodio_podcast foreign key(pod_id)
		references trabalho.podcast(id)
        on update cascade on delete cascade
);

create table trabalho.usuario_classifica_episodio(
    ep_id varchar(40),
    pod_id varchar(40),
    usr_id varchar(40),
    constraint pk_usuario_classifica_episodio PRIMARY KEY(pod_id, usr_id, ep_id),
    constraint fk_usuario_classifica_podcast foreign key(pod_id)
		references trabalho.podcast(id)
        on update cascade on delete cascade,
    constraint fk_usuario_classifica_usuario foreign key(usr_id)
		references trabalho.usuario(id)
        on update cascade on delete cascade,
    constraint fk_usuario_classifica_episodio foreign key(ep_id, pod_id)
		references trabalho.episodio(id, pod_id)
        on update cascade on delete cascade
);

create table trabalho.usuario_comenta_episodio(
    id varchar(40),
    ep_id varchar(40),
    pod_id varchar(40),
    usr_id varchar(40),
    comentario varchar(240),
    constraint pk_usuario_comenta_episodio PRIMARY KEY(id),
    constraint fk_usuario_comenta_podcast foreign key(pod_id)
		references trabalho.podcast(id)
        on update cascade on delete cascade,
    constraint fk_usuario_comenta_usuario foreign key(usr_id)
		references trabalho.usuario(id)
        on update cascade on delete cascade,
    constraint fk_usuario_comenta_episodio foreign key(ep_id, pod_id)
		references trabalho.episodio(id, pod_id)
        on update cascade on delete cascade
);

--drop schema trabalho cascade;