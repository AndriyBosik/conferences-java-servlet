<%@ taglib uri="tags" prefix="taglib" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="tf" tagdir="/WEB-INF/tags" %>

<div class="container">
    <div class="row">
        <div class="col s12">
            <div class="s-hflex">
                <div class="equal-flex">
                    <h4 class="grey-text text-darken-2 mb0"><taglib:message value="proposed_topics" /></h4>
                </div>
            </div>
            <hr />
        </div>
        <div class="col s12 py10">
            <ul class="collection speaker-proposals-collection">
                <c:set var="lastMeetingId" value="0" />
                <c:forEach items="${proposedTopics}" var="topic">
                    <c:if test="${lastMeetingId != topic.meetingId}">
                        <c:set var="lastMeetingId" value="${topic.meetingId}" />
                        <li class="collection-item s-hflex">
                            <a href="<taglib:linkTo href="/meetings/show/${topic.meetingId}" />" class="full-width s-hflex grey-text text-darken-3">
                                <div class="s-vflex-center">
                                    <span class="weight-normal" style="font-size: 22px;">${topic.meetingTitle}</span>
                                </div>
                                <div class="s-vflex-center mx10">
                                    <div class="circle stretch-background user-avatar z-depth-1" style="background-image: url('/resources/images/meetings/${topic.meetingImagePath}')"></div>
                                </div>
                                <div class="translucent s-hflex-end equal-flex">
                                    <div class="s-vflex-center fs20">
                                        <fmt:formatDate value="${topic.meetingDate}" pattern="dd-MM-yyyy HH:mm" />
                                    </div>
                                </div>
                            </a>
                        </li>
                    </c:if>

                    <li class="collection-item s-hflex">
                        <div class="z-depth-1 user-avatar stretch-background">
                            <img src="/resources/images/avatars/${topic.speakerImagePath}" alt="" class="circle full-width full-height" />
                        </div>
                        <div class="collection-content mx20 equal-flex s-vflex-center py10">
                            <div class="s-hflex">
                                <span><taglib:message value="speaker" /></span>
                                <span class="topic-title weight-normal px5">${topic.speakerName} ${topic.speakerSurname}</span>
                                <span class="lowercase"><taglib:message value="speaker_proposed_topic" /></span>
                                <span class="topic-title weight-normal px5">${topic.proposedTopicTitle}</span>
                            </div>
                        </div>
                        <div class="secondary-content s-vflex-center">
                            <div class="s-hflex">
                                <form action="<taglib:linkTo href="/topics/accept-proposed" />" method="post" class="m0">
                                    <input type="hidden" name="id" value="${topic.id}" />
                                    <button type="submit" class="btn waves-effect waves-light mx10">
                                        <span><taglib:message value="accept"/></span>
                                        <i class="material-icons right">check</i>
                                    </button>
                                </form>
                                <form action="<taglib:linkTo href="/topics/reject-proposed" />" method="post" class="m0">
                                    <input type="hidden" name="id" value="${topic.id}" />
                                    <button type="submit" class="btn waves-effect waves-light red">
                                        <span><taglib:message value="reject" /></span>
                                        <i class="material-icons right">clear</i>
                                    </button>
                                </form>
                            </div>
                        </div>
                    </li>
                </c:forEach>
            </ul>
        </div>
    </div>
</div>