package com.conferences.validator;

import com.conferences.entity.Meeting;
import com.conferences.model.FormError;

import java.util.ArrayList;
import java.util.List;

public class MeetingEditableDataValidator extends AbstractValidator<Meeting> {
    private static final String ADDRESS = "address";
    private static final String DATE = "date";

    @Override
    public List<FormError> validate(Meeting model) {
        List<FormError> formErrors = new ArrayList<>();
        addIfNotRequired(formErrors, model.getAddress(), ADDRESS);
        addIfNotHasMinimumLength(formErrors, model.getAddress(), 5, ADDRESS);
        addIfDateIsNotAfterNow(formErrors, model.getDate(), DATE);
        return formErrors;
    }
}
