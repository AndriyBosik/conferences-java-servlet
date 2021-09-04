package com.conferences.service.implementation;

import com.conferences.config.ErrorKey;
import com.conferences.dao.abstraction.IReportTopicDao;
import com.conferences.dao.abstraction.IReportTopicSpeakerDao;
import com.conferences.entity.ReportTopic;
import com.conferences.entity.ReportTopicSpeaker;
import com.conferences.factory.DaoFactory;
import com.conferences.factory.ValidatorFactory;
import com.conferences.model.FormError;
import com.conferences.service.abstraction.IReportTopicService;
import com.conferences.validator.IValidator;

import java.util.List;

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
    public List<FormError> updateTopicWithSpeaker(ReportTopic reportTopic) {
        List<FormError> errors = reportTopicValidator.validate(reportTopic);
        if (errors.isEmpty() && !reportTopicDao.updateWithSpeaker(reportTopic)) {
            errors.add(new FormError(ErrorKey.UPDATING_ERROR));
        }
        return errors;
    }

    @Override
    public boolean setSpeakerForTopic(ReportTopicSpeaker reportTopicSpeaker) {
        return reportTopicSpeakerDao.saveWithProposalsDeletionTable(reportTopicSpeaker);
    }

    @Override
    public List<FormError> saveWithSpeaker(ReportTopic reportTopic) {
        List<FormError> errors = reportTopicValidator.validate(reportTopic);
        if (errors.isEmpty() && !reportTopicDao.saveWithSpeaker(reportTopic)) {
            errors.add(new FormError(ErrorKey.CREATION_ERROR));
        }
        return errors;
    }
}
