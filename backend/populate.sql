-- USUARIOS
INSERT INTO trabalho.usuario VALUES ('usr_001', 'luckinha45@gmail.com', 'Lucas', 'male', 21, '123456', 'BR');
INSERT INTO trabalho.usuario VALUES ('usr_002', 'rricoldi@gmail.com', 'Renan', 'male', 21, '654321', 'BR');
INSERT INTO trabalho.usuario VALUES ('usr_003', 'WilliamMClewis@armyspy.com', 'William M. Clewis', 'other', 30, 'ieR5yah5', 'US');
INSERT INTO trabalho.usuario VALUES ('usr_004', 'SophiaCastroCorreia@hotmail.com', 'Sophia Castro', 'female', 25, 'Keih2voh', 'PT');

-- PODCASTS
INSERT INTO trabalho.podcast VALUES ('podcast_001', 'feed.nerdcast.com', 'NerdCast', 'www.nerdcast.com', 'nerdcast@gmail.com');
INSERT INTO trabalho.podcast VALUES ('podcast_002', 'feed.chadverb.com', 'ChadrezVerbal', 'www.chadrezverbal.com', 'cverbal@yahoo.com');
INSERT INTO trabalho.podcast VALUES ('podcast_003', 'feed.new.com', 'NewPodcast', 'www.new.com', 'new@yahoo.com');
INSERT INTO trabalho.podcast VALUES ('podcast_004', 'feed.noemail.com', 'NoEmail', 'www.noemail.com', NULL);

-- EPISODIOS
INSERT INTO trabalho.episodio VALUES ('epi_001', 'podcast_001', 3);
INSERT INTO trabalho.episodio VALUES ('epi_002', 'podcast_001', 1);
INSERT INTO trabalho.episodio VALUES ('epi_003', 'podcast_002', 5);
INSERT INTO trabalho.episodio VALUES ('epi_004', 'podcast_002', 0);
INSERT INTO trabalho.episodio VALUES ('epi_005', 'podcast_002', 1);

-- INSCRICOES
INSERT INTO trabalho.usuario_esta_inscrito_no_podcast VALUES (3, 'podcast_001', 'usr_001');
INSERT INTO trabalho.usuario_esta_inscrito_no_podcast VALUES (4, 'podcast_002', 'usr_001');
INSERT INTO trabalho.usuario_esta_inscrito_no_podcast VALUES (7, 'podcast_001', 'usr_002');
INSERT INTO trabalho.usuario_esta_inscrito_no_podcast VALUES (2, 'podcast_001', 'usr_003');

-- COMENTARIOS EM EPISODIOS
INSERT INTO trabalho.usuario_comenta_episodio VALUES ('cmt_epi_001', 'epi_001', 'podcast_001', 'usr_001', 'Meio + ou -');
INSERT INTO trabalho.usuario_comenta_episodio VALUES ('cmt_epi_002', 'epi_003', 'podcast_002', 'usr_002', 'Bao d+');
INSERT INTO trabalho.usuario_comenta_episodio VALUES ('cmt_epi_003', 'epi_005', 'podcast_002', 'usr_001', 'Gosto muito');
INSERT INTO trabalho.usuario_comenta_episodio VALUES ('cmt_epi_004', 'epi_001', 'podcast_001', 'usr_003', 'Very good episode bro!');

-- COMENTARIOS EM PODCASTS
INSERT INTO trabalho.comentarios_podcast_usuario VALUES ('cmt_pod_001', 'I love this podcast bro', 'podcast_001', 'usr_003');
INSERT INTO trabalho.comentarios_podcast_usuario VALUES ('cmt_pod_002', 'Meio q n sei', 'podcast_001', 'usr_001');
INSERT INTO trabalho.comentarios_podcast_usuario VALUES ('cmt_pod_003', 'Gosto duns episodio ai', 'podcast_002', 'usr_004');

-- USUARIO CURTIU EPISODIO
INSERT INTO trabalho.usuario_classifica_episodio VALUES ('epi_001', 'podcast_001', 'usr_001');
INSERT INTO trabalho.usuario_classifica_episodio VALUES ('epi_001', 'podcast_001', 'usr_002');
INSERT INTO trabalho.usuario_classifica_episodio VALUES ('epi_002', 'podcast_001', 'usr_001');
INSERT INTO trabalho.usuario_classifica_episodio VALUES ('epi_003', 'podcast_002', 'usr_004');