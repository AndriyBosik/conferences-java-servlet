package com.conferences.handler.abstraction;

import java.time.LocalDateTime;

/**
 * <p>
 *     Defines rules to validate values
 * </p>
 *
 * @author Andriy
 * @version 1.0
 * @since 2021/09/09
 */
public interface IFieldValidationHandler {

    /**
     * <p>
     *     Checks whatever string contains any characters
     * </p>
     * @param value value to be checked
     * @return true if string contains any characters, false otherwise
     */
    boolean checkValueIsRequired(String value);

    /**
     * <p>
     *     Checks whatever string contains at least {@code minValues} symbols
     * </p>
     * @param value string to be checked
     * @param minValue minimal length of string
     * @return true if string has at least {@code minValues} symbols
     */
    boolean checkStringMinLength(String value, int minValue);

    /**
     * <p>
     *     Checks whatever {@code dateTime} is after current time
     * </p>
     * @param dateTime value to be checked
     * @return true if {@code dateTime} is after current time, false otherwise
     */
    boolean checkDateIsAfterNow(LocalDateTime dateTime);
}
