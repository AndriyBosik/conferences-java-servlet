package com.conferences.handler.abstraction;

import javax.servlet.http.HttpServletRequest;

/**
 * <p>
 *     Defines methods to process JSON data
 * </p>
 *
 * @author Andriy
 * @version 1.0
 * @since 2021/09/09
 */
public interface IJsonHandler {

    /**
     * <p>
     *     Parses {@link HttpServletRequest} into object of {@code objectClass} class
     * </p>
     * @param request {@link HttpServletRequest} to get JSON string from
     * @param objectClass class of object to parse JSON string to
     * @param <T> type of return object
     * @return parsed object
     */
    <T> T parseJsonRequestBodyToObject(HttpServletRequest request, Class<T> objectClass);
}
