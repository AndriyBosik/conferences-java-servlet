package com.conferences.handler.abstraction;

/**
 * <p>
 *     Defines methods to work with links
 * </p>
 *
 * @author Andriy
 * @version 1.0
 * @since 2021/09/09
 */
public interface ILinkHandler {

    /**
     * <p>
     *     Extracts language from URL
     * </p>
     * @param url string value to extract language from
     * @return language extracted from URL, default language may be returned if no language was found in URL
     */
    String getLangFromUrl(String url);

    /**
     * <p>
     *     Adds language to URL
     * </p>
     * @param url string value of URL to add language to
     * @param lang language that must be added
     * @return URL string with added language
     */
    String addLangToUrl(String url, String lang);
}
