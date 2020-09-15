<%-- 
    Document   : my_user
    Created on : 14/09/2020, 19:10:52
    Author     : renan
--%>

<%@tag description="User authentication handler" pageEncoding="UTF-8"%>

<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@attribute name="context" required="true"%>

<c:if test="${empty sessionScope.usuario}">
    <c:redirect url="/" />
</c:if>