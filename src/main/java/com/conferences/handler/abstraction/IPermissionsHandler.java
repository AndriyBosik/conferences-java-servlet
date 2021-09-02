package com.conferences.handler.abstraction;

import com.conferences.config.HttpMethod;

public interface IPermissionsHandler {

    boolean isAllowed(String role, HttpMethod method, String url);

}
