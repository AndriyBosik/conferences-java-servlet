package com.conferences.utils;

public class TimeUtils {

    private TimeUtils() {}

    public static String addZeroToBegin(String value) {
        if (value.length() < 2) {
            return "0" + value;
        }
        return value;
    }

}
