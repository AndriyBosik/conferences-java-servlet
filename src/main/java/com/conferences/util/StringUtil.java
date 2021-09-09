package com.conferences.util;

import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;

/**
 * <p>
 *     Defines methods to process strings
 * </p>
 *
 * @author Andriy
 * @version 1.0
 * @since 2021/09/09
 */
public class StringUtil {

    private StringUtil() {}

    /**
     * <p>
     *     Checks whatever passed string is null of empty
     * </p>
     * @param value string to check
     * @return true if {@code value} is null or empty, false otherwise
     */
    public static boolean isNullOrEmpty(String value) {
        return  value == null ||
                value.trim().isEmpty();
    }

    /**
     * <p>
     *     Checks whatever of string array elements are null or empty
     * </p>
     * @param values string array to check
     * @return true if all elements are null or empty, false otherwise
     */
    public static boolean isNullOrEmptyAll(String... values) {
        for (String value: values) {
            if (!isNullOrEmpty(value)) {
                return false;
            }
        }
        return true;
    }

    /**
     * <p>
     *     Converts string to UTF-8 encoding
     * </p>
     * @param value string that needs to be encoded
     * @param charset current string charset
     * @return string in UTF-8 encoding
     */
    public static String convertStringToUTF8(String value, Charset charset) {
        return new String(value.getBytes(charset), StandardCharsets.UTF_8);
    }

}
