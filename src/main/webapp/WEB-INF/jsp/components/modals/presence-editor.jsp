<%@ taglib uri="tags" prefix="taglib" %>
<%@ taglib prefix="tags" uri="tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<div id="presence-editor" class="modal height-70">

    <div class="presenceSendingPreloader progress hidden">
        <div class="indeterminate"></div>
    </div>

    <div class="modal-content row full-width">
        <h5 class="col s12"><taglib:message value="edit_presence" /></h5>
        <div class="col s12">
            <blockquote class="fs20">
                <taglib:message value="you_have_to_refresh_page_to_see_changes" />
            </blockquote>
        </div>
        <div class="col s12">
            <ul id="usersPresenceCollection" class="collection">
                <c:forEach items="${stats.usersMeetings}" var="stat">
                    <li class="collection-item s-hflex">
                        <div class="z-depth-1 user-avatar stretch-background">
                            <img src="/resources/images/avatars/<c:out value="${stat.user.imagePath}" /> " alt="" class="circle full-width full-height" />
                        </div>
                        <span class="title weight-normal s-vflex-center mx10 equal-flex">
                            <c:out value="${stat.user.name}" /> <c:out value="${stat.user.surname}" />
                        </span>
                        <span class="secondary-content s-vflex-center col s12 m3">
                            <div class="s-hflex-end">
                                <span class="presenceValue" data-presence="${stat.present}" data-user-id="${stat.user.id}"></span>
                            </div>
                        </span>
                    </li>
                </c:forEach>
            </ul>
        </div>
    </div>
</div>