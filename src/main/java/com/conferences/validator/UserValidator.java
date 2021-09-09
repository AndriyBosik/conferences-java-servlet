package com.conferences.validator;

import com.conferences.entity.User;
import com.conferences.model.FormError;

import java.util.List;

/**
 * {@inheritDoc}
 */
public class UserValidator extends UserRequiredForUpdateDataValidator {

    private static final String PASSWORD = "password";

    /**
     * {@inheritDoc}
     */
    @Override
    public List<FormError> validate(User model) {
        List<FormError> formErrors = super.validate(model);
        addIfNotRequired(formErrors, model.getPassword(), PASSWORD);
        addIfNotHasMinimumLength(formErrors, model.getPassword(), 5, PASSWORD);
        return formErrors;
    }
}
