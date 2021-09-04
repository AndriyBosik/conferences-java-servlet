<%@ tag %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ attribute name="errors" required="true" type="java.util.List" %>

<c:if test="${(not empty errors) and (errors.size() > 0)}">
    <blockquote class="red-text">
        <c:forEach items="${errors}" var="error">
            <h6 class="weight-normal">${error}</h6>
        </c:forEach>
    </blockquote>
</c:if>