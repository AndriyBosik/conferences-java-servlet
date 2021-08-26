package com.conferences.helper;

import com.conferences.config.Defaults;

import javax.servlet.http.HttpSession;

public class LangHelper {

    public String getLang(String url) {
        if (url.length() == 0) {
            return Defaults.LANG.toString();
        }
        String[] parts = url.split("/");
        for (String part: parts) {
            if (part.length() > 0) {
                return part;
            }
        }
        return null;
    }

    public String getLang(HttpSession session) {
        String sessionLang = (String) session.getAttribute("lang");
        if (sessionLang != null) {
            return sessionLang;
        }
        return Defaults.LANG.toString();
    }

}
