--drop schema trabalho cascade;

create schema trabalho;

create table trabalho.podcast(
    id varchar(40),
	rss_feed varchar(200) not null unique,
    nome varchar(100) not null,
    site varchar(200) not null,
  	email varchar(100),
    --vizualizacoes integer default 0,
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

create table trabalho.log_acesso (
	id varchar(40),
	pod_id varchar(40),
	usr_id varchar(40),
	constraint pk_log_acesso PRIMARY KEY(id),
	constraint fk_log_acesso_pod foreign key(pod_id)
		references trabalho.podcast(id)
        on update cascade on delete cascade,
	constraint fk_log_acesso_usr foreign key(usr_id)
		references trabalho.usuario(id)
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

/*
INSERT INTO trabalho.log_acesso VALUES ('log_000', 'af46a958-b921-47b6-9f87-5c4fdc68ea79', 'usr_0');
INSERT INTO trabalho.log_acesso VALUES ('log_001', 'af46a958-b921-47b6-9f87-5c4fdc68ea79', 'usr_0');
INSERT INTO trabalho.log_acesso VALUES ('log_002', 'af46a958-b921-47b6-9f87-5c4fdc68ea79', 'usr_0');
INSERT INTO trabalho.log_acesso VALUES ('log_003', 'af46a958-b921-47b6-9f87-5c4fdc68ea79', 'usr_3');
INSERT INTO trabalho.log_acesso VALUES ('log_004', '67074e68-bc2f-442b-8af2-7d328b6af6eb', 'usr_3');
*/

--Selecionar podcast mais acessado:
CREATE OR REPLACE FUNCTION trabalho.getMostViewedPodcasts() RETURNS VARCHAR AS $$
DECLARE
	f RECORD;
	rtn VARCHAR;
BEGIN
	rtn = '{"acessos": [';
	FOR f IN SELECT la.pod_id pod_id, p.nome pod_nome, COUNT(*) nAcessos FROM trabalho.log_acesso la
	JOIN trabalho.podcast p ON la.pod_id = p.id
	GROUP BY pod_id, pod_nome
	ORDER BY nAcessos DESC
	LOOP
		rtn = rtn || 
			'{
				"pod_id": "'|| f.pod_id ||'",
				"pod_nome": "'|| f.pod_nome ||'",
				"nAcessos": '|| f.nAcessos ||'
			},';
	END LOOP;
	
	PERFORM substr(rtn, 1, length(rtn) - 2);
	
	rtn = LEFT(rtn, -1);
	
	rtn = rtn || ']}';
    
	RETURN rtn::jsonb;
END
$$ LANGUAGE PLPGSQL;

-- Selecionar a media de idade de cada podcast:
CREATE OR REPLACE FUNCTION trabalho.getMediaIdadeByPodcast() RETURNS VARCHAR AS $$
DECLARE
	f RECORD;
	rtn VARCHAR;
BEGIN
	rtn = '{"medias": [';
	FOR f IN SELECT la.pod_id pod_id, p.nome pod_nome, AVG(usr.idade) avg_idade FROM trabalho.usuario usr
		JOIN (SELECT DISTINCT usr_id, pod_id FROM trabalho.log_acesso) la ON la.usr_id = usr.id
		JOIN trabalho.podcast p ON la.pod_id = p.id
		GROUP BY la.pod_id, p.nome
	LOOP
		rtn = rtn || 
			'{
				"pod_id": "'|| f.pod_id ||'",
				"pod_nome": "'|| f.pod_nome ||'",
				"avg_idade": '|| f.avg_idade ||'
			},';
	END LOOP;
	
	PERFORM substr(rtn, 1, length(rtn) - 2);
	
	rtn = LEFT(rtn, -1);
	
	rtn = rtn || ']}';
    
	RETURN rtn::jsonb;
END
$$ LANGUAGE PLPGSQL;

/*
INSERT INTO trabalho.tags_podcast VALUES ('Legal', 'af46a958-b921-47b6-9f87-5c4fdc68ea79');
INSERT INTO trabalho.tags_podcast VALUES ('Legal', '168c1536-4c42-4b8f-8c77-f06affefec6a');
INSERT INTO trabalho.tags_podcast VALUES ('Legal', 'f1b92ce7-47a7-43cd-8586-4a09185cdd05');
INSERT INTO trabalho.tags_podcast VALUES ('Coding', 'af46a958-b921-47b6-9f87-5c4fdc68ea79');
INSERT INTO trabalho.tags_podcast VALUES ('Coding', '67074e68-bc2f-442b-8af2-7d328b6af6eb');
INSERT INTO trabalho.tags_podcast VALUES ('Dia-a-Dia', '67074e68-bc2f-442b-8af2-7d328b6af6eb');
INSERT INTO trabalho.tags_podcast VALUES ('Economia', 'af46a958-b921-47b6-9f87-5c4fdc68ea79');
*/

-- Selecionar tags mais acessadas
CREATE OR REPLACE FUNCTION trabalho.getMostViewedTags() RETURNS VARCHAR AS $$
DECLARE
	f RECORD;
	rtn VARCHAR;
BEGIN
	rtn = '{"acessos": [';
	FOR f IN SELECT ta.tag, COUNT(*) nAcessos FROM trabalho.log_acesso la
		JOIN trabalho.tags_podcast ta ON la.pod_id = ta.pod_id
		GROUP BY ta.tag
		ORDER BY nAcessos DESC
	LOOP
		rtn = rtn || 
			'{
				"pod_id": "'|| f.tag ||'",
				"nAcessos": '|| f.nAcessos ||'
			},';
	END LOOP;
	
	PERFORM substr(rtn, 1, length(rtn) - 2);
	
	rtn = LEFT(rtn, -1);
	
	rtn = rtn || ']}';
    
	RETURN rtn::jsonb;
END
$$ LANGUAGE PLPGSQL;

-- Selecionar media de idades de cada tag
CREATE OR REPLACE FUNCTION trabalho.getMediaIdadeByTag() RETURNS VARCHAR AS $$
DECLARE
	f RECORD;
	rtn VARCHAR;
BEGIN
	rtn = '{"medias": [';
	FOR f IN 
		SELECT subq.tag tag, AVG(subq.idade) avg_idade FROM (
			SELECT ta.tag, usr.nome, MAX(usr.idade) idade FROM trabalho.usuario usr
				JOIN (SELECT DISTINCT usr_id, pod_id FROM trabalho.log_acesso) la ON la.usr_id = usr.id
				JOIN trabalho.tags_podcast ta ON la.pod_id = ta.pod_id
				GROUP BY ta.tag, usr.nome
		) subq
		GROUP BY subq.tag
	LOOP
		rtn = rtn || 
			'{
				"tag": "'|| f.tag ||'",
				"avg_idade": '|| f.avg_idade ||'
			},';
	END LOOP;
	
	PERFORM substr(rtn, 1, length(rtn) - 2);
	
	rtn = LEFT(rtn, -1);
	
	rtn = rtn || ']}';
    
	RETURN rtn::jsonb;
END
$$ LANGUAGE PLPGSQL;








