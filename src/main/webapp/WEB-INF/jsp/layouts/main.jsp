<%@ page pageEncoding="UTF-8" contentType="text/html;charset=UTF-8" %>
<%@ taglib uri="tags" prefix="taglib" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=utf-8">
        <meta name="viewport" content="width=device-width, initial-scale=1">

        <title>
            <c:choose>
                <c:when test="${(empty title or title eq '')}">
                    <taglib:message value="page.title.${view}" />
                </c:when>
                <c:otherwise>
                    <c:out value="${title}" />
                </c:otherwise>
            </c:choose>
        </title>

        <link href="https://fonts.googleapis.com/icon?family=Material+Icons" rel="stylesheet">
        <link href="/resources/css/site.css" rel="stylesheet" />
        <link href="/resources/css/common.css" rel="stylesheet" />
        <link href="/resources/css/indentations.css" rel="stylesheet" />
        <link href="/resources/css/materialize.min.css" rel="stylesheet" />
        <link href="/resources/css/materialize-extensions.css" rel="stylesheet" />

        <c:catch>
            <jsp:include page="/WEB-INF/jsp/views/${view}/styles.jsp" />
        </c:catch>
    </head>
    <body>
        <jsp:include page="/WEB-INF/jsp/components/navbar.jsp" />

        <main>
            <jsp:include page="/WEB-INF/jsp/views/${view}/content.jsp" />
        </main>

        <jsp:include page="/WEB-INF/jsp/components/footer.jsp" />
        <jsp:include page="/WEB-INF/jsp/components/i18n-js.jsp" />
        <jsp:include page="/WEB-INF/jsp/components/constants-js.jsp" />
        <jsp:include page="/WEB-INF/jsp/components/default-js.jsp" />

        <script type="text/javascript" src="/resources/js/moment.min.js"></script>
        <script type="text/javascript" src="/resources/js/materialize.min.js"></script>
        <script type="text/javascript" src="/resources/js/helper-functions.js"></script>
        <script type="text/javascript" src="/resources/js/updaters.js"></script>
        <script type="text/javascript" src="/resources/js/materialize-extensions.js"></script>
        <script type="text/javascript" src="/resources/js/startup.js"></script>

        <c:catch>
            <jsp:include page="/WEB-INF/jsp/views/${view}/scripts.jsp" />
        </c:catch>
    </body>
</html>
