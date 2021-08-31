package com.conferences.config;

public enum Roles {
    MODERATOR("moderator"),
    SPEAKER("speaker"),
    USER("user");

    String title;

    Roles(String title) {
        this.title = title;
    }

    @Override
    public String toString() {
        return title;
    }
}
