<%@ tag %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ attribute name="pagesCount" required="true" type="java.lang.Integer" %>
<%@ attribute name="currentPage" required="true" type="java.lang.Integer" %>
<%@ attribute name="pageColor" type="java.lang.String" %>
<%@ attribute name="paginationId" type="java.lang.String" %>
<%@ attribute name="paginationClass" type="java.lang.String" %>


<c:if test="${pagesCount > 1}">

    <c:set var="pageColor" value="${(empty pageColor) ? 'teal' : pageColor}" />

    <div id="${paginationId}" class="pages-area ${paginationClass}">
        <ul class="pagination center-align">
            <li class="${currentPage == 1 ? "disabled" : "waves-effect"}">
                <a href="#"><i class="material-icons">chevron_left</i></a>
            </li>

            <c:forEach begin="1" end="${pagesCount}" varStatus="status">
                <li class="${status.index == currentPage ? "active ".concat(pageColor) : "waves-effect"}"><a href="#">${status.index}</a></li>
            </c:forEach>

            <li class="${currentPage == pagesCount ? "disabled" : "waves-effect"}">
                <a href="#"><i class="material-icons">chevron_right</i></a>
            </li>
        </ul>
    </div>

</c:if>