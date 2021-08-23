package com.conferences.helper;

import com.conferences.config.Defaults;

import javax.servlet.http.HttpSession;

public class LangHelper {

    public String getLangFromSession(HttpSession session) {
        String sessionLang = (String) session.getAttribute("lang");
        if (sessionLang != null) {
            return sessionLang;
        }
        return Defaults.LANG.toString();
    }

}
