package com.conferences.config;

public enum Defaults {
    DEFAULT_LANG("en"),
    CURRENT_LANG("currentLanguage"),
    CURRENT_LINK("currentLink"),
    USER("user"),
    DATE_FORMAT("dd-MM-yyyy HH:mm");

    private final String defaultValue;

    Defaults(String defaultValue) {
        this.defaultValue = defaultValue;
    }

    @Override
    public String toString() {
        return defaultValue;
    }
}
