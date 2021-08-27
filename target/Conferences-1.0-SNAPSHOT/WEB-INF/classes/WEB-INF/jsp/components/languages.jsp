<%@ taglib prefix="taglib" uri="tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<ul class="languages-list my10 <%= request.getParameter("class") %>">
    <c:forEach items="${languages}" var="language">
        <c:set var="linkClass" value="teal-text text-lighten-1" />
        <c:if test="${currentLanguage.equals(language)}">
            <c:set var="linkClass" value="red-text" />
        </c:if>

        <li><a class="<c:out value="${linkClass}" />" href="/${language}">${language}</a></li>
    </c:forEach>
</ul>