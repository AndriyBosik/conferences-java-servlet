<%@ taglib uri="tags" prefix="taglib" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="tf" tagdir="/WEB-INF/tags" %>

<div class="container">
    <div class="row">
        <div class="col s12">
            <div class="s-hflex">
                <div class="equal-flex">
                    <h4 class="grey-text text-darken-2 mb0"><taglib:message value="header.meetings" /></h4>
                </div>
                <c:if test="${sessionScope.user.role.title == 'moderator'}">
                    <div class="s-vflex-end">
                        <a href="#meeting-form" class="btn waves-effect waves-light modal-trigger">
                            <taglib:message value="add" />
                            <i class="material-icons right">add</i>
                        </a>
                    </div>
                </c:if>
            </div>
            <hr />
        </div>
        <div class="meetings-list-area col s12" style="padding-left: 0px; padding-right: 0px;">
            <c:choose>

                <c:when test="${not empty meetings}">
                    <c:forEach items="${meetings}" var="meeting">
                        <div class="col s12 m4">
                            <div class="card hoverable meeting-card">
                                <div class="card-image stretch-background" style="background-image: url('/resources/images/meetings/${meeting.imagePath}')">
                                    <a href="<taglib:linkTo href="/meetings/edit/${meeting.id}" />" class="tooltipped waves-light blue-text text-darken-3 top-right-element text-hoverable" data-position="right" data-tooltip="<taglib:message value="edit" />">
                                        <i class="material-icons small">edit</i>
                                    </a>

                                    <a href="<taglib:linkTo href="/meetings/show/${meeting.id}" />" class="tooltipped btn-floating halfway-fab waves-effect waves-light blue darken-3" data-position="right" data-tooltip="<taglib:message value="view" />">
                                        <i class="material-icons">arrow_forward</i>
                                    </a>
                                </div>
                                <div class="card-content">
                                    <span class="card-title truncate" style="font-weight: 400;">${meeting.title}</span>
                                    <p class="truncate-4 translucent-3p">
                                            ${meeting.description}
                                    </p>
                                    <hr class="date-divider" />
                                    <div class="s-hflex-end">
                                <span class="translucent">
                                    <fmt:formatDate value="${meeting.date}" pattern="dd-MM-yyyy HH:mm" />
                                </span>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </c:forEach>

                    <tf:pagination currentPage="1" pagesCount="7" paginationClass="col s12" />

                </c:when>

                <c:otherwise>
                    <p class="center-align translucent large-text">
                        <taglib:message value="page_not_found" />
                    </p>
                </c:otherwise>

            </c:choose>
        </div>

    </div>
</div>

<c:if test="${sessionScope.user.role.title == 'moderator'}">
    <jsp:include page="/WEB-INF/jsp/components/modals/new-meeting-form.jsp" />
</c:if>