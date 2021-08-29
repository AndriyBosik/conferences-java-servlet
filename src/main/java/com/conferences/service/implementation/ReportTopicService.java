package com.conferences.service.implementation;

import com.conferences.dao.abstraction.IReportTopicDao;
import com.conferences.dao.implementation.ReportTopicDao;
import com.conferences.entity.ReportTopic;
import com.conferences.service.abstraction.IReportTopicService;
import com.conferences.validator.IValidator;
import com.conferences.validator.ReportTopicValidator;

public class ReportTopicService implements IReportTopicService {

    private final IReportTopicDao reportTopicDao;
    private final IValidator<ReportTopic> reportTopicValidator;

    public ReportTopicService() {
        reportTopicDao = new ReportTopicDao();
        reportTopicValidator = new ReportTopicValidator();
    }

    @Override
    public boolean save(ReportTopic reportTopic) {
        if (reportTopicValidator.isValid(reportTopic)) {
            return reportTopicDao.create(reportTopic);
        }
        return false;
    }

    @Override
    public boolean update(ReportTopic reportTopic) {
        if (reportTopicValidator.isValid(reportTopic)) {
            return reportTopicDao.update(reportTopic);
        }
        return false;
    }

    @Override
    public boolean setSpeakerForTopic(int topicId, int speakerId) {
        return reportTopicDao.updateSpeakerIdForTopic(topicId, speakerId);
    }
}
