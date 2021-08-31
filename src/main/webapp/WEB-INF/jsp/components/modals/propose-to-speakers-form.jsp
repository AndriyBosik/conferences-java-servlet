<%@ taglib uri="tags" prefix="taglib" %>
<%@ taglib prefix="tags" uri="tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<div id="propose-to-speakers-form" class="modal height-70">
    <jsp:include page="/WEB-INF/jsp/components/preloaders/circle-preloader.jsp">
        <jsp:param name="preloaderId" value="proposal-preloader" />
    </jsp:include>

    <div class="proposalSendingPreloader progress hidden">
        <div class="indeterminate"></div>
    </div>

    <div class="modal-content row full-width">
        <h5 class="col s12"><taglib:message value="propose_to_speakers" /></h5>
        <div class="col s12">
            <ul id="availableSpeakersCollection" class="collection">
                <c:forEach items="${speakers}" var="speaker">
                    <li class="collection-item s-hflex">
                        <div class="z-depth-1 user-avatar stretch-background">
                            <img src="/resources/images/avatars/${speaker.login}.png" alt="" class="circle full-width full-height" data-error="avatarDefault" />
                        </div>
                        <span class="title weight-normal s-vflex-center mx10 equal-flex">
                            ${speaker.name} ${speaker.surname}
                        </span>
                        <span class="secondary-content s-vflex-center">
                            <form action="<taglib:linkTo href="/topics/propose-speakers" />" method="post" class="m0 proposalForm">
                                <input type="hidden" name="speaker_id" value="${speaker.id}">
                                <input type="hidden" name="topic_id" value="" />
                                <button type="submit" class="btn waves-effect waves-light">
                                    <taglib:message value="propose" />
                                    <i class="material-icons right">check</i>
                                </button>
                            </form>
                        </span>
                    </li>
                </c:forEach>
            </ul>
        </div>
    </div>
</div>