package com.conferences.validator;

import com.conferences.model.FormError;

import java.util.List;

/**
 * <p>
 *     Defines function to validate model of T class
 * </p>
 * @param <T> - type of model to be validated
 *
 * @author Andriy
 * @version 1.0
 * @since 2021/09/09
 */
public interface IValidator<T> {

    /**
     * <p>
     *     Makes validation
     * </p>
     * @param model object to validate
     * @return list of errors or empty list if no errors were found
     */
    List<FormError> validate(T model);
}
