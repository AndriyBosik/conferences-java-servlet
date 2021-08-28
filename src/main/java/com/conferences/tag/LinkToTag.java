package com.conferences.tag;

import com.conferences.config.Defaults;
import com.conferences.handler.LinkHandler;

import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.TagSupport;
import java.io.IOException;

public class LinkToTag extends TagSupport {

    private String href;

    private final LinkHandler linkHandler;

    public LinkToTag() {
        this.linkHandler = new LinkHandler();
    }

    @Override
    public int doStartTag() {
        String lang = (String) pageContext.getRequest().getAttribute(Defaults.CURRENT_LANG.toString());

        JspWriter out = pageContext.getOut();

        String url = linkHandler.addLangToUrl(href, lang);

        try {
            out.print(url);
        } catch (IOException exception) {
            exception.printStackTrace();
        }

        return SKIP_BODY;
    }

    public void setHref(String href) {
        this.href = href;
    }
}
