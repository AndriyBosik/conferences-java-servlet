package com.conferences.handler.abstraction;

public interface ILinkHandler {

    String getLangFromUrl(String url);

    String addLangToUrl(String url, String lang);

}
