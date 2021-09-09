package com.conferences.validator;

import com.conferences.entity.Meeting;
import com.conferences.model.FormError;

import java.util.*;

/**
 * {@inheritDoc}
 */
public class MeetingValidator extends AbstractValidator<Meeting> {
    private static final String TITLE = "title";
    private static final String DESCRIPTION = "description";
    private static final String ADDRESS = "address";
    private static final String DATE = "date";
    private static final String IMAGE_PATH = "image_path";

    /**
     * {@inheritDoc}
     */
    @Override
    public List<FormError> validate(Meeting model) {
        List<FormError> formErrors = new ArrayList<>();
        addIfNotRequired(formErrors, model.getTitle(), TITLE);
        addIfNotHasMinimumLength(formErrors, model.getTitle(), 5, TITLE);
        addIfNotRequired(formErrors, model.getDescription(), DESCRIPTION);
        addIfNotHasMinimumLength(formErrors, model.getDescription(), 10, DESCRIPTION);
        addIfNotRequired(formErrors, model.getAddress(), ADDRESS);
        addIfNotHasMinimumLength(formErrors, model.getAddress(), 5, ADDRESS);
        addIfDateIsNotAfterNow(formErrors, model.getDate(), DATE);
        return formErrors;
    }
}
