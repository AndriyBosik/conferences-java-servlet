package com.conferences.validator;

import com.conferences.config.ErrorKey;
import com.conferences.factory.HandlerFactory;
import com.conferences.handler.abstraction.IFieldValidationHandler;
import com.conferences.model.FormError;

import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

public abstract class AbstractValidator<T> implements IValidator<T> {

    protected IFieldValidationHandler fieldValidationHandler;

    protected AbstractValidator() {
        fieldValidationHandler = HandlerFactory.getInstance().getFieldValidationHandler();
    }

    protected void addIfNotRequired(List<FormError> errors, String value, String... params) {
        if (!fieldValidationHandler.checkValueIsRequired(value)) {
            errors.add(new FormError(ErrorKey.REQUIRED_FIELD, params));
        }
    }

    protected void addIfNotHasMinimumLength(List<FormError> errors, String value, int minLength, String... params) {
        if (!fieldValidationHandler.checkStringMinLength(value, minLength)) {
            List<String> paramsList = new ArrayList<>(Arrays.asList(params));
            paramsList.add(minLength + "");
            errors.add(new FormError(ErrorKey.FIELD_MINIMUM_LENGTH, paramsList.toArray(new String[0])));
        }
    }

    protected void addIfDateIsNotAfterNow(List<FormError> errors, LocalDateTime dateTime, String... params) {
        if (!fieldValidationHandler.checkDateIsAfterNow(dateTime)) {
            errors.add(new FormError(ErrorKey.FUTURE_DATE, params));
        }
    }
}
