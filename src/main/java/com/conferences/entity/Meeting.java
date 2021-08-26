package com.conferences.entity;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Meeting {
    private int id;
    private String title;
    private String description;
    private String imagePath;
    private Date date;

    private List<ReportTopic> reportTopics;

    public Meeting() {
        reportTopics = new ArrayList<>();
    }

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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getImagePath() {
        return imagePath;
    }

    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public List<ReportTopic> getReportTopics() {
        return reportTopics;
    }

    public void setReportTopics(List<ReportTopic> reportTopics) {
        this.reportTopics = reportTopics;
    }
}
