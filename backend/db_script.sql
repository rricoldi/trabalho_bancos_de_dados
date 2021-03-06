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
    pod_id varchar(40),
    usr_id varchar(40),
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
		references trabalho.podcast(id)
        on update cascade on delete cascade,
	constraint fk_comentario_usuario foreign key(usr_id)
		references trabalho.usuario(id)
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
    comentario varchar(240) NOT NULL,
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

CREATE OR REPLACE FUNCTION trabalho.geraRelatorioPodcast(_pod_id VARCHAR) RETURNS VARCHAR AS $$
DECLARE
	rtn JSON;
	inscritosTotal INTEGER;
	inscritosMale INTEGER;
	inscritosFemale INTEGER;
	inscritosOther INTEGER;
BEGIN
	inscritosTotal = (SELECT COALESCE(COUNT(*),0) FROM trabalho.usuario_esta_inscrito_no_podcast WHERE pod_id = _pod_id);
	inscritosMale = (
		SELECT COALESCE(COUNT(*),0) FROM trabalho.usuario_esta_inscrito_no_podcast uip
		JOIN trabalho.usuario usr ON uip.usr_id = usr.id
		WHERE uip.pod_id = _pod_id AND usr.sexo = 'male'
	);
	
	rtn = ('{
		   "relatorio": {
		   		"inscritos":' || inscritosTotal || ',
		   		"inscritosMale":' || inscritosMale || '
			}}'
	)::jsonb;
	
	RETURN rtn;
END
$$ LANGUAGE PLPGSQL;

/*CREATE OR REPLACE FUNCTION trabalho.hashPassword(_senha VARCHAR) RETURNS VARCHAR AS $$
BEGIN
	RETURN md5('B3G_3NCR!PT@T!0N_' || _senha || '_3ND_3NCR!PT@T!0N');
END
$$ LANGUAGE PLPGSQL;*/

CREATE OR REPLACE FUNCTION trabalho.hashPassword(_senha VARCHAR) RETURNS VARCHAR AS $$
DECLARE
n INTEGER = 1;
i INTEGER = 1;
hash VARCHAR = '';
symbols VARCHAR = '!@#$%&*_+-/';
BEGIN
	LOOP
		EXIT WHEN n = (length(_senha)+1);
		
		hash = hash || substring(_senha, n, 1) || substring(symbols, i, 1);
		
		n = n + 1;
		
		i = i + 1;
		IF i > length(symbols) THEN
			i = 1;
		END IF;
	END LOOP;
	
    hash = 'B3G_3NCR!PT@T!0N_' || hash || '_3ND_3NCR!PT@T!0N';
	RAISE NOTICE '%', hash;	
    
	RETURN md5(hash);
END
$$ LANGUAGE PLPGSQL;

--drop schema trabalho cascade;