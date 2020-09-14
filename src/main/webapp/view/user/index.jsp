<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib tagdir="/WEB-INF/tags/session" prefix="session"%>
<!DOCTYPE html>
<html>
    <head>
        <%@include file="/view/include/head.jsp"%>
        <title>Usuários do Podcast</title>
    </head>
    <body>

        <div class="container">
            
            <div class="text-center div_inserir_excluir">
                <a class="btn btn-lg btn-primary" href="${pageContext.servletContext.contextPath}/user/create">
                    Inserir novo usuário
                </a>

                <button class="btn btn-lg btn-warning" data-toggle="modal" data-target=".modal_excluir_usuarios">
                    Excluir múltiplos usuários
                </button>
                <a class="btn btn-default"
                   href="${pageContext.servletContext.contextPath}/logout"
                   data-toggle="tooltip"
                   data-original-title="Logout">
                    <i class="fa fa-sign-out"></i>
                </a>
            </div>

            <form class="form_excluir_usuarios" action="${pageContext.servletContext.contextPath}/user/delete" method="POST">
                <table class="table table-striped">
                    <thead>
                        <tr>
                            <th class="h4">E-mail</th>
                            <th class="col-lg-6 h4">Nome</th>
                            <th class="col-sm-5 h4 text-center">Ação</th>
                            <th class="col-sm-1 h4 text-center">Excluir?</th>
                        </tr>
                    </thead>
                    <tbody>
                        <c:forEach var="usuario" items="${requestScope.usuarioList}">
                            <tr>
                                <td>
                                    <span class="h4"><c:out value="${usuario.email}"/></span>
                                </td>
                                <td class="row">
                                    <a class="link_visualizar_usuario" href="#" data-href="${pageContext.servletContext.contextPath}/user/read?email=${usuario.email}">
                                        <span class="h4"><c:out value="${usuario.nome}"/></span>
                                    </a>
                                </td>
                                <td class="col text-center">
                                    <div class="row text-center">
                                        <a class="btn btn-default"
                                            href="${pageContext.servletContext.contextPath}/user/update?email=${usuario.email}"
                                            data-toggle="tooltip"
                                            data-original-title="Editar">
                                             <i class="fa fa-pencil"></i>
                                        </a>
                                        <a class="btn btn-default link_excluir_usuario"
                                            href="#"
                                            data-href="${pageContext.servletContext.contextPath}/user/delete?email=${usuario.email}"
                                            data-toggle="tooltip"
                                            data-original-title="Excluir">
                                             <i class="fa fa-trash"></i>
                                        </a>
                                    </div>
                                </td>
                                <td class="text-center">
                                    <input class="checkbox-inline" type="checkbox" name="delete" value="${usuario.email}" />
                                </td>
                            </tr>
                        </c:forEach>
                    </tbody>
                </table>
            </form>
            <div class="modal fade modal_excluir_usuario">
                <div class="modal-dialog">
                    <div class="modal-content">
                        <div class="modal-header">
                            <h4 class="modal-title">Confirmação</h4>
                            <button class="close" type="button" data-dismiss="modal"><span>&times;</span></button>
                        </div>
                        <div class="modal-body">
                            <p>Tem certeza de que deseja excluir este usuário?</p>
                        </div>
                        <div class="modal-footer">
                            <a class="btn btn-danger link_confirmacao_excluir_usuario">Sim</a>
                            <button class="btn btn-primary" type="button" data-dismiss="modal">Não</button>
                        </div>
                    </div>
                </div>
            </div>            
        
            <div class="modal fade modal_excluir_usuarios">
                <div class="modal-dialog">
                    <div class="modal-content">
                        <div class="modal-header">
                            <h4 class="modal-title">Confirmação</h4>
                            <button class="close" type="button" data-dismiss="modal"><span>&times;</span></button>
                        </div>
                        <div class="modal-body">
                            <p>Tem certeza de que deseja excluir os usuários selecionados?</p>
                        </div>
                        <div class="modal-footer">
                            <button class="btn btn-danger button_confirmacao_excluir_usuarios" type="button">Sim</button>
                            <button class="btn btn-primary" type="button" data-dismiss="modal">Não</button>
                        </div>
                    </div>
                </div>
            </div>
            <div class="modal modal-visualizar-usuario">
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
                                        <p class="p_email"></p>
                                        <p class="p_nome"></p>
                                        <p class="p_idade"></p>
                                        <p class="p_sexo"></p>
                                        <p class="p_pais"></p>                                        
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
        <script src="${pageContext.servletContext.contextPath}/assets/js/user.js"></script>
    </body>
</html>