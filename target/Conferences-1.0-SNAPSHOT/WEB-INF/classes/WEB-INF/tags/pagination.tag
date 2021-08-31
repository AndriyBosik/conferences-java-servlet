<%@ tag %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="taglib" uri="tags" %>
<%@ attribute name="pagesLinks" required="true" type="java.util.List" %>
<%@ attribute name="currentPage" required="true" type="java.lang.Integer" %>
<%@ attribute name="pageColor" type="java.lang.String" %>
<%@ attribute name="paginationId" type="java.lang.String" %>
<%@ attribute name="paginationClass" type="java.lang.String" %>


<c:if test="${pagesLinks.size() > 1}">

    <c:set var="emptyLink" value="#" />

    <c:set var="pageColor" value="${(empty pageColor) ? 'teal' : pageColor}" />
    <c:set var="previousPage" value="${currentPage > 1 ? pagesLinks.get(currentPage - 2) : emptyLink}" />
    <c:set var="nextPage" value="${currentPage < pagesLinks.size() ? pagesLinks.get(currentPage) : emptyLink}" />

    <div id="${paginationId}" class="pages-area ${paginationClass}">
        <ul class="pagination center-align">
            <li class="${currentPage == 1 ? "disabled" : "waves-effect"}">
                <a href="<taglib:linkTo href="${previousPage}" saveQueryString="true" />" class="${previousPage == emptyLink ? 'deactivate' : ''}"><i class="material-icons">chevron_left</i></a>
            </li>

            <c:forEach begin="1" end="${pagesLinks.size()}" varStatus="status">
                <li class="${status.index == currentPage ? "active ".concat(pageColor) : "waves-effect"}">
                    <a href="<taglib:linkTo href="${pagesLinks.get(status.index - 1)}" saveQueryString="true" />">${status.index}</a>
                </li>
            </c:forEach>

            <li class="${currentPage == pagesLinks.size() ? "disabled" : "waves-effect"}">
                <a href="<taglib:linkTo href="${nextPage}" saveQueryString="true" />" class="${nextPage == emptyLink ? 'deactivate' : ''}"><i class="material-icons">chevron_right</i></a>
            </li>
        </ul>
    </div>

</c:if>