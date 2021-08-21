package com.conferences.helper;

import com.conferences.config.Defaults;

import javax.servlet.http.HttpServletRequest;

public class LangHelper {

    public String getLangFromSession(HttpServletRequest request) {
        String sessionLang = (String) request.getSession().getAttribute("lang");
        if (sessionLang != null) {
            return sessionLang;
        }
        return Defaults.LANG.toString();
    }

}
