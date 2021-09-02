package com.conferences.entity.custom;

import com.conferences.annotation.Column;

import java.time.LocalDateTime;
import java.util.Date;

public class ProposalData {

    @Column(name = "id")
    private int id;

    @Column(name = "meeting_id")
    private int meetingId;

    @Column(name = "meeting_title")
    private String meetingTitle;

    @Column(name = "meeting_date")
    private LocalDateTime meetingDate;

    @Column(name = "meeting_image_path")
    private String meetingImagePath;

    @Column(name = "report_topic_id")
    private int reportTopicId;

    @Column(name = "report_topic_title")
    private String reportTopicTitle;

    @Column(name = "speaker_id")
    private int speakerId;

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

    public LocalDateTime getMeetingDate() {
        return meetingDate;
    }

    public void setMeetingDate(LocalDateTime meetingDate) {
        this.meetingDate = meetingDate;
    }

    public String getMeetingImagePath() {
        return meetingImagePath;
    }

    public void setMeetingImagePath(String meetingImagePath) {
        this.meetingImagePath = meetingImagePath;
    }

    public int getReportTopicId() {
        return reportTopicId;
    }

    public void setReportTopicId(int reportTopicId) {
        this.reportTopicId = reportTopicId;
    }

    public String getReportTopicTitle() {
        return reportTopicTitle;
    }

    public void setReportTopicTitle(String reportTopicTitle) {
        this.reportTopicTitle = reportTopicTitle;
    }

    public int getSpeakerId() {
        return speakerId;
    }

    public void setSpeakerId(int speakerId) {
        this.speakerId = speakerId;
    }
}
