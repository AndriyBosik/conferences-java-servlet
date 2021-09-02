package com.conferences.handler.implementation;

import com.conferences.handler.abstraction.IEncodingHandler;

import javax.servlet.http.HttpServletRequest;
import java.nio.charset.StandardCharsets;
import java.util.Map;

public class EncodingHandler implements IEncodingHandler {

    @Override
    public String getUTF8ValueFromRequest(HttpServletRequest request, String parameterName) {
        String value = request.getParameter("title");
        return new String(value.getBytes(StandardCharsets.ISO_8859_1), StandardCharsets.UTF_8);
    }

    @Override
    public String getUTF8ValueFromFormDataMap(Map<String, String> formData, String parameterName) {
        String value = formData.get(parameterName);
        return new String(value.getBytes(StandardCharsets.ISO_8859_1), StandardCharsets.UTF_8);
    }


}
