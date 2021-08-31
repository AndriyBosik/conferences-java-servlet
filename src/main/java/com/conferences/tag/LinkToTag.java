package com.conferences.tag;

import com.conferences.config.Defaults;
import com.conferences.handler.LinkHandler;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.TagSupport;
import java.io.IOException;

public class LinkToTag extends TagSupport {

    private String href;
    private String toLang;
    private Boolean saveQueryString;

    private final LinkHandler linkHandler;

    public LinkToTag() {
        this.linkHandler = new LinkHandler();
    }

    @Override
    public int doStartTag() {
        initToLang();
        initSaveQueryString();

        JspWriter out = pageContext.getOut();

        String url;
        if ("".equals(href)) {
            url = (String) pageContext.getRequest().getAttribute(Defaults.CURRENT_LINK.toString());
        } else {
            url = href;
        }

        url = addQueryStringIfNecessary(url);

        String link = linkHandler.addLangToUrl(url, toLang);

        try {
            out.print(link);
        } catch (IOException exception) {
            exception.printStackTrace();
        }

        resetData();

        return SKIP_BODY;
    }

    private void initToLang() {
        if (toLang == null || "".equals(toLang)) {
            toLang = (String) pageContext.getRequest().getAttribute(Defaults.CURRENT_LANG.toString());
        }
    }

    private void initSaveQueryString() {
        if (saveQueryString == null) {
            saveQueryString = false;
        }
    }

    private String addQueryStringIfNecessary(String url) {
        if (!saveQueryString) {
            return url;
        }
        HttpServletRequest request = (HttpServletRequest) pageContext.getRequest();
        String queryString = request.getQueryString();
        if (queryString != null && !queryString.isEmpty()) {
            return url + "?" + queryString;
        }
        return url;
    }

    private void resetData() {
        setToLang(null);
        setSaveQueryString(null);
    }

    public void setToLang(String toLang) {
        this.toLang = toLang;
    }

    public void setHref(String href) {
        this.href = href;
    }

    public Boolean getSaveQueryString() {
        return saveQueryString;
    }

    public void setSaveQueryString(Boolean saveQueryString) {
        this.saveQueryString = saveQueryString;
    }
}
