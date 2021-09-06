package com.conferences.handler.implementation;

import com.conferences.config.HttpMethod;
import com.conferences.handler.abstraction.IPermissionsHandler;

import java.nio.file.FileSystems;
import java.nio.file.PathMatcher;
import java.nio.file.Paths;
import java.util.*;

public class PermissionsHandler implements IPermissionsHandler {

    private static final String ALL_ROLES_KEY = "";
    private static final int ERROR_OK = 200;
    private static final int ERROR_FORBIDDEN = 403;

    private final Map<String, Map<HttpMethod, List<String>>> allowed;

    private PermissionsHandler() {
        allowed = new HashMap<>();
    }

    @Override
    public int checkPermission(String url, HttpMethod method, String role) {
        return (checkForRole(role, method, url) == ERROR_OK || checkForRole(ALL_ROLES_KEY, method, url) == ERROR_OK) ? ERROR_OK : ERROR_FORBIDDEN;
    }

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

    public static class Builder {

        private PermissionsHandler helper;

        private final List<String> urlPatterns;
        private final List<HttpMethod> httpMethods;

        public Builder() {
            urlPatterns = new ArrayList<>();
            httpMethods = new ArrayList<>();
        }

        public Builder init() {
            helper = new PermissionsHandler();
            initData();
            return this;
        }

        public Builder controlUrls(String... urls) {
            this.urlPatterns.addAll(Arrays.asList(urls));
            return this;
        }

        public Builder withMethods(HttpMethod... httpMethods) {
            this.httpMethods.addAll(Arrays.asList(httpMethods));
            return this;
        }

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

        public Builder allowAllRoles() {
            return allowAnyRoleOf(ALL_ROLES_KEY);
        }

        public PermissionsHandler build() {
            return helper;
        }

        private void initData() {
            urlPatterns.clear();
            httpMethods.clear();
        }

        private void addAllHttpMethods() {
            httpMethods.addAll(Arrays.asList(HttpMethod.values()));
        }

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
