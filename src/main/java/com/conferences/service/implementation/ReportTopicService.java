package com.conferences.service.implementation;

import com.conferences.dao.abstraction.IReportTopicDao;
import com.conferences.dao.abstraction.IReportTopicSpeakerDao;
import com.conferences.dao.implementation.ReportTopicDao;
import com.conferences.dao.implementation.ReportTopicSpeakerDao;
import com.conferences.entity.ReportTopic;
import com.conferences.entity.ReportTopicSpeaker;
import com.conferences.factory.DaoFactory;
import com.conferences.factory.ValidatorFactory;
import com.conferences.service.abstraction.IReportTopicService;
import com.conferences.validator.IValidator;
import com.conferences.validator.ReportTopicValidator;

public class ReportTopicService implements IReportTopicService {

    private final IReportTopicDao reportTopicDao;
    private final IReportTopicSpeakerDao reportTopicSpeakerDao;
    private final IValidator<ReportTopic> reportTopicValidator;

    public ReportTopicService() {
        reportTopicDao = DaoFactory.getInstance().getReportTopicDao();
        reportTopicSpeakerDao = DaoFactory.getInstance().getReportTopicSpeakerDao();
        reportTopicValidator = ValidatorFactory.getInstance().getReportTopicValidator();
    }

    @Override
    public boolean save(ReportTopic reportTopic) {
        if (reportTopicValidator.isValid(reportTopic)) {
            return reportTopicDao.create(reportTopic);
        }
        return false;
    }

    @Override
    public boolean updateTopicWithSpeaker(ReportTopic reportTopic) {
        if (reportTopicValidator.isValid(reportTopic)) {
            return reportTopicDao.updateWithSpeaker(reportTopic);
        }
        return false;
    }

    @Override
    public boolean setSpeakerForTopic(ReportTopicSpeaker reportTopicSpeaker) {

        return reportTopicSpeakerDao.saveWithProposalsDeletionTable(reportTopicSpeaker);
    }

    @Override
    public boolean saveWithSpeaker(ReportTopic reportTopic) {
        return reportTopicDao.saveWithSpeaker(reportTopic);
    }
}
