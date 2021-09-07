package com.conferences.util;

import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;

public class StringUtil {

    private StringUtil() {}

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

    public static String convertStringToUTF8(String value, Charset charset) {
        return new String(value.getBytes(charset), StandardCharsets.UTF_8);
    }

}
