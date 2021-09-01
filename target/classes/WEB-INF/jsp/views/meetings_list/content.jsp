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

        <jsp:include page="/WEB-INF/jsp/components/meetings.jsp" />

    </div>
</div>

<tf:forRoles roles="${['moderator']}">
    <jsp:include page="/WEB-INF/jsp/components/modals/new-meeting-form.jsp" />
    <jsp:include page="/WEB-INF/jsp/components/modals/edit-meeting-form.jsp" />
</tf:forRoles>
