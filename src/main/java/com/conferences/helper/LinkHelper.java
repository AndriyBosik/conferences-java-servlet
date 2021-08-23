package com.conferences.helper;

public class LinkHelper {

    public String addLangToUrl(String url, String lang) {
        StringBuilder link = new StringBuilder("/" + lang);

        if (!url.startsWith("/") && url.length() > 0) {
            link.append("/");
        }
        link.append(url);

        return link.toString();
    }

}
