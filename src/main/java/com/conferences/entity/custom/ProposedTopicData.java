package com.conferences.entity.custom;

import com.conferences.annotation.Column;

import java.util.Date;

public class ProposedTopicData {

    @Column(name = "id")
    private int id;

    @Column(name = "meeting_id")
    private int meetingId;

    @Column(name = "meeting_title")
    private String meetingTitle;

    @Column(name = "meeting_date")
    private Date meetingDate;

    @Column(name = "meeting_image_path")
    private String meetingImagePath;

    @Column(name = "proposed_topic_title")
    private String proposedTopicTitle;

    @Column(name = "speaker_name")
    private String speakerName;

    @Column(name = "speaker_surname")
    private String speakerSurname;

    @Column(name = "speaker_image_path")
    private String speakerImagePath;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getMeetingId() {
        return meetingId;
    }

    public void setMeetingId(int meetingId) {
        this.meetingId = meetingId;
    }

    public String getMeetingTitle() {
        return meetingTitle;
    }

    public void setMeetingTitle(String meetingTitle) {
        this.meetingTitle = meetingTitle;
    }

    public Date getMeetingDate() {
        return meetingDate;
    }

    public void setMeetingDate(Date meetingDate) {
        this.meetingDate = meetingDate;
    }

    public String getMeetingImagePath() {
        return meetingImagePath;
    }

    public void setMeetingImagePath(String meetingImagePath) {
        this.meetingImagePath = meetingImagePath;
    }

    public String getProposedTopicTitle() {
        return proposedTopicTitle;
    }

    public void setProposedTopicTitle(String proposedTopicTitle) {
        this.proposedTopicTitle = proposedTopicTitle;
    }

    public String getSpeakerName() {
        return speakerName;
    }

    public void setSpeakerName(String speakerName) {
        this.speakerName = speakerName;
    }

    public String getSpeakerSurname() {
        return speakerSurname;
    }

    public void setSpeakerSurname(String speakerSurname) {
        this.speakerSurname = speakerSurname;
    }

    public String getSpeakerImagePath() {
        return speakerImagePath;
    }

    public void setSpeakerImagePath(String speakerImagePath) {
        this.speakerImagePath = speakerImagePath;
    }
}
