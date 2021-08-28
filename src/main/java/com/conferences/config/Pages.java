package com.conferences.config;

public enum Pages {
    HOME("/"),
    LOGIN("/home/login"),
    LOGOUT("/home/logout"),
    PROFILE("/home/profile"),
    MEETINGS_LIST("/meetings/all"),
    MEETINGS_LIST_PAGE("/meetings/all/*"),
    MEETING("/meetings/show/"){
        @Override
        public String toString() {
            return url + "*";
        }
    },
    TOPIC("/topics/show/*"),
    CREATE_TOPIC("/topics/create"),
    UPDATE_TOPIC("/topics/update"),
    JOIN_USER_TO_MEETING("/meetings/join-user");

    protected final String url;

    Pages(String url) {
        this.url = url;
    }

    public String getUrl() {
        return url;
    }

    @Override
    public String toString() {
        return url;
    }
}
