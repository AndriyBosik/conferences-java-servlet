<%@ taglib uri="tags" prefix="taglib" %>
<%@ taglib prefix="tags" uri="tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<div id="topic-proposal" class="modal">

    <jsp:include page="/WEB-INF/jsp/components/preloaders/circle-preloader.jsp">
        <jsp:param name="preloaderId" value="topic-preloader" />
    </jsp:include>

    <form id="add-topic-proposal-form" action="<tags:linkTo href="/topics/propose" />" method="post" class="s-vflex full-height m0">
        <input type="hidden" name="meeting_id" value="${meeting.id}" />

        <div class="modal-content row full-width" style="margin-bottom: 0px">
            <h5 class="col s12"><taglib:message value="propose_topic" /></h5>

            <div class="input-field col s12">
                <input id="topic-title" name="topic_title" type="text">
                <label for="topic-title"><taglib:message value="title" /></label>
            </div>
        </div>
        <div class="modal-footer equal-flex s-vflex-end">
            <div class="col px20">
                <button type="submit" class="btn waves-effect waves-light">
                    <taglib:message value="propose"/>
                    <i class="material-icons right">local_offer</i>
                </button>
            </div>
        </div>
    </form>
</div>