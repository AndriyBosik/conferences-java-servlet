package com.conferences.handler;

import com.conferences.config.Defaults;

public class LinkHandler {

    public String getLangFromUrl(String url) {
        if (url.length() == 0) {
            return Defaults.DEFAULT_LANG.toString();
        }
        String[] parts = url.split("/");
        for (String part: parts) {
            if (part.length() > 0) {
                return part;
            }
        }
        return Defaults.DEFAULT_LANG.toString();
    }

    public String addLangToUrl(String url, String lang) {
        StringBuilder link = new StringBuilder("/" + lang);

        if (!url.startsWith("/") && url.length() > 0) {
            link.append("/");
        }
        link.append(url);

        return link.toString();
    }

}
