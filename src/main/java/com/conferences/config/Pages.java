package com.conferences.config;

public enum Pages {
    HOME("/"),
    LOGIN("/login"),
    PROFILE("/profile");

    private String url;

    Pages(String url) {
        this.url = url;
    }

    @Override
    public String toString() {
        return url;
    }
}
