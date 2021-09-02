package com.conferences.entity;

import com.conferences.annotation.Column;
import com.conferences.annotation.Table;

@Table(name = "users_meetings")
public class UserMeeting {

    @Column(name="id", key = true, autoGenerated = true)
    private int id;

    @Column(name="user_id")
    private int userId;

    @Column(name="meeting_id")
    private int meetingId;

    @Column(name="present")
    private boolean present;

    private User user;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getMeetingId() {
        return meetingId;
    }

    public void setMeetingId(int meetingId) {
        this.meetingId = meetingId;
    }

    public boolean isPresent() {
        return present;
    }

    public void setPresent(boolean present) {
        this.present = present;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
