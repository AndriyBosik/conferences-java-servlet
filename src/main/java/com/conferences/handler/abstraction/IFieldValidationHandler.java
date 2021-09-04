package com.conferences.handler.abstraction;

import java.time.LocalDateTime;

public interface IFieldValidationHandler {

    boolean checkValueIsRequired(String value);

    boolean checkStringMinLength(String value, int minValue);

    boolean checkDateIsAfterNow(LocalDateTime dateTime);

}
