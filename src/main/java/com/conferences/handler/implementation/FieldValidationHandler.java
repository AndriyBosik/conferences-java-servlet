package com.conferences.handler.implementation;

import com.conferences.handler.abstraction.IFieldValidationHandler;

import java.time.LocalDateTime;

public class FieldValidationHandler implements IFieldValidationHandler {

    @Override
    public boolean checkValueIsRequired(String value) {
        return value != null && !value.trim().isEmpty();
    }

    @Override
    public boolean checkStringMinLength(String value, int minValue) {
        return value != null && value.trim().length() >= minValue;
    }

    @Override
    public boolean checkDateIsAfterNow(LocalDateTime dateTime) {
        return dateTime.isAfter(LocalDateTime.now());
    }
}
