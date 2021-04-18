const faker = require('faker/locale/pt_BR')
const fs = require('fs')

const pod_ids = [
    'af46a958-b921-47b6-9f87-5c4fdc68ea79',
    'fa3abe16-3645-4d22-a01d-893e6f20c408',
    'f8e13774-d631-4f34-93f0-2f5d55590265',
    'de2f0ddb-9903-436a-a5e2-54224254c818',
    '67074e68-bc2f-442b-8af2-7d328b6af6eb',
    'bc8dac25-793c-4a96-aafc-4ea7020f4f60',
    '219328a1-cec6-4e8c-b003-52a3ec9766bb',
    'f0166bd2-8e4d-4308-b5d7-0b3d627c5dc5',
    '168c1536-4c42-4b8f-8c77-f06affefec6a',
    '5bdf7377-8330-4f9f-8efd-e95301c122ce',
    'f1b92ce7-47a7-43cd-8586-4a09185cdd05',
    'b553d865-8fe2-47de-9a30-10e1dea3bd1d',
    'c58b280a-56d4-4ad8-8c85-730e4ea86361',
    '7e429e8e-7669-403d-bb78-3cd7f9f75afd',
]

let query = `
    INSERT INTO trabalho.podcast VALUES ('af46a958-b921-47b6-9f87-5c4fdc68ea79', 'https://jovemnerd.com.br/feed-nerdcast', 'NerdCast', 'https://jovemnerd.com.br', NULL);
    INSERT INTO trabalho.podcast VALUES ('fa3abe16-3645-4d22-a01d-893e6f20c408', 'https://anchor.fm/s/3ad82c84/podcast/rss', 'Diário de um Jovem Moderno', 'https://anchor.fm/diariodeumjovemmoderno', NULL);
    INSERT INTO trabalho.podcast VALUES ('f8e13774-d631-4f34-93f0-2f5d55590265', 'https://anchor.fm/s/c946c20/podcast/rss', 'Devs Cansados', 'https://www.devscansados.com.br/', 'contato@devscansados.com.br');
    INSERT INTO trabalho.podcast VALUES ('de2f0ddb-9903-436a-a5e2-54224254c818', 'https://www.spreaker.com/show/4863104/episodes/feed', 'Podpah', 'https://www.spreaker.com/show/podpah_1', NULL);
    INSERT INTO trabalho.podcast VALUES ('67074e68-bc2f-442b-8af2-7d328b6af6eb', 'https://anchor.fm/s/2a80a35c/podcast/rss', 'Quadro em Branco', 'https://anchor.fm/quadro-em-branco', 'podcastembranco@gmail.com');
    INSERT INTO trabalho.podcast VALUES ('bc8dac25-793c-4a96-aafc-4ea7020f4f60', 'https://www.omnycontent.com/d/playlist/aaea4e69-af51-495e-afc9-a9760146922b/75a86d39-9e0e-4e9a-b948-aae301805fe6/514362cf-31b4-4ed2-af40-aae301805ffd/podcast.rss', 'Office Ladies', 'https://officeladies.com/', 'officeladies@earwolf.com');
    INSERT INTO trabalho.podcast VALUES ('219328a1-cec6-4e8c-b003-52a3ec9766bb', 'https://rss-feed-flowpodcast-2eqj3fl3la-ue.a.run.app/feed/rss', 'Flow', 'https://flowpodcast.com.br/', NULL);
    INSERT INTO trabalho.podcast VALUES ('f0166bd2-8e4d-4308-b5d7-0b3d627c5dc5', 'https://anchor.fm/s/248804cc/podcast/rss', 'Estrada Sobrenatural', 'https://linktr.ee/Estradasobrenatural', 'comercial.estradasobrenatural@gmail.com');
    INSERT INTO trabalho.podcast VALUES ('168c1536-4c42-4b8f-8c77-f06affefec6a', 'https://anchor.fm/s/11c4550c/podcast/rss', 'Hoje no TecMundo Podcast', 'https://www.tecmundo.com.br/', NULL);
    INSERT INTO trabalho.podcast VALUES ('5bdf7377-8330-4f9f-8efd-e95301c122ce', 'https://feeds.simplecast.com/tL5DvHPD', '2Devs', 'https://2devs.simplecast.com/', NULL);
    INSERT INTO trabalho.podcast VALUES ('f1b92ce7-47a7-43cd-8586-4a09185cdd05', 'https://anchor.fm/s/3bf2f2c/podcast/rss', 'Podcast FalaDev', 'https://anchor.fm/faladev', NULL);
    INSERT INTO trabalho.podcast VALUES ('b553d865-8fe2-47de-9a30-10e1dea3bd1d', 'http://www.deviante.com.br/podcasts/scicast/feed/', 'Scicast', 'https://www.deviante.com.br/podcasts/scicast/', NULL);
    INSERT INTO trabalho.podcast VALUES ('c58b280a-56d4-4ad8-8c85-730e4ea86361', 'https://republicadomedo.com.br/feed/podcast', 'RdMCast', 'https://republicadomedo.com.br/', 'contato@republicadomedo.com.br');
    INSERT INTO trabalho.podcast VALUES ('7e429e8e-7669-403d-bb78-3cd7f9f75afd', 'https://hipsters.tech/feed/podcast', 'Hipsters ponto tech', 'https://hipsters.tech/', NULL);
`

