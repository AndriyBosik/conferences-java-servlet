package com.conferences.handler.abstraction;

/**
 * <p>
 *     Defines methods to handle property files
 * </p>
 *
 * @author Andriy
 * @version 1.0
 * @since 2021/09/09
 */
public interface IPropertiesHandler {

    /**
     * <p>
     *     Returns property value from property value
     * </p>
     * @param filename name of resource bundle
     * @param lang localization
     * @param key property key
     * @return value for specific key and localization or only key if no value was found
     */
    String getPropertyValue(String filename, String lang, String key);
}
