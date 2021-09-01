package com.conferences.service.implementation;

import com.conferences.dao.abstraction.ITopicProposalDao;
import com.conferences.dao.implementation.TopicProposalDao;
import com.conferences.entity.ReportTopic;
import com.conferences.entity.TopicProposal;
import com.conferences.mapper.IMapper;
import com.conferences.mapper.TopicProposalToReportTopicMapper;
import com.conferences.service.abstraction.ITopicProposalService;
import com.conferences.validator.IValidator;
import com.conferences.validator.ReportTopicValidator;

import java.util.ArrayList;
import java.util.List;

public class TopicProposalService implements ITopicProposalService {

    private final ITopicProposalDao topicProposalDao;
    private final IMapper<TopicProposal, ReportTopic> mapper;
    private final IValidator<ReportTopic> validator;

    public TopicProposalService() {
        topicProposalDao = new TopicProposalDao();
        mapper = new TopicProposalToReportTopicMapper();
        validator = new ReportTopicValidator();
    }

    @Override
    public boolean addTopicProposal(TopicProposal topicProposal) {
        if (!validator.isValid(mapper.map(topicProposal))) {
            return false;
        }
        return topicProposalDao.create(topicProposal);
    }

    @Override
    public boolean rejectTopicProposal(TopicProposal topicProposal) {
        return topicProposalDao.delete(topicProposal.getId());
    }

    @Override
    public boolean acceptTopicProposal(TopicProposal topicProposal) {
        return topicProposalDao.createReportTopicWithProposalDeletion(topicProposal);
    }

    @Override
    public List<TopicProposal> getAllByMeetingWithSpeakers(int meetingId) {
        return topicProposalDao.findAllByMeetingIdWithSpeakers(meetingId);
    }
}
