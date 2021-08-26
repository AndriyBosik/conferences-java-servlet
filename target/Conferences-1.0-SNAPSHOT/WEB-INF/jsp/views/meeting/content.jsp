<%@ taglib prefix="taglib" uri="tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="tf" tagdir="/WEB-INF/tags" %>

<div class="container py20">
    <div class="row">
        <div class="meeting-container s-vflex">
            <div class="z-depth-2 stretch-background" style="height: 300px; background-image: url('/resources/images/meetings/${meeting.imagePath}')"></div>

            <div class="s-hflex pt15">
                <p class="meeting-title weight-slight m0 equal-flex">${meeting.title}</p>
                <p class="meeting-title translucent m0 s-hflex-center">
                    <span>
                        <fmt:formatDate value="${meeting.date}" pattern="dd-MM-yyyy HH:mm" />
                    </span>
                    <span class="s-vflex-center">
                        <i class="material-icons small ml5">
                            access_time
                        </i>
                    </span>
                </p>
            </div>
            <div class="separator mb10"></div>

            <p class="meeting-description translucent-2p m0">
                ${meeting.description}
            </p>

            <c:choose>
                <c:when test="${not empty meeting.reportTopics}">
                    <p class="meeting-title weight-slight m0 pt15"><taglib:message value="topics" /></p>
                    <div class="separator mb10"></div>

                    <table class="striped highlight z-depth-1">
                        <thead>
                            <tr>
                                <th class="center-align">#</th>
                                <th><taglib:message value="speaker" /></th>
                                <th><taglib:message value="topic" /></th>
                            </tr>
                        </thead>
                        <tbody>
                            <c:forEach items="${meeting.reportTopics}" var="topic" varStatus="loopStatus">
                                <tr class="clickable topic-item" data-href="<taglib:linkTo href="/topics/show/${topic.id}" />">
                                    <td class="center-align">${loopStatus.index + 1}</td>
                                    <td>
                                        <div class="s-hflex">
                                            <div class="z-depth-1 user-avatar stretch-background" style="background-image: url('/resources/images/avatars/${topic.speaker.login}.png')"></div>
                                            <div class="s-vflex-center px10 weight-normal">${topic.speaker.name} ${topic.speaker.surname}</div>
                                        </div>
                                    </td>
                                    <td>${topic.title}</td>
                                </tr>
                            </c:forEach>
                        </tbody>
                    </table>

                    <tf:pagination currentPage="1" pagesCount="7"/>

                </c:when>

                <c:otherwise>
                    <p class="center-align translucent large-text">
                        <taglib:message value="no_topics" />
                    </p>
                </c:otherwise>
            </c:choose>
        </div>
    </div>
</div>