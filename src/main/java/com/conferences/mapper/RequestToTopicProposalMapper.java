package com.conferences.mapper;

import com.conferences.config.Defaults;
import com.conferences.entity.TopicProposal;
import com.conferences.entity.User;
import com.conferences.handler.abstraction.IEncodingHandler;
import com.conferences.handler.implementation.EncodingHandler;

import javax.servlet.http.HttpServletRequest;

public class RequestToTopicProposalMapper implements IMapper<HttpServletRequest, TopicProposal> {

    private final IEncodingHandler encodingHandler;

    public RequestToTopicProposalMapper() {
        encodingHandler = new EncodingHandler();
    }

    @Override
    public TopicProposal map(HttpServletRequest request) {
        User speaker = (User) request.getSession().getAttribute(Defaults.USER.toString());
        TopicProposal topicProposal = new TopicProposal();
        topicProposal.setSpeakerId(speaker.getId());
        topicProposal.setMeetingId(Integer.parseInt(request.getParameter("meeting_id")));
        topicProposal.setTopicTitle(encodingHandler.getUTF8ValueFromRequest(request, "topic_title"));
        return topicProposal;
    }
}
