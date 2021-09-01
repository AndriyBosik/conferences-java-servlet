package com.conferences.mapper;

import com.conferences.config.Defaults;
import com.conferences.entity.TopicProposal;
import com.conferences.entity.User;

import javax.servlet.http.HttpServletRequest;

public class RequestToTopicProposalMapper implements IMapper<HttpServletRequest, TopicProposal> {

    @Override
    public TopicProposal map(HttpServletRequest request) {
        User speaker = (User) request.getSession().getAttribute(Defaults.USER.toString());
        TopicProposal topicProposal = new TopicProposal();
        topicProposal.setSpeakerId(speaker.getId());
        topicProposal.setMeetingId(Integer.parseInt(request.getParameter("meeting_id")));
        topicProposal.setTopicTitle(request.getParameter("topic_title"));
        return topicProposal;
    }
}
