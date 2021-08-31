package com.conferences.handler.abstraction;

import javax.servlet.http.HttpServletRequest;

public interface IJsonHandler {

    <T> T parseJsonRequestBodyToObject(HttpServletRequest request, Class<T> objectClass);

}
