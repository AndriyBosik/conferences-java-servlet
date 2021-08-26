package com.conferences.validator;

public interface IValidator<T> {

    boolean isValid(T model);

}
