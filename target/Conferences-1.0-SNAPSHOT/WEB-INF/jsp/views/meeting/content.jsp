<%@ taglib prefix="taglib" uri="tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="tf" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<div class="container py20">
    <div class="row">
        <div class="meeting-container s-vflex">
            <div class="s-hflex full-width">
                <p class="meeting-title weight-slight m0 equal-flex truncate"><c:out value="${meeting.title}" /></p>

                <tf:forRoles roles="${['user']}">
                    <div class="s-hflex-end">
                        <c:choose>
                            <c:when test="${not isJoined}">
                                <jsp:useBean id="now" class="java.util.Date" />
                                <c:if test="${meeting.date gt now}">
                                    <form class="m0" action="<taglib:linkTo href="/meetings/join-user" />" method="post">
                                        <input type="hidden" name="meeting_id" value="${meeting.id}">
                                        <button type="submit" class="btn waves-effect waves-light light-blue darken-4">
                                            <taglib:message value="join" />
                                            <i class="material-icons right">person_add</i>
                                        </button>
                                    </form>
                                </c:if>
                            </c:when>

                            <c:otherwise>
                                <div class="weight-strong translucent uppercase s-hflex uppercase">
                                    <i class="material-icons s-vflex-center px5 weight-strong">check</i>
                                    <span class="s-vflex-center"><taglib:message value="joined" /></span>
                                </div>
                            </c:otherwise>
                        </c:choose>
                    </div>
                </tf:forRoles>
            </div>

            <div class="separator mb10"></div>

            <tf:forRoles roles="${['moderator']}">
                <div class="full-width s-vflex my15">
                    <div class="s-hflex-end">
                        <div class="equal-flex s-hflex-start">
                            <c:if test="${stats.totalUsersCount > 0}">
                                <button type="button" class="btn btn-small waves-effect waves-light blue darken-3 modal-trigger" data-target="presence-editor">
                                    <taglib:message value="edit" />
                                    <i class="material-icons right">edit</i>
                                </button>
                            </c:if>
                        </div>
                        <div class="translucent s-hflex fs20 tooltipped" data-position="bottom" data-tooltip="<taglib:message value="present_users" />/<taglib:message value="joined_users" />">
                            <span class="s-vflex-end px5">
                                <i class="material-icons fs30">people</i>
                            </span>
                            <span class="s-vflex-end">${stats.presentUsersCount}/${stats.totalUsersCount}</span>
                        </div>
                    </div>
                    <div class="progress">
                        <div class="determinate blue darken-3" style="width: ${(stats.presentUsersCount / stats.totalUsersCount)*100}%"></div>
                    </div>
                </div>
            </tf:forRoles>

            <div class="col s12">
                <tf:errors errors="${updatedTopicErrors}"></tf:errors>
                <tf:errors errors="${createdTopicErrors}"></tf:errors>
            </div>

            <div class="z-depth-2 stretch-background" style="height: 300px; background-image: url('/resources/images/meetings/<c:out value="${meeting.imagePath}" />')"></div>

            <div class="full-width py15 meeting-additional-data">
                <div class="meeting-where s-hflex my5">
                    <span class="weight-strong s-hflex">
                        <i class="material-icons pr5 float-left">location_on</i>
                        <taglib:message value="where" />:
                    </span>
                    <span class="translucent-3p px5">
                        <c:out value="${meeting.address}" />
                    </span>
                </div>
                <div class="meeting-when s-hflex my5">
                    <span class="weight-strong s-hflex">
                        <i class="material-icons pr5 float-left">access_time</i>
                        <taglib:message value="when" />:
                    </span>
                    <span class="translucent-3p px5">
                        <taglib:dateFormatter date="${meeting.date}" format="dd-MM-yyyy HH:mm" />
                    </span>
                </div>
                <div class="meeting-where s-hflex my5">
                    <span class="weight-strong s-hflex">
                        <i class="material-icons pr5 float-left">title</i>
                        <taglib:message value="topics" />:
                    </span>
                    <span class="translucent-3p px5">
                        <c:out value="${meeting.reportTopicsCount}" />
                    </span>
                </div>
                <div class="meeting-where s-hflex my5">
                    <span class="weight-strong s-hflex">
                        <i class="material-icons pr5 float-left">people</i>
                        <taglib:message value="participants" />:
                    </span>
                    <span class="translucent-3p px5">
                        ${meeting.usersCount}
                    </span>
                </div>
            </div>

            <p class="meeting-description translucent-2p m0">
                <c:out value="${meeting.description}" />
            </p>

            <div class="s-hflex">
                <p class="meeting-title weight-slight m0 pt15 equal-flex"><taglib:message value="topics" /></p>

                <div class="s-vflex-end">
                    <tf:forRoles roles="${['moderator']}">
                        <a href="#topic-form" class="btn waves-effect waves-light modal-trigger createTopicFormTrigger">
                            <taglib:message value="add" />
                            <i class="material-icons right">add</i>
                        </a>
                    </tf:forRoles>
                    <tf:forRoles roles="${['speaker']}">
                        <a href="#topic-proposal" class="btn waves-effect waves-light modal-trigger">
                            <taglib:message value="propose" />
                            <i class="material-icons right">local_offer</i>
                        </a>
                    </tf:forRoles>
                </div>
            </div>
            <div class="separator mb10 mt5"></div>

            <c:choose>
                <c:when test="${not empty meeting.reportTopics}">

                    <table class="striped highlight z-depth-1">
                        <thead>
                            <tr>
                                <th class="center-align">#</th>
                                <th><taglib:message value="speaker" /></th>
                                <th><taglib:message value="topic" /></th>
                                <tf:forRoles roles="${['moderator']}">
                                    <th></th>
                                </tf:forRoles>
                            </tr>
                        </thead>
                        <tbody>
                            <c:forEach items="${meeting.reportTopics}" var="topic" varStatus="loopStatus">
                                <tr class="topic-item" data-topic-id="${topic.id}" data-report-topic-speaker-id="${not empty topic.reportTopicSpeaker ? topic.reportTopicSpeaker.id : ''}">
                                    <td class="center-align">${loopStatus.index + 1}</td>
                                    <td>
                                        <c:choose>
                                            <c:when test="${not empty topic.reportTopicSpeaker}">
                                                <div class="s-hflex" data-speaker-id="${topic.reportTopicSpeaker.speakerId}">
                                                    <div class="z-depth-1 user-avatar stretch-background">
                                                        <img src="/resources/images/avatars/<c:out value="${topic.reportTopicSpeaker.speaker.imagePath}" />" alt="" class="full-width full-height" data-errorKey="avatarDefault" />
                                                    </div>
                                                    <div class="s-vflex-center px10 weight-normal">
                                                        <c:out value="${topic.reportTopicSpeaker.speaker.name}" /> <c:out value="${topic.reportTopicSpeaker.speaker.surname}" />
                                                    </div>
                                                </div>
                                            </c:when>
                                            <c:otherwise>
                                                <div class="red-text weight-strong s-hflex">
                                                    <span class="px10 s-vflex-center">
                                                        <taglib:message value="no_speaker" />
                                                    </span>

                                                    <tf:forRoles roles="${['speaker']}">
                                                        <c:choose>
                                                            <c:when test="${fn:contains(proposedTopicIds, topic.id)}">
                                                                <span class="grey-text text-lighten-1 weight-normal">
                                                                    <taglib:message value="you_proposed_yourself" />
                                                                </span>
                                                            </c:when>
                                                            <c:otherwise>
                                                                <form action="<taglib:linkTo href="/topics/propose-speaker" />" method="post" class="m0">
                                                                    <input type="hidden" name="meeting_id" value="${meeting.id}" />
                                                                    <input type="hidden" name="report_topic_id" value="${topic.id}" />
                                                                    <button type="submit" class="btn-floating orange tooltipped" data-position="left" data-tooltip="<taglib:message value="propose_me" />">
                                                                        <i class="material-icons">assignment_ind</i>
                                                                    </button>
                                                                </form>
                                                            </c:otherwise>
                                                        </c:choose>
                                                    </tf:forRoles>

                                                    <tf:forRoles roles="${['moderator']}">
                                                        <a href="#topic-proposals-form" class="modal-trigger proposalsSearchTrigger weight-slight" data-topic-id="${topic.id}">
                                                            (<taglib:message value="select_from_proposals" />)
                                                        </a>
                                                    </tf:forRoles>
                                                </div>
                                            </c:otherwise>
                                        </c:choose>
                                    </td>
                                    <td>
                                        <span data-title><c:out value="${topic.title}" /></span>
                                    </td>
                                    <tf:forRoles roles="${['moderator']}">
                                        <td class="px20">
                                            <div class="s-hflex-end">
                                                <c:if test="${empty topic.reportTopicSpeaker}">
                                                    <span class="clickable proposalTrigger modal-trigger tooltipped px5" data-target="propose-to-speakers-form" data-position="bottom" data-tooltip="<taglib:message value="propose_to_speakers" />">
                                                        <i class="material-icons orange-text">person_add</i>
                                                    </span>
                                                </c:if>
                                                <span class="clickable topicTrigger modal-trigger blue-text text-darken-3 tooltipped" data-target="topic-form" data-position="bottom" data-tooltip="<taglib:message value="edit" />">
                                                    <i class="material-icons">edit</i>
                                                </span>
                                            </div>
                                        </td>
                                    </tf:forRoles>
                                </tr>
                            </c:forEach>
                        </tbody>
                    </table>

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

<tf:forRoles roles="${['moderator']}">
    <jsp:include page="/WEB-INF/jsp/components/modals/new-topic-form.jsp">
        <jsp:param name="meeting" value="${meeting}" />
        <jsp:param name="speakers" value="${speakers}" />
    </jsp:include>

    <jsp:include page="/WEB-INF/jsp/components/modals/propose-to-speakers-form.jsp" />

    <jsp:include page="/WEB-INF/jsp/components/modals/topic-proposals-form.jsp" />

    <c:if test="${stats.totalUsersCount > 0}">
        <jsp:include page="/WEB-INF/jsp/components/modals/presence-editor.jsp">
            <jsp:param name="stats" value="${stats}" />
        </jsp:include>
    </c:if>
</tf:forRoles>

<tf:forRoles roles="${['speaker']}">
    <jsp:include page="/WEB-INF/jsp/components/modals/add-topic-proposal-form.jsp" />
</tf:forRoles>