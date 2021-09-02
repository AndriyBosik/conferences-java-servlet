package com.conferences.utils;

public class StringUtils {

    private StringUtils() {}

    public static boolean isNullOrEmpty(String value) {
        return  value == null ||
                value.trim().isEmpty();
    }

    public static boolean isNullOrEmptyAll(String... values) {
        for (String value: values) {
            if (!isNullOrEmpty(value)) {
                return false;
            }
        }
        return true;
    }

}
