package com.conferences.config;

public enum HttpMethods {
    GET("GET"),
    POST("POST");

    private String method;

    HttpMethods(String method) {
        this.method = method;
    }

    @Override
    public String toString() {
        return method;
    }
}
