package com.conferences.service.implementation;

import com.conferences.dao.abstraction.ITopicProposalDao;
import com.conferences.entity.ReportTopic;
import com.conferences.entity.TopicProposal;
import com.conferences.factory.DaoFactory;
import com.conferences.factory.MapperFactory;
import com.conferences.factory.ValidatorFactory;
import com.conferences.mapper.IMapper;
import com.conferences.service.abstraction.ITopicProposalService;
import com.conferences.validator.IValidator;

import java.util.List;

public class TopicProposalService implements ITopicProposalService {

    private final ITopicProposalDao topicProposalDao;
    private final IMapper<TopicProposal, ReportTopic> mapper;
    private final IValidator<ReportTopic> validator;

    public TopicProposalService() {
        topicProposalDao = DaoFactory.getInstance().getTopicProposalDao();
        mapper = MapperFactory.getInstance().getTopicProposalToReportTopicMapper();
        validator = ValidatorFactory.getInstance().getReportTopicValidator();
    }

    @Override
    public boolean addTopicProposal(TopicProposal topicProposal) {
        if (validator.validate(mapper.map(topicProposal)) != null) {
            return false;
        }
        return topicProposalDao.create(topicProposal);
    }

    @Override
    public boolean rejectTopicProposal(int topicProposalId) {
        return topicProposalDao.delete(topicProposalId);
    }

    @Override
    public boolean acceptTopicProposal(int topicProposalId) {
        return topicProposalDao.createReportTopicWithProposalDeletion(topicProposalId);
    }

    @Override
    public List<TopicProposal> getAllByMeetingWithSpeakers(int meetingId) {
        return topicProposalDao.findAllByMeetingIdWithSpeakers(meetingId);
    }

    @Override
    public int getProposedTopicsCount() {
        return topicProposalDao.getRecordsCount();
    }
}
