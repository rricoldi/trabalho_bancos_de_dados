<%@page contentType="text/html" pageEncoding="UTF-8" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@taglib tagdir="/WEB-INF/tags/session" prefix="session"%>
<session:my_user context="${pageContext.servletContext.contextPath}"/>
<!DOCTYPE html>
<html>
    <head>
        <%@include file="/view/include/head.jsp" %>
        <title>Atualização de podcast!</title>
    </head>
    <body>

        <div class="container">
            <h2 class="text-center">Atualização dos dados do podcast <c:out value="${podcast.nome}"/></h2>

            <form
                class="form"
                action="${pageContext.servletContext.contextPath}/podcast/update"
                enctype="form-data"
                method="POST">
                
                <input type="hidden" name="rss_feed" value="${podcast.rss_feed}">
                
                <div class="form-group">
                    <label for="podcast-nome" class="control-label">Nome</label>
                    <input id="podcast-nome" class="form-control" value="${podcast.nome}" type="text" name="nome" required/>
                </div>

                <div class="form-group">
                    <label class="control-label" for="podcast-rss_feed">Rss Feed</label>
                    <input id="podcast-rss_feed" class="form-control" value="${podcast.rss_feed}" type="url" name="rss_feed" required autofocus/>

                    <p class="help-block"></p>
                </div>

                <div class="form-group">
                    <label class="control-label" for="podcast-site">Site do Podcast</label>
                    <input id="podcast-site" class="form-control" value="${podcast.site}" type="url" name="site" required autofocus/>

                    <p class="help-block"></p>
                </div>

                <div class="text-center">
                    <button class="btn btn-lg btn-primary" type="submit">Atualizar</button>
                </div>
            </form>
        </div>

        <%@include file="/view/include/scripts.jsp" %>
        <script src="${pageContext.servletContext.contextPath}/assets/js/podcast.js"></script>
    </body>
</html>