package com.conferences.config;

public enum Pages {
    HOME("/"),
    LOGIN("/home/login"),
    LOGOUT("/home/logout"),
    PROFILE("/home/profile"),
    MEETINGS_LIST("/meetings/all"),
    MEETINGS_LIST_PAGE("/meetings/all/*"),
    MEETING("/meetings/show/*"),
    TOPIC("/topics/show/*");

    private final String url;

    Pages(String url) {
        this.url = url;
    }

    @Override
    public String toString() {
        return url;
    }
}
