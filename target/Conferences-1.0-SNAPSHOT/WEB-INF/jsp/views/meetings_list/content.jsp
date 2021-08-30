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
                <tf:forRoles roles="${['moderator']}">
                    <div class="s-vflex-end">
                        <a href="#meeting-form" class="btn waves-effect waves-light modal-trigger">
                            <taglib:message value="add" />
                            <i class="material-icons right">add</i>
                        </a>
                    </div>
                </tf:forRoles>
            </div>
            <hr />
        </div>

        <div class="filters col s12">
            <form action="" method="get">
                <div class="s-hflex">
                    <div class="input-field">
                        <select id="sort-by-option" name="sort-by" initial-value="${sortByOption}">
                            <option value="" disabled selected><taglib:message value="sort_by" />...</option>
                            <option value="date"><taglib:message value="by_date" /></option>
                            <option value="users"><taglib:message value="by_users" /></option>
                            <option value="topics"><taglib:message value="by_topics" /></option>
                        </select>
                    </div>
                    <div class="input-field mx10">
                        <select id="sort-order-option" name="sort-order" initial-value="${sortOrderOption}">
                            <option value="asc" selected><taglib:message value="ascending" /></option>
                            <option value="desc"><taglib:message value="descending" /></option>
                        </select>
                    </div>
                    <div class="input-field">
                        <select id="select-option" name="filter-selector" initial-value="${filterSelector}">
                            <option value="all" selected><taglib:message value="all" /></option>
                            <option value="past"><taglib:message value="past" /></option>
                            <option value="future"><taglib:message value="future" /></option>
                        </select>
                    </div>
                    <div class="s-vflex-center mx10">
                        <button type="submit" class="btn waves-effect orange">
                            <taglib:message value="confirm" />
                        </button>
                    </div>
                </div>
            </form>
        </div>

        <div class="meetings-list-area col s12" style="padding-left: 0px; padding-right: 0px;">
            <c:choose>

                <c:when test="${not empty meetings}">
                    <c:forEach items="${meetings}" var="meeting">
                        <div class="col s12 m6 l4">
                            <div class="card hoverable meeting-card">
                                <div class="card-header">
                                    <h6 class="py5 px10 translucent truncate">
                                        ${meeting.address}
                                    </h6>
                                </div>
                                <div class="card-image stretch-background" style="background-image: url('/resources/images/meetings/${meeting.imagePath}')">
                                    <tf:forRoles roles="${['moderator']}">
                                        <a href="<taglib:linkTo href="/meetings/edit/${meeting.id}" />" class="tooltipped waves-light blue-text text-darken-3 top-right-element text-hoverable" data-position="right" data-tooltip="<taglib:message value="edit" />">
                                            <i class="material-icons small">edit</i>
                                        </a>
                                    </tf:forRoles>

                                    <a href="<taglib:linkTo href="/meetings/show/${meeting.id}" />" class="tooltipped btn-floating halfway-fab waves-effect waves-light blue darken-3" data-position="right" data-tooltip="<taglib:message value="view" />">
                                        <i class="material-icons">arrow_forward</i>
                                    </a>
                                </div>
                                <div class="card-content">
                                    <span class="card-title truncate" style="font-weight: 400;">${meeting.title}</span>

                                    <div class="s-vflex mb5">
                                        <div class="s-hflex">
                                            <span class="weight-strong"><taglib:message value="topics" />: </span>
                                            <span class="px5">${meeting.reportTopicsCount}</span>
                                        </div>
                                        <div class="s-hflex">
                                            <span class="weight-strong"><taglib:message value="participants" />: </span>
                                            <span class="px5">${meeting.usersCount}</span>
                                        </div>
                                    </div>

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

                    <tf:pagination
                            currentPage="${currentPage}"
                            pagesLinks="${pagesLinks}"
                            paginationClass="col s12" />

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

<tf:forRoles roles="${['moderator']}">
    <jsp:include page="/WEB-INF/jsp/components/modals/new-meeting-form.jsp" />
</tf:forRoles>
