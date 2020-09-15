<%@page contentType="text/html" pageEncoding="UTF-8" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@taglib tagdir="/WEB-INF/tags/session" prefix="session"%>
<session:my_user context="${pageContext.servletContext.contextPath}"/>
<!DOCTYPE html>
<html>
    <head>
        <%@include file="/view/include/head.jsp" %>
        <title>Comentário!</title>
    </head>
    <body>

        <div class="container">
            <h2 class="text-center">Comentário no podcast</h2>

            <form
                class="form"
                action="${pageContext.servletContext.contextPath}/comentario/create"
                enctype="form-data"
                method="POST">
                
                <input type="hidden" name="rss_feed" value="<%= request.getParameter("rss_feed") %>">
                <input type="hidden" name="email" value="${usuario.email}">

                <div class="form-group">
                    <label class="control-label" for="comentario">Comentario</label>
                    <textarea id="comentario" class="form-control" placeholder="Comentário" rows="10" name="comentario" required autofocus></textarea>

                </div>

                <div class="text-center">
                    <button class="btn btn-lg btn-primary" type="submit">Comentar</button>
                </div>
            </form>
        </div>

        <%@include file="/view/include/scripts.jsp" %>
        <script src="${pageContext.servletContext.contextPath}/assets/js/podcast.js"></script>

    </body>
</html>