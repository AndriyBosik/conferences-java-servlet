package com.conferences.config;

public enum Defaults {
    DEFAULT_LANG("en"),
    CURRENT_LANG("__current_lang__"),
    DATE_FORMAT("dd-MM-yyyy HH:mm:ss");

    private String defaultValue;

    Defaults(String defaultValue) {
        this.defaultValue = defaultValue;
    }

    @Override
    public String toString() {
        return defaultValue;
    }
}
