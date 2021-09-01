package com.conferences.config;

public enum Pages {
    HOME("/"),
    LOGIN("/home/login"),
    LOGOUT("/home/logout"),
    PROFILE("/home/profile"),
    SPEAKER_PROPOSALS_PAGE("/home/proposals"),
    SPEAKER_MEETINGS_DEFAULT_PAGE("/home/speaker-meetings"),
    SPEAKER_MEETINGS("/home/speaker-meetings/*"),
    MEETINGS_LIST("/meetings/all"),
    MEETINGS_LIST_PAGE("/meetings/all/*"),
    JOIN_USER_TO_MEETING("/meetings/join-user"),
    MEETING_EDIT("/meetings/edit"),
    MEETING("/meetings/show/") {
        @Override
        public String toString() {
            return url + "*";
        }
    },
    CREATE_TOPIC("/topics/create"),
    UPDATE_TOPIC("/topics/update"),
    PROPOSE_SPEAKER_TO_TOPIC("/topics/propose-speaker"),
    SET_SPEAKER_FROM_PROPOSALS("/topics/set-speaker-from-proposals"),
    SIGN_UP_USER("/users/sign-up"),
    API_TOPIC_PROPOSALS("/topics-api/get-topic-proposals/") {
        @Override
        public String toString() {
            return url + "*";
        }
    },
    API_TOPIC_AVAILABLE_SPEAKERS_FOR_PROPOSAL("/topics-api/get-available-speakers-for-proposal/") {
        @Override
        public String toString() {
            return url + "*";
        }
    },
    API_TOPIC_PROPOSE_FOR_USER("/topics-api/propose-topic-for-user"),
    REJECT_PROPOSAL("/proposals/reject-proposal");

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
