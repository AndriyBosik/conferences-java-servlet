package com.conferences.config;

public enum Defaults {
    LANG("en");

    private String defaultValue;

    Defaults(String defaultValue) {
        this.defaultValue = defaultValue;
    }

    @Override
    public String toString() {
        return defaultValue;
    }
}
