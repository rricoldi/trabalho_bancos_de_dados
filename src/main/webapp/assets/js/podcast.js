function deletePodcast(e) {
    e.preventDefault();
    $('.link_confirmacao_excluir_podcast').attr('href', $(this).data('href'));
    $('.modal_excluir_podcast').modal();
}

function deletePodcasts(e) {
    e.preventDefault();
    $('.form_excluir_podcasts').submit();
}

function readPodcast(e) {
    e.preventDefault();
    $.get($(this).data('href'), function (data) {
        const podcast = JSON.parse(data);
        console.log(podcast);
        var $modal = $('.modal-visualizar-podcast');

        $modal.find(".p_rss_feed").html('<strong>Rss Feed: </strong>' + podcast.rss_feed);
        $modal.find(".p_nome").html('<strong>Nome: </strong>' + podcast.nome);
        $modal.find(".p_site").html('<strong>Site: </strong>' + podcast.site);
        
        $modal.modal();
    });
}


$(document).ready(function () {
    $(document).on('click', '.link_excluir_podcast', deletePodcast);
    $(document).on('click', '.button_confirmacao_excluir_podcasts', deletePodcasts);
    $(document).on('click', '.link_visualizar_podcast', readPodcast);    
    $("*[data-toggle='tooltip']").tooltip({
        'container': 'body'
    });
});