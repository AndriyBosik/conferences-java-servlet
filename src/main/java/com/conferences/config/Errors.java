package com.conferences.config;

import com.conferences.handler.abstraction.IPropertiesHandler;
import com.conferences.handler.implementation.PropertiesHandler;

public enum Errors {
    OK(""),
    INVALID_LOGIN_OR_PASSWORD("errors.invalid_login_or_password"),
    PASSWORDS_ARE_NOT_EQUAL("errors.passwords_are_not_equal"),
    INVALID_OLD_PASSWORD("errors.invalid_old_password");

    private final String key;
    private final IPropertiesHandler propertiesHandler;

    Errors(String key) {
        this.key = key;
        propertiesHandler = new PropertiesHandler();
    }

    public String getByLang(String lang) {
        return propertiesHandler.getPropertyValue("messages", lang, "errors.invalid_login_or_password");
    }

}
