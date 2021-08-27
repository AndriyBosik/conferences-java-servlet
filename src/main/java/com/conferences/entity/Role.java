package com.conferences.entity;

import com.conferences.annotation.Column;
import com.conferences.annotation.Table;

import java.io.Serializable;

@Table(name = "roles")
public class Role implements Serializable {
    private static final long serialVersionUID = 324582034705235L;

    @Column(name = "id", key = true)
    private int id;

    @Column(name = "title")
    private String title;

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
}
