package com.conferences.handler.abstraction;

import com.conferences.config.HttpMethod;

/**
 * <p>
 *     Defines methods to control access to resources
 * </p>
 *
 * @author Andriy
 * @version 1.0
 * @since 2021/09/09
 */
public interface IPermissionsHandler {

    /**
     * <p>
     *     Checks permission for specific resource
     * </p>
     * @param url string value to check permission for
     * @param method HTTP method
     * @param role user role
     * @return code of HTTP error
     */
    int checkPermission(String url, HttpMethod method, String role);
}
