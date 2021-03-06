package com.conferences.config;

public enum Page {
    HOME("/"),
    LOGIN("/home/login"),
    LOGOUT("/home/logout"),
    PROFILE("/home/profile"),
    SPEAKER_PROPOSALS_PAGE("/home/proposals"),
    SPEAKER_MEETINGS_DEFAULT_PAGE("/home/speaker-meetings"),
    SPEAKER_MEETINGS("/home/speaker-meetings/*"),
    MEETINGS_CREATE("/meetings/create"),
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
    PROPOSE_TOPIC("/topics/propose"),
    PROPOSE_SPEAKER_TO_TOPIC("/topics/propose-speaker"),
    PROPOSED_TOPICS("/topics/speaker-proposed"),
    SAVE_USERS_PRESENCE("/meetings-api/save-user-presence"),
    ACCEPT_PROPOSED_TOPIC("/topics/accept-proposed"),
    REJECT_PROPOSED_TOPIC("/topics/reject-proposed"),
    SET_SPEAKER_FROM_PROPOSALS("/topics/set-speaker-from-proposals"),
    SIGN_UP_USER("/users/sign-up"),
    CHANGE_USER_AVATAR("/users/change-avatar"),
    UPDATE_USER_PROFILE("/users/update-profile"),
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
    API_TOPIC_PROPOSE_FOR_USER("/topics-api/propose-topic-for-speaker"),
    REJECT_PROPOSAL("/proposals/reject-proposal");

    protected final String url;

    Page(String url) {
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
