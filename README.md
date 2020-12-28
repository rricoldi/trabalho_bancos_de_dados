# Trabalho de Bancos De Dados


Como trabalho da disciplina de bancos de dados, será realizado o desenvolvimento de um software.


## 🛠 Ferramentas
<p align="left">
 <img src="https://agoncal.files.wordpress.com/2014/05/java_ee_logo_vert_v2.png" width="80">
 <img src="https://upload.wikimedia.org/wikipedia/commons/2/29/Postgresql_elephant.svg" width="80"> 
 <img src="https://upload.wikimedia.org/wikipedia/commons/thumb/4/43/Feed-icon.svg/1200px-Feed-icon.svg.png" width="80">
 <img src="https://cdn-images-1.medium.com/max/512/1*6kK9j74vyOmXYm1gN6ARhQ.png" width="80">
 <img src="https://spring.io/images/spring-logo-9146a4d3298760c2e7e49595184e1975.svg" height="80">
</p>

## 💡 Ideia
A ideia do projeto consiste em um agregador de podcasts, que dará suporte pra cadastro de usuários e de podcasts, através do feed rss, permitindo que os usuários ouçam o podcast, comentar e classificar o podcast. Os usuários também podem pesquisar por podcasts. Há também os gráficos e relatórios com dados do público de cada podcast.

## 🧰 Funcionalidades
- CRUD do podcast, onde teremos as informações: RSS Feed, nome, site, e-mail de contato, sendo o feed único e o e-mail opcional.
- CRUD de episódio, apenas quando há alguma relação com ele, ou seja, quando alguém curte ele.
- CRUD de usuários, onde teremos as informações: Nome, idade, email, senha, país, sexo.
- Gráfico do podcast, onde irá conter classificação média de cada podcast, episódios curtidos, faixa etária, sexo e país de usuários tanto geral, quanto pra cada inscrito em podcasts.
- Feed com os podcasts os quais o usuário está inscrito e todos os podcasts.
- Busca em podcasts através de palavras-chave.

## 📃 Descrição
O usuário quando entra no site tem algumas opções:

- Login: o usuário será redirecionado para uma tela onde irá inserir e-mail e senha, o backend irá chamar uma função que vai verificar se a senha corresponde à hash gravada no banco, e caso corresponda o usuário irá retornar ao início da aplicação como usuário logado.
- Cadastro de Usuário: o usuário ira para a tela onde terá que informar nome, idade, email, senha, país e sexo, sendo que o e-mail deve ser único, o backend irá pegar essas informações e verificar se há algum e-mail igual, se não houver, ele irá criptografar a senha e salvar as informações no banco de dados.
- Feed de Podcasts: todos os podcasts cadastrados estão no feed, ao clicar em algum e ele irá redirecionar o usuário para a página de podcast. Também há um campo de busca, no qual você pode digitar o nome de um podcast e ele irá aparecer para você.
Caso o usuário faça login algumas coisas mudam na tela inicial:

- Cadastro de Podcast: o usuário vai para a tela onde informa nome, feed rss, site e e-mail de contato do podcast, sendo o feed rss único e o e-mail opcional. O backend verifica se o feed já existe, se não existir ele salva no banco. Assim, o usuário é retornado ao início, com o podcast já adicionado no feed.
- Feed de inscrições: todos os podcasts nos quais o usuário está inscrito aparecem aqui, ao clicar em algum e ele irá redirecionar o usuário para a página de podcast. 
- Sair: o usuário é deslogado.
A tela de podcast possui as seguintes informações e opções:

- Imagem, Nome e Descrição do podcast, informações colhidas do feed rss.
- Todos os episódios, cada um com sua imagem, nome e botão para tocar.
Caso o usuário faça login algumas coisas mudam na tela de podcast:

- Avaliação média, número de comentários, site, email, feed, todas informações retornadas do servidor, que consulta o banco atrás de todas as informações e as retorna em apenas um JSON para o front.
- Botão de inscrição, caso você seja inscrito naquele podcast ele permite que você avalie o podcast, modificando a avaliação média.
- Número de curtidas dos episódios, podendo curtir cada episódio. Quando curtido, caso seja a primeira curtida, o backend irá criar uma instância do episódio no banco de dados.
- Caso clique no número de comentários, aparece todos os comentários daquele podcast, além de permitir que você comente nele.
- Também há o botão de gerar gráficos, que no futuro irá gerar gráficos mostrando informações daquele podcast.


## 📖 Diagrama E-R
<img src="https://i.imgur.com/rgFnvJ4.png" width="800">

## 📑 Modelo Relacional
<img src="https://i.imgur.com/Vsy3j8W.jpg" width="800">
