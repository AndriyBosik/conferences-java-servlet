<%@ taglib prefix="taglib" uri="tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<div class="container py20">
    <div class="row">
        <div class="meeting-container s-vflex">
            <div class="z-depth-2 stretch-background" style="height: 300px; background-image: url('/resources/images/${meeting.imagePath}')"></div>

            <p class="meeting-title weight-slight m0 pt15">${meeting.title}</p>
            <div class="separator mb10"></div>

            <p class="meeting-description translucent-2p m0">
                ${meeting.description}
            </p>

            <c:choose>
                <c:when test="${not empty meeting.reportTopics}">
                    <p class="meeting-title weight-slight m0 pt15"><taglib:message value="topics" /></p>
                    <div class="separator mb10"></div>

                    <c:forEach items="${meeting.reportTopics}" var="topic">
                        <h6>${topic.title}(${topic.speaker.name} ${topic.speaker.surname})</h6>
                        <br />
                    </c:forEach>
                </c:when>

                <c:otherwise>
                    There are no topics available yet :(
                </c:otherwise>
            </c:choose>
        </div>
    </div>
</div>