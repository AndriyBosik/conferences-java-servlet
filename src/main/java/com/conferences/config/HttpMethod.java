package com.conferences.config;

public enum HttpMethod {
    GET("GET"),
    POST("POST");

    private final String method;

    HttpMethod(String method) {
        this.method = method.toUpperCase();
    }

    public static HttpMethod fromString(String httpMethod) {
        return HttpMethod.valueOf(httpMethod.toUpperCase());
    }

    @Override
    public String toString() {
        return method;
    }
}
