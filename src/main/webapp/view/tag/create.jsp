<%@page contentType="text/html" pageEncoding="UTF-8" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@taglib tagdir="/WEB-INF/tags/session" prefix="session"%>
<session:my_user context="${pageContext.servletContext.contextPath}"/>
<!DOCTYPE html>
<html>
    <head>
        <%@include file="/view/include/head.jsp" %>
        <title>Tag!</title>
    </head>
    <body>

        <div class="container">
            <h2 class="text-center">Tag do podcast</h2>

            <form
                class="form"
                action="${pageContext.servletContext.contextPath}/tag/create"
                enctype="form-data"
                method="POST">
                
                <input type="hidden" name="rss_feed" value="<%= request.getParameter("rss_feed") %>">

                <div class="form-group">
                    <label for="tag" class="control-label">Tag</label>
                    <input id="tag" class="form-control" placeholder="tag" type="text" name="tag" required/>
                </div>

                <div class="text-center">
                    <button class="btn btn-lg btn-primary" type="submit">Adicionar Tag!</button>
                </div>
            </form>
        </div>

        <%@include file="/view/include/scripts.jsp" %>
        <script src="${pageContext.servletContext.contextPath}/assets/js/podcast.js"></script>

    </body>
</html>