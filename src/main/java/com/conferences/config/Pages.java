package com.conferences.config;

public enum Pages {
    HOME("/"),
    LOGIN("/home/login"),
    LOGOUT("/home/logout"),
    PROFILE("/home/profile"),
    MEETINGS_LIST("/meetings/all"),
    MEETINGS_LIST_PAGE("/meetings/all/*"),
    MEETING("/meetings/show/") {
        @Override
        public String toString() {
            return url + "*";
        }
    },
    TOPIC("/topics/show/*"),
    CREATE_TOPIC("/topics/create"),
    UPDATE_TOPIC("/topics/update"),
    PROPOSE_SPEAKER_TO_TOPIC("/topics/propose-speaker"),
    SET_SPEAKER_FROM_PROPOSALS("/topics/set-speaker-from-proposals"),
    JOIN_USER_TO_MEETING("/meetings/join-user"),
    SIGN_UP_USER("/users/sign-up"),
    API_TOPIC_PROPOSALS("/topics-api/get-topic-proposals/") {
        @Override
        public String toString() {
            return url + "*";
        }
    };

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
