package com.conferences.config;

import com.conferences.handler.PropertiesHandler;

public enum Errors {
    INVALID_LOGIN_OR_PASSWORD("errors.invalid_login_or_password");

    private final String key;
    private final PropertiesHandler propertiesHandler;

    Errors(String key) {
        this.key = key;
        propertiesHandler = new PropertiesHandler();
    }

    public String getByLang(String lang) {
        return propertiesHandler.getPropertyValue("messages", lang, "errors.invalid_login_or_password");
    }

}
