<%@ tag %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ attribute name="roles" required="true" type="java.util.List" %>

<c:if test="${fn:containsIgnoreCase(roles, sessionScope.user.role.title)}">

    <jsp:doBody />

</c:if>