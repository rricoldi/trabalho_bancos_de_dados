<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<!DOCTYPE html>
<html>
    <head>
        <%@include file="/view/include/head.jsp"%>
        <title>Login de Usuário</title>
    </head>
    <body>
        <div class="container">
            <form class="form-signin" action="${pageContext.servletContext.contextPath}/login" method="POST">
                <h2 class="form-signin-heading">Por favor, faça login.</h2>

                <input class="form-control" type="email" name="email" placeholder="podcast@mail.com" required autofocus>
                <input class="form-control" type="password" name="senha" placeholder="********" required>
                <p class="help-block">Ainda não é cadastrado?
                    <a href="${pageContext.servletContext.contextPath}/user/create">
                        Clique aqui
                    </a>
                </p>

                <button class="btn btn-lg btn-primary btn-block" type="submit">Login</button>
            </form>
                        
        </div>

        <%@include file="/view/include/scripts.jsp"%>
        <script src="${pageContext.servletContext.contextPath}/assets/js/base.js"></script>
    </body>
</html>