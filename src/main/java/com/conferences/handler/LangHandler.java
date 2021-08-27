package com.conferences.handler;

import com.conferences.config.Defaults;

import javax.servlet.http.HttpSession;

public class LangHandler {

    public String getLang(HttpSession session) {
        String sessionLang = (String) session.getAttribute("lang");
        if (sessionLang != null) {
            return sessionLang;
        }
        return Defaults.DEFAULT_LANG.toString();
    }

}
