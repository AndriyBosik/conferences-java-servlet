<%@ taglib uri="tags" prefix="taglib" %>
<%@ taglib prefix="tags" uri="tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<div id="topic-form" class="modal height-70">
    <form action="<tags:linkTo href="/topics/create" />" method="post" class="s-vflex full-height m0">
        <input type="hidden" name="meeting_id" value="${meeting.id}" />

        <div class="modal-content row full-width">
            <h5 class="col s12"><taglib:message value="create_topic" /></h5>

            <div class="input-field col m6 s12">
                <input id="topic-title" name="title" type="text">
                <label for="topic-title"><taglib:message value="title" /></label>
            </div>

            <div class="input-field col m6 s12">
                <select name="speaker_id" class="icons">
                    <option value="" disabled selected>Choose speaker</option>
                    <c:forEach items="${speakers}" var="speaker">
                        <option value="${speaker.id}" data-icon="/resources/images/avatars/${speaker.login}.png">
                            ${speaker.name} ${speaker.surname}
                        </option>
                        <label>Assign speaker</label>
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