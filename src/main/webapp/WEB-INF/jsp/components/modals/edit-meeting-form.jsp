<%@ taglib uri="tags" prefix="taglib" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<div id="edit-meeting-modal" class="modal height-70">
    <jsp:include page="/WEB-INF/jsp/components/preloaders/circle-preloader.jsp">
        <jsp:param name="preloaderId" value="meeting-preloader" />
    </jsp:include>

    <form action="<taglib:linkTo href="/meetings/edit" />" method="post">
        <input type="hidden" name="id" value="" />
        <div class="modal-content row">
            <h5 class="col s12"><taglib:message value="edit_meeting" /></h5>

            <div class="input-field col s12">
                <input id="address" name="address" type="text" value="<c:out value="${updatedMeetingFields.address}" />">
                <label for="address"><taglib:message value="address" /></label>
            </div>

            <div class="col s12 s-hflex-center">
                <input type="text" name="date" placeholder="<taglib:message value="select_date" />" class="date-picker" />
                <div class="col">
                    <input type="number" name="hours" min="0" max="23" value="12" class="center-align" />
                </div>
                <span class="s-vflex-center weight-normal time-divider">:</span>
                <div class="col">
                    <input type="number" name="minutes" min="0" max="59" value="00" class="center-align" />
                </div>
            </div>
        </div>

        <div class="modal-footer">
            <div class="col px20">
                <button type="submit" class="btn waves-effect waves-light">
                    <taglib:message value="confirm" />
                    <i class="material-icons right">check</i>
                </button>
            </div>
        </div>
    </form>
</div>