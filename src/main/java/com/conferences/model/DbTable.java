package com.conferences.model;

import java.util.ArrayList;
import java.util.List;

public class DbTable {

    private String name;

    private String key;

    private List<String> fields;

    public DbTable() {
        fields = new ArrayList<>();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public List<String> getFields() {
        return fields;
    }

    public void setFields(List<String> fields) {
        this.fields = fields;
    }
}
