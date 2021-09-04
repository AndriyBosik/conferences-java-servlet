package com.conferences.handler.abstraction;

import com.conferences.config.HttpMethod;

public interface IPermissionsHandler {

    int checkPermission(String url, HttpMethod method, String role);

}
