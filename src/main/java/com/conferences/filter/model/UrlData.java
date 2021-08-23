package com.conferences.filter.model;

public class UrlData {

    private String lang;
    private String path;

    public UrlData() {}

    public UrlData(String lang, String path) {
        this.lang = lang;
        this.path = path;
    }

    public String getLang() {
        return lang;
    }

    public void setLang(String lang) {
        this.lang = lang;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }
}
