<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="tags" prefix="taglib" %>
<%@ taglib prefix="tf" tagdir="/WEB-INF/tags" %>

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
                <div class="col s12 m6 l4 meetingContainer" data-id="${meeting.id}">
                    <div class="card hoverable meeting-card">
                        <div class="card-header s-hflex">
                            <h6 class="py5 px10 translucent truncate" data-address>
                                ${meeting.address}
                            </h6>
                        </div>
                        <div class="card-image stretch-background" style="background-image: url('/resources/images/meetings/${meeting.imagePath}')">
                            <a href="<taglib:linkTo href="/meetings/show/${meeting.id}" />" class="tooltipped btn-floating halfway-fab waves-effect waves-light blue darken-3" data-position="right" data-tooltip="<taglib:message value="view" />">
                                <i class="material-icons">arrow_forward</i>
                            </a>
                            <tf:forRoles roles="${['moderator']}">
                                <jsp:useBean id="now" class="java.util.Date" />
                                <div class="top-right-element s-hflex">
                                    <c:if test="${not meeting.outdated}">
                                        <div class="clickable tooltipped waves-light blue-text text-darken-3 text-hoverable modal-trigger" data-position="right" data-tooltip="<taglib:message value="edit" />" data-target="edit-meeting-modal">
                                            <i class="material-icons small">edit</i>
                                        </div>
                                    </c:if>
                                    <div class="clickable tooltipped waves-light orange-text text-hoverable" data-position="right" data-tooltip="<taglib:message value="joined_users" />: ${meeting.usersCount}<br /><taglib:message value="was_present" />: ${meeting.presentUsersCount}">
                                        <i class="material-icons small">help</i>
                                    </div>
                                </div>
                            </tf:forRoles>
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
                                <span class="translucent" data-date>
                                    <taglib:dateFormatter date="${meeting.date}" format="dd-MM-yyyy HH:mm" />
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