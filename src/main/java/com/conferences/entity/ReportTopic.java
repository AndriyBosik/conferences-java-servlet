package com.conferences.entity;

import com.conferences.annotation.Column;
import com.conferences.annotation.Key;
import com.conferences.annotation.Table;

@Table(name = "report_topics")
public class ReportTopic {

    @Key
    @Column(name = "id")
    private int id;

    @Column(name = "title")
    private String title;

    @Column(name = "meeting_id")
    private int meetingId;

    @Column(name = "speaker_id")
    private int speakerId;

    private User Speaker;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getMeetingId() {
        return meetingId;
    }

    public void setMeetingId(int meetingId) {
        this.meetingId = meetingId;
    }

    public int getSpeakerId() {
        return speakerId;
    }

    public void setSpeakerId(int speakerId) {
        this.speakerId = speakerId;
    }

    public User getSpeaker() {
        return Speaker;
    }

    public void setSpeaker(User speaker) {
        Speaker = speaker;
    }
}
