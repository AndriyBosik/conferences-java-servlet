package com.conferences.validator;

import com.conferences.entity.ReportTopic;
import com.conferences.model.FormError;

import java.util.ArrayList;
import java.util.List;

public class ReportTopicValidator extends AbstractValidator<ReportTopic> {

    private static final String TITLE = "title";

    @Override
    public List<FormError> validate(ReportTopic model) {
        List<FormError> formErrors = new ArrayList<>();
        addIfNotRequired(formErrors, model.getTitle(), TITLE);
        addIfNotHasMinimumLength(formErrors, model.getTitle(), 5, TITLE);
        return formErrors;
    }
}
