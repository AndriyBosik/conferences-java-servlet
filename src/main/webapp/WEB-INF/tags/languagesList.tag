<%@ tag %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ attribute name="className" type="java.lang.String" %>
<%@ attribute name="basicLinkClass" type="java.lang.String" %>
<%@ attribute name="activeLinkClass" type="java.lang.String" %>

<c:set var="basicLinkClass" value="${(empty basicLinkClass) ? 'teal-text text-lighten-1' : basicLinkClass}" />
<c:set var="activeLinkClass" value="${(empty activeLinkClass) ? 'red-text' : activeLinkClass}" />

<ul class="uppercase weight-normal ${className}">
    <c:forEach items="${requestScope.languages}" var="language">
        <li>
            <a href="/${language}" class="${requestScope.currentLanguage.equals(language) ? activeLinkClass : basicLinkClass} px5">${language}</a>
        </li>
    </c:forEach>
</ul>