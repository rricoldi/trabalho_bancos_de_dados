<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib tagdir="/WEB-INF/tags/session" prefix="session"%>
<session:my_user context="${pageContext.servletContext.contextPath}"/>
<!DOCTYPE html>
<html>
    <head>
        <%@include file="/view/include/head.jsp"%>
        <title>Podcasts</title>
    </head>
    <body>

        <div class="container">
            
            <div class="text-center div_inserir_excluir">
                <a class="btn btn-lg btn-primary" href="${pageContext.servletContext.contextPath}/podcast/create">
                    Inserir novo podcast
                </a>

                <button class="btn btn-lg btn-warning" data-toggle="modal" data-target=".modal_excluir_podcasts">
                    Excluir múltiplos podcasts
                </button>
            </div>

            <form class="form_excluir_podcasts" action="${pageContext.servletContext.contextPath}/podcast/delete" method="POST">
                <table class="table table-striped">
                    <thead>
                        <tr>
                            <th class="col-lg-6 h4">Nome</th>
                            <th class="col-sm-5 h4 text-center">Ação</th>
                            <th class="col-sm-1 h4 text-center">Excluir?</th>
                        </tr>
                    </thead>
                    <tbody>
                        <c:forEach var="podcast" items="${requestScope.podcastList}">
                            <tr>
                                <td class="">
                                    <a class="link_visualizar_podcast" href="#" data-href="${pageContext.servletContext.contextPath}/podcast/read?rss_feed=${podcast.rss_feed}">
                                        <span class="h4"><c:out value="${podcast.nome}"/></span>
                                    </a>
                                </td>
                                <td class="col text-center">
                                    <div class="row text-center">
                                        <a class="btn btn-default"
                                            href="${pageContext.servletContext.contextPath}/podcast/update?rss_feed=${podcast.rss_feed}"
                                            data-toggle="tooltip"
                                            data-original-title="Editar">
                                             <i class="fa fa-pencil"></i>
                                        </a>
                                        <a class="btn btn-default link_excluir_podcast"
                                            href="#"
                                            data-href="${pageContext.servletContext.contextPath}/podcast/delete?rss_feed=${podcast.rss_feed}"
                                            data-toggle="tooltip"
                                            data-original-title="Excluir">
                                             <i class="fa fa-trash"></i>
                                        </a>
                                    </div>
                                </td>
                                <td class="text-center">
                                    <input class="checkbox-inline" type="checkbox" name="delete" value="${podcast.rss_feed}" />
                                </td>
                            </tr>
                        </c:forEach>
                    </tbody>
                </table>
            </form>
            <div class="modal fade modal_excluir_podcast">
                <div class="modal-dialog">
                    <div class="modal-content">
                        <div class="modal-header">
                            <h4 class="modal-title">Confirmação</h4>
                            <button class="close" type="button" data-dismiss="modal"><span>&times;</span></button>
                        </div>
                        <div class="modal-body">
                            <p>Tem certeza de que deseja excluir este podcast?</p>
                        </div>
                        <div class="modal-footer">
                            <a class="btn btn-danger link_confirmacao_excluir_podcast">Sim</a>
                            <button class="btn btn-primary" type="button" data-dismiss="modal">Não</button>
                        </div>
                    </div>
                </div>
            </div>            
        
            <div class="modal fade modal_excluir_podcasts">
                <div class="modal-dialog">
                    <div class="modal-content">
                        <div class="modal-header">
                            <h4 class="modal-title">Confirmação</h4>
                            <button class="close" type="button" data-dismiss="modal"><span>&times;</span></button>
                        </div>
                        <div class="modal-body">
                            <p>Tem certeza de que deseja excluir os podcasts selecionados?</p>
                        </div>
                        <div class="modal-footer">
                            <button class="btn btn-danger button_confirmacao_excluir_podcasts" type="button">Sim</button>
                            <button class="btn btn-primary" type="button" data-dismiss="modal">Não</button>
                        </div>
                    </div>
                </div>
            </div>
            <div class="modal modal-visualizar-podcast">
                <div class="modal-dialog">
                    <div class="modal-content">
                        <div class="modal-header">
                            <h4 class="modal-title">Detalhes</h4>
                            <button class="close" type="button" data-dismiss="modal"><span>&times;</span></button>
                        </div>
                        <div class="modal-body">
                            <div class="container-fluid">
                                <div class="row">
                                    <div class="col-md-8">
                                        <p class="p_rss_feed"></p>
                                        <p class="p_nome"></p>
                                        <p class="p_site"></p>                                       
                                    </div>
                                </div>
                            </div>

                        </div>
                        <div class="modal-footer">
                            <button class="btn btn-primary" type="button" data-dismiss="modal">Fechar</button>
                        </div>
                    </div>
                </div>
            </div>
        </div>

                                                     
        <%@include file="/view/include/scripts.jsp"%>
        <script src="${pageContext.servletContext.contextPath}/assets/js/podcast.js"></script>
    </body>
</html>