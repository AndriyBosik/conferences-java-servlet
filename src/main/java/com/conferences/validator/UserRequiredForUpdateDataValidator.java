package com.conferences.validator;

import com.conferences.config.ErrorKey;
import com.conferences.entity.User;
import com.conferences.model.FormError;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

public class UserRequiredForUpdateDataValidator extends AbstractValidator<User> {

    private static final String EMAIL_REGEX = "^[\\w-\\.]+@([\\w-]+\\.)+[\\w-]{2,4}$";
    private static final Pattern EMAIL_PATTERN = Pattern.compile(EMAIL_REGEX);
    private static final String LOGIN = "login";
    private static final String SURNAME = "surname";
    private static final String NAME = "name";
    private static final String EMAIL = "email";

    @Override
    public List<FormError> validate(User model) {
        List<FormError> formErrors = new ArrayList<>();
        addIfNotRequired(formErrors, model.getLogin(), LOGIN);
        addIfNotHasMinimumLength(formErrors, model.getLogin(), 4, LOGIN);

        addIfNotRequired(formErrors, model.getSurname(), SURNAME);
        addIfNotHasMinimumLength(formErrors, model.getSurname(), 2, SURNAME);

        addIfNotRequired(formErrors, model.getName(), NAME);
        addIfNotHasMinimumLength(formErrors, model.getName(), 2, NAME);

        addIfNotRequired(formErrors, model.getEmail(), EMAIL);
        if (!isCorrectEmail(model.getEmail())) {
            formErrors.add(new FormError(ErrorKey.INVALID_EMAIL));
        }

        return formErrors;
    }

    private boolean isCorrectEmail(String email) {
        return EMAIL_PATTERN.matcher(email).matches();
    }
}
