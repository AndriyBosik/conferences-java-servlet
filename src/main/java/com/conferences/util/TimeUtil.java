package com.conferences.util;

/**
 * <p>
 *     Defines functions to process Date and Time
 * </p>
 *
 * @author Andriy
 * @version 1.0
 * @since 2021/09/09
 */
public class TimeUtil {

    private TimeUtil() {}

    /**
     * <p>
     *     Adds zero to begin of number value to make it 2 chars length minimum
     * </p>
     * @param value number
     * @return string with 2 chars length minimum containing zero at begin if passed number was one-digit
     */
    public static String addZeroToBegin(int value) {
        if (value < 10) {
            return "0" + value;
        }
        return value + "";
    }

}
