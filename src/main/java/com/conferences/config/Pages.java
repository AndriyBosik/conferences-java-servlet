package com.conferences.config;

public enum Pages {
    HOME("/"),
    LOGIN("/login"),
    LOGOUT("/logout"),
    PROFILE("/profile");

    private final String url;

    Pages(String url) {
        this.url = url;
    }

    @Override
    public String toString() {
        return url;
    }
}
