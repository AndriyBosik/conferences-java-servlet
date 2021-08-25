package com.conferences.config;

public enum Pages {
    HOME("/"),
    LOGIN("/home/login"),
    LOGOUT("/home/logout"),
    PROFILE("/home/profile"),
    CONFERENCES_LIST("/meetings/all");

    private final String url;

    Pages(String url) {
        this.url = url;
    }

    @Override
    public String toString() {
        return url;
    }
}
