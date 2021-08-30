package com.conferences.config;

public enum MeetingSortOption {
    NOTHING,
    DATE,
    USERS,
    TOPICS;

    public static MeetingSortOption fromString(String value) {
        try {
            return MeetingSortOption.valueOf(value.toUpperCase());
        } catch (IllegalArgumentException exception) {
            return NOTHING;
        }
    }
}
