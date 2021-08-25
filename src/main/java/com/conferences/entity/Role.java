package com.conferences.entity;

import java.io.Serializable;

public class Role implements Serializable {
    private static final long serialVersionUID = 324582034705235L;

    private int id;
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
