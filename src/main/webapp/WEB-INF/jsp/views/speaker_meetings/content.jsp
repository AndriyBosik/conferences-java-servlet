<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="tags" prefix="taglib" %>
<%@ taglib prefix="tf" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<div class="container">
    <div class="row">
        <div class="col s12">
            <h4 class="grey-text text-darken-2 mb0"><taglib:message value="my_meetings" /></h4>
            <hr />
        </div>

        <jsp:include page="/WEB-INF/jsp/components/meetings.jsp" />

    </div>
</div>