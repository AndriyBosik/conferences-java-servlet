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

    private final LinkHandler linkHandler;

    public LinkToTag() {
        this.linkHandler = new LinkHandler();
    }

    @Override
    public int doStartTag() {
        initToLang();

        JspWriter out = pageContext.getOut();

        String url = "";
        if ("".equals(href)) {
            HttpServletRequest request = (HttpServletRequest) pageContext.getRequest();
            String queryString = request.getQueryString();
            String currentLink = (String) pageContext.getRequest().getAttribute(Defaults.CURRENT_LINK.toString());
            if (queryString != null && !queryString.isEmpty()) {
                currentLink += "?" + queryString;
            }
            url = linkHandler.addLangToUrl(currentLink, toLang);
        } else {
            url = linkHandler.addLangToUrl(href, toLang);
        }

        try {
            out.print(url);
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

    private void resetData() {
        setToLang(null);
    }

    public void setToLang(String toLang) {
        this.toLang = toLang;
    }

    public void setHref(String href) {
        this.href = href;
    }
}