const genders = ['male', 'female']

for(let i = 0; i < 600; i++) {
    const gender = genders[faker.datatype.number(1)]
    const name = faker.name.firstName(gender)
    const lastName = faker.name.lastName()
    query += `INSERT INTO trabalho.usuario VALUES ('usr_${i}', '${i}_${faker.internet.email(name, lastName)}', '${name} ${lastName}', '${gender}', ${faker.datatype.number(60)}, 'password', '${faker.address.country()}');\n`

    for(let j = 0; j < 3; j++) {
        const pod_id = pod_ids[faker.datatype.number(pod_ids.length-1)]
        query += `INSERT INTO trabalho.usuario_esta_inscrito_no_podcast VALUES (${faker.datatype.number(5)}, '${pod_id}', 'usr_${i}');\n`
        query += `INSERT INTO trabalho.comentarios_podcast_usuario VALUES ('comment_${i}_${j}', '${faker.lorem.sentence()}', '${pod_id}', 'usr_${i}');\n`
        query += `INSERT INTO trabalho.log_acesso VALUES ('log_${i}_${j}', '${pod_id}', 'usr_${i}');\n`
    }
}

query += `INSERT INTO trabalho.tags_podcast VALUES ('Comédia', 'af46a958-b921-47b6-9f87-5c4fdc68ea79');\n`
query += `INSERT INTO trabalho.tags_podcast VALUES ('Dia a Dia', 'fa3abe16-3645-4d22-a01d-893e6f20c408');\n`
query += `INSERT INTO trabalho.tags_podcast VALUES ('Prgramação', 'f8e13774-d631-4f34-93f0-2f5d55590265');\n`
query += `INSERT INTO trabalho.tags_podcast VALUES ('Comédia', 'de2f0ddb-9903-436a-a5e2-54224254c818');\n`
query += `INSERT INTO trabalho.tags_podcast VALUES ('Cinema', '67074e68-bc2f-442b-8af2-7d328b6af6eb');\n`
query += `INSERT INTO trabalho.tags_podcast VALUES ('Cinema', 'bc8dac25-793c-4a96-aafc-4ea7020f4f60');\n`
query += `INSERT INTO trabalho.tags_podcast VALUES ('Comédia', '219328a1-cec6-4e8c-b003-52a3ec9766bb');\n`
query += `INSERT INTO trabalho.tags_podcast VALUES ('Terror', 'f0166bd2-8e4d-4308-b5d7-0b3d627c5dc5');\n`
query += `INSERT INTO trabalho.tags_podcast VALUES ('Tecnologia', '168c1536-4c42-4b8f-8c77-f06affefec6a');\n`
query += `INSERT INTO trabalho.tags_podcast VALUES ('Programação', '5bdf7377-8330-4f9f-8efd-e95301c122ce');\n`
query += `INSERT INTO trabalho.tags_podcast VALUES ('Programação', 'f1b92ce7-47a7-43cd-8586-4a09185cdd05');\n`
query += `INSERT INTO trabalho.tags_podcast VALUES ('Educação', 'b553d865-8fe2-47de-9a30-10e1dea3bd1d');\n`
query += `INSERT INTO trabalho.tags_podcast VALUES ('Terror', 'c58b280a-56d4-4ad8-8c85-730e4ea86361');\n`
query += `INSERT INTO trabalho.tags_podcast VALUES ('Tecnologia', '7e429e8e-7669-403d-bb78-3cd7f9f75afd');\n`

fs.writeFile('query.sql', query, function (err,data) {
    if (err) {
      return console.log(err);
    }
});

console.log(query)

/*
-- INSCRICOES
INSERT INTO trabalho.usuario_esta_inscrito_no_podcast VALUES (3, 'podcast_001', 'usr_001');
INSERT INTO trabalho.usuario_esta_inscrito_no_podcast VALUES (4, 'podcast_002', 'usr_001');
INSERT INTO trabalho.usuario_esta_inscrito_no_podcast VALUES (7, 'podcast_001', 'usr_002');
INSERT INTO trabalho.usuario_esta_inscrito_no_podcast VALUES (2, 'podcast_001', 'usr_003');

-- COMENTARIOS EM PODCASTS
INSERT INTO trabalho.comentarios_podcast_usuario VALUES ('cmt_pod_001', 'I love this podcast bro', 'podcast_001', 'usr_003');
INSERT INTO trabalho.comentarios_podcast_usuario VALUES ('cmt_pod_002', 'Meio q n sei', 'podcast_001', 'usr_001');
INSERT INTO trabalho.comentarios_podcast_usuario VALUES ('cmt_pod_003', 'Gosto duns episodio ai', 'podcast_002', 'usr_004');
*/