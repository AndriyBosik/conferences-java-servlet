package com.conferences.entity;

import com.conferences.annotation.Column;
import com.conferences.annotation.Key;
import com.conferences.annotation.Table;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Table(name = "meetings")
public class Meeting {

    @Key
    @Column(name = "id")
    private int id;

    @Column(name = "title")
    private String title;

    @Column(name = "description")
    private String description;

    @Column(name = "image_path")
    private String imagePath;

    @Column(name = "date")
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