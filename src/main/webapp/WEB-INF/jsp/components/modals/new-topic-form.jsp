<%@ taglib uri="tags" prefix="taglib" %>
<%@ taglib prefix="tags" uri="tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<div id="topic-form" class="modal height-70">

    <jsp:include page="/WEB-INF/jsp/components/preloaders/circle-preloader.jsp">
        <jsp:param name="preloaderId" value="topic-preloader" />
    </jsp:include>

    <form id="new-topic-form" action="<tags:linkTo href="/topics/create" />" method="post" class="s-vflex full-height m0">
        <input type="hidden" name="id" value="" />
        <input type="hidden" name="report_topic_speaker_id" value="" />
        <input type="hidden" name="meeting_id" value="${meeting.id}" />

        <div class="modal-content row full-width">
            <h5 class="col s12"><taglib:message value="create_topic" /></h5>

            <div class="input-field col m6 s12">
                <input id="topic-title" name="title" type="text">
                <label for="topic-title"><taglib:message value="title" /></label>
            </div>

            <div class="input-field col m6 s12">
                <select name="speaker_id" class="icons">
                    <option value="" selected><taglib:message value="choose_speaker" /></option>
                    <c:forEach items="${speakers}" var="speaker">
                        <option value="${speaker.id}" data-icon="/resources/images/avatars/${speaker.imagePath}">
                            <c:out value="${speaker.name}" /> <c:out value="${speaker.surname}" />
                        </option>
                    </c:forEach>
                </select>
            </div>
        </div>
        <div class="modal-footer equal-flex s-vflex-end">
            <div class="col px20">
                <button type="submit" class="btn waves-effect waves-light">
                    <taglib:message value="confirm"/>
                    <i class="material-icons right">check</i>
                </button>
            </div>
        </div>
    </form>
</div>