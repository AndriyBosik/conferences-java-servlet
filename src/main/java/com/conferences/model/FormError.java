package com.conferences.model;

import com.conferences.config.ErrorKey;

import java.util.Arrays;
import java.util.List;

public class FormError {

    private ErrorKey errorKey;

    private List<String> params;

    private String lang;

    public FormError() {}

    public FormError(ErrorKey errorKey, String... params) {
        this.errorKey = errorKey;
        this.params = Arrays.asList(params);
    }

    public FormError(String lang, ErrorKey errorKey, String... params) {
        this(errorKey, params);
        this.lang = lang;
    }

    public ErrorKey getErrorKey() {
        return errorKey;
    }

    public void setErrorKey(ErrorKey errorKey) {
        this.errorKey = errorKey;
    }

    public List<String> getParams() {
        return params;
    }

    public void setParams(List<String> params) {
        this.params = params;
    }

    public String getLang() {
        return lang;
    }

    public void setLang(String lang) {
        this.lang = lang;
    }
}
