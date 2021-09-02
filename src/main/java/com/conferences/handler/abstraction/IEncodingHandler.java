package com.conferences.handler.abstraction;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

public interface IEncodingHandler {

    String getUTF8ValueFromRequest(HttpServletRequest request, String parameterName);

    String getUTF8ValueFromFormDataMap(Map<String, String> formData, String parameterName);

}
