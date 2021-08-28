<%@ taglib uri="tags" prefix="taglib" %>
<%@ taglib prefix="tags" uri="tags" %>

<div id="topic-form" class="modal">
    <form action="<tags:linkTo href="/topics/create" />" method="post">
        <input type="hidden" name="meeting_id" value="${meeting.id}" />

        <div class="modal-content">
            <h5><taglib:message value="create_topic" /></h5>

            <div class="input-field full-width">
                <input id="topic-title" name="title" type="text">
                <label for="topic-title"><taglib:message value="title" /></label>
            </div>
        </div>
        <div class="modal-footer">
            <div class="col px20">
                <button type="submit" class="btn waves-effect waves-light">
                    <taglib:message value="confirm"/>
                    <i class="material-icons right">check</i>
                </button>
            </div>
        </div>
    </form>
</div>