package com.conferences.handler.implementation;

import com.conferences.config.HttpMethod;
import com.conferences.handler.abstraction.IPermissionsHandler;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.nio.file.FileSystems;
import java.nio.file.PathMatcher;
import java.nio.file.Paths;
import java.util.*;

/**
 * {@inheritDoc}
 */
public class PermissionsHandler implements IPermissionsHandler {

    private static final Logger LOGGER = LogManager.getLogger(PermissionsHandler.class);
    private static final String ALL_ROLES_KEY = "";
    private static final int ERROR_OK = 200;
    private static final int ERROR_FORBIDDEN = 403;

    private final Map<String, Map<HttpMethod, List<String>>> allowed;

    private PermissionsHandler() {
        allowed = new HashMap<>();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int checkPermission(String url, HttpMethod method, String role) {
        int result = (checkForRole(role, method, url) == ERROR_OK || checkForRole(ALL_ROLES_KEY, method, url) == ERROR_OK) ? ERROR_OK : ERROR_FORBIDDEN;
        LOGGER.info("Checking permission for {} role, {} method, {} url. Result: {}", role, method, url, result);
        return result;
    }

    /**
     * <p>
     *     Checks permission for specified role for specified url with specified HTTP method
     * </p>
     * @param role user role to check permission for
     * @param method HTTP method of HTTP request
     * @param url string value of URL resource
     * @return code of HTTP error
     */
    private int checkForRole(String role, HttpMethod method, String url) {
        role = role.toLowerCase();
        if (!allowed.containsKey(role) || !allowed.get(role).containsKey(method)) {
            return ERROR_FORBIDDEN;
        }
        List<String> allowedUrlPatterns = allowed.get(role).get(method);
        for (String pattern: allowedUrlPatterns) {
            PathMatcher matcher = FileSystems.getDefault().getPathMatcher("glob:" + pattern);
            if (matcher.matches(Paths.get(url))) {
                return ERROR_OK;
            }
        }
        return ERROR_FORBIDDEN;
    }

    /**
     * <p>
     *     Builder used to create {@link PermissionsHandler}
     * </p>
     */
    public static class Builder {

        private PermissionsHandler helper;

        private final List<String> urlPatterns;
        private final List<HttpMethod> httpMethods;

        public Builder() {
            urlPatterns = new ArrayList<>();
            httpMethods = new ArrayList<>();
        }

        /**
         * <p>
         *     Inits {@link PermissionsHandler} object
         * </p>
         * @return {@link Builder}
         */
        public Builder init() {
            helper = new PermissionsHandler();
            initData();
            return this;
        }

        /**
         * <p>
         *     Adds urls which will be controlled by handler
         * </p>
         * @param urls array of urls
         * @return {@link Builder}
         */
        public Builder controlUrls(String... urls) {
            this.urlPatterns.addAll(Arrays.asList(urls));
            return this;
        }

        /**
         * <p>
         *     Adds allowed HTTP methods to added URLs
         * </p>
         * @param httpMethods array of HTTP methods represented by {@link HttpMethod} enum
         * @return {@link Builder}
         */
        public Builder withMethods(HttpMethod... httpMethods) {
            this.httpMethods.addAll(Arrays.asList(httpMethods));
            return this;
        }

        /**
         * <p>
         *     Allows URL resources for specific user roles
         * </p>
         * @param roles list of user roles
         * @return {@link Builder}
         */
        public Builder allowAnyRoleOf(String... roles) {
            if (httpMethods.isEmpty()) {
                addAllHttpMethods();
            }
            Arrays.stream(roles)
                .map(String::toLowerCase)
                .forEach(this::addForRole);
            initData();
            return this;
        }

        /**
         * <p>
         *     Allows URL resources for all user roles
         * </p>
         * @return {@link Builder}
         */
        public Builder allowAllRoles() {
            return allowAnyRoleOf(ALL_ROLES_KEY);
        }

        /**
         * <p>
         *     Returns configured {@link PermissionsHandler}
         * </p>
         * @return {@link PermissionsHandler}
         */
        public PermissionsHandler build() {
            return helper;
        }

        /**
         * <p>
         *     Inits builder data
         * </p>
         */
        private void initData() {
            urlPatterns.clear();
            httpMethods.clear();
        }

        /**
         * <p>
         *     Adds all HTTP methods to list of allowed HTTP methods
         * </p>
         */
        private void addAllHttpMethods() {
            httpMethods.addAll(Arrays.asList(HttpMethod.values()));
        }

        /**
         * <p>
         *     Configures {@link PermissionsHandler} to control URL
         * </p>
         * @param role user role to control URLs for
         */
        private void addForRole(String role) {
            if (!helper.allowed.containsKey(role)) {
                helper.allowed.put(role, new EnumMap<>(HttpMethod.class));
            }

            for (HttpMethod method: httpMethods) {
                if (!helper.allowed.get(role).containsKey(method)) {
                    helper.allowed.get(role).put(method, new ArrayList<>());
                }

                for (String url: urlPatterns) {
                    helper.allowed.get(role).get(method).add(url);
                }
            }
        }

    }

}
