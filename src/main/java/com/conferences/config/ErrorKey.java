package com.conferences.config;

import com.conferences.handler.abstraction.IPropertiesHandler;
import com.conferences.handler.implementation.PropertiesHandler;

public enum ErrorKey {
    OK(""),
    INVALID_LOGIN_OR_PASSWORD("errors.invalid_login_or_password"),
    PASSWORDS_ARE_NOT_EQUAL("errors.passwords_are_not_equal"),
    INVALID_OLD_PASSWORD("errors.invalid_old_password"),
    REQUIRED_FIELD("errors.required_field"),
    FIELD_MINIMUM_LENGTH("errors.field_minimum_length"),
    FUTURE_DATE("errors.future_date"),
    IMAGE_LOADING_FAILED("errors.image_loading_failed"),
    CREATION_ERROR("errors.creation_error"),
    UPDATING_ERROR("errors.updating_error"),
    DELETION_ERROR("errors.deletion_error"),
    INVALID_EMAIL("errors.invalid_email"),
    EXISTING_USER("errors.existing_user"),
    INVALID_ROLE("errors.invalid_role"),
    REGISTRATION_ERROR("errors.registration_error"),
    EMPTY_PASSWORD_FIELD("errors.empty_password_field");

    private final String key;
    private final IPropertiesHandler propertiesHandler;

    ErrorKey(String key) {
        this.key = key;
        propertiesHandler = new PropertiesHandler();
    }

    public String getByLang(String lang) {
        return propertiesHandler.getPropertyValue("messages", lang, key);
    }

    @Override
    public String toString() {
        return key;
    }

}
