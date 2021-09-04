package com.conferences.validator;

import com.conferences.model.FormError;

import java.util.List;

public interface IValidator<T> {

    List<FormError> validate(T model);

}
