package com.conferences.validator;

import com.conferences.entity.ReportTopic;

public class ReportTopicValidator implements IValidator<ReportTopic> {

    @Override
    public boolean isValid(ReportTopic model) {
        return  model.getTitle() != null && model.getTitle().length() >= 5 &&
                model.getMeetingId() > 0;
    }
}
