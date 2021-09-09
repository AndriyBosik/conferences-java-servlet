package com.conferences.handler.implementation;

import com.conferences.handler.abstraction.IFieldValidationHandler;

import java.time.LocalDateTime;

/**
 * {@inheritDoc}
 */
public class FieldValidationHandler implements IFieldValidationHandler {

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean checkValueIsRequired(String value) {
        return value != null && !value.trim().isEmpty();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean checkStringMinLength(String value, int minValue) {
        return value != null && value.trim().length() >= minValue;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean checkDateIsAfterNow(LocalDateTime dateTime) {
        return dateTime != null && dateTime.isAfter(LocalDateTime.now());
    }
}
