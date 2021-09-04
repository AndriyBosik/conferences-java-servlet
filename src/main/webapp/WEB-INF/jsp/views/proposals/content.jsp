<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="tags" prefix="taglib" %>
<%@ taglib prefix="tf" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<div class="container">
    <div class="row">
        <div class="col s12">
            <h4 class="grey-text text-darken-2 mb0"><taglib:message value="page.title.proposals" /></h4>
            <hr />
        </div>
        <div class="col s12">
            <tf:errors errors="${rejectionErrors}" />
            <tf:errors errors="${proposalErrors}" />
        </div>
        <div class="col s12">
            <ul class="tabs m-blue m-darken-3">
                <li class="tab col s6">
                    <a href="#my" class="blue-text text-darken-3">My</a>
                </li>
                <li class="tab col s6">
                    <a href="#for-me" class="blue-text text-darken-3">For me</a>
                </li>
                <li class="indicator"></li>
            </ul>
        </div>
        <div id="my" class="col s12">
            <div class="py10">
                <ul class="collection speaker-proposals-collection">
                    <c:set var="lastMeetingId" value="0" />
                    <c:forEach items="${speakerProposals}" var="proposal">
                        <c:if test="${lastMeetingId != proposal.meetingId}">
                            <c:set var="lastMeetingId" value="${proposal.meetingId}" />
                            <li class="collection-item s-hflex">
                                <a href="<taglib:linkTo href="/meetings/show/${proposal.meetingId}" />" class="full-width s-hflex grey-text text-darken-3">
                                    <div class="s-vflex-center">
                                        <span class="weight-normal" style="font-size: 22px;"><c:out value="${proposal.meetingTitle}" /></span>
                                    </div>
                                    <div class="s-vflex-center mx10">
                                        <div class="circle stretch-background user-avatar z-depth-1" style="background-image: url('/resources/images/meetings/<c:out value="${proposal.meetingImagePath}" />')"></div>
                                    </div>
                                    <div class="translucent s-hflex-end equal-flex">
                                        <div class="s-vflex-center fs20">
                                            <taglib:dateFormatter date="${proposal.meetingDate}" format="dd-MM-yyyy HH:mm" />
                                        </div>
                                    </div>
                                </a>
                            </li>
                        </c:if>

                        <li class="collection-item s-hflex">
                            <div class="collection-content mx20 equal-flex s-vflex-center py10">
                                <span class="topic-title weight-normal"><c:out value="${proposal.reportTopicTitle}" /></span>
                            </div>
                            <span class="secondary-content s-vflex-center">
                                <i class="material-icons small blue-text text-darken-3 tooltipped" data-position="left" data-tooltip="<taglib:message value="processing" />">hourglass_bottom</i>
                            </span>
                        </li>
                    </c:forEach>
                </ul>
            </div>
        </div>
        <div id="for-me" class="col s12">
            <div class="py10">
                <ul class="collection speaker-proposals-collection">
                    <c:set var="lastMeetingId" value="0" />
                    <c:forEach items="${moderatorProposals}" var="proposal">
                        <c:if test="${lastMeetingId != proposal.meetingId}">
                            <c:set var="lastMeetingId" value="${proposal.meetingId}" />
                            <li class="collection-item s-hflex">
                                <a href="<taglib:linkTo href="/meetings/show/${proposal.meetingId}" />" class="full-width s-hflex grey-text text-darken-3">
                                    <div class="s-vflex-center">
                                        <span class="weight-normal" style="font-size: 22px;"><c:out value="${proposal.meetingTitle}" /></span>
                                    </div>
                                    <div class="s-vflex-center mx10">
                                        <div class="circle stretch-background user-avatar z-depth-1" style="background-image: url('/resources/images/meetings/<c:out value="${proposal.meetingImagePath}" />')"></div>
                                    </div>
                                    <div class="translucent s-hflex-end equal-flex">
                                        <div class="s-vflex-center fs20">
                                            <taglib:dateFormatter date="${proposal.meetingDate}" format="dd-MM-yyyy HH:mm" />
                                        </div>
                                    </div>
                                </a>
                            </li>
                        </c:if>

                        <li class="collection-item s-hflex">
                            <div class="collection-content mx20 equal-flex s-vflex-center py10">
                                <span class="topic-title weight-normal"><c:out value="${proposal.reportTopicTitle}" /></span>
                            </div>
                            <span class="secondary-content s-vflex-center">
                                <div class="s-hflex">
                                    <form action="<taglib:linkTo href="/topics/set-speaker-from-proposals" />" method="post" class="m0">
                                        <input type="hidden" name="topic_id" value="${proposal.reportTopicId}" />
                                        <button type="submit" class="btn waves-effect waves-light mx10">
                                            <span><taglib:message value="accept"/></span>
                                            <i class="material-icons right">check</i>
                                        </button>
                                    </form>
                                    <form action="<taglib:linkTo href="/proposals/reject-proposal" />" method="post" class="m0">
                                        <input type="hidden" name="topic_id" value="${proposal.reportTopicId}" />
                                        <button type="submit" class="btn waves-effect waves-light red">
                                            <span><taglib:message value="reject" /></span>
                                            <i class="material-icons right">clear</i>
                                        </button>
                                    </form>
                                </div>
                            </span>
                        </li>
                    </c:forEach>
                </ul>
            </div>
        </div>
    </div>
</div>