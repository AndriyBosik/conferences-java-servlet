package com.conferences.config;

public enum MeetingFilterSelector {
    ALL,
    PAST,
    FUTURE;

    public static MeetingFilterSelector fromString(String value) {
        try {
            return MeetingFilterSelector.valueOf(value.toUpperCase());
        } catch (IllegalArgumentException exception) {
            return ALL;
        }
    }
}
