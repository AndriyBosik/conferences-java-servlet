package com.conferences.config;

import com.conferences.handler.abstraction.IPropertiesHandler;
import com.conferences.handler.implementation.PropertiesHandler;

public enum Errors {
    INVALID_LOGIN_OR_PASSWORD("errors.invalid_login_or_password");

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
