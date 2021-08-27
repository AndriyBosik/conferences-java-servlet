package com.conferences.tag;

import com.conferences.handler.LangHandler;
import com.conferences.handler.LinkHandler;

import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.TagSupport;
import java.io.IOException;

public class LinkToTag extends TagSupport {

    private String href;

    private LangHandler langHandler;
    private LinkHandler linkHandler;

    public LinkToTag() {
        this.langHandler = new LangHandler();
        this.linkHandler = new LinkHandler();
    }

    @Override
    public int doStartTag() {
        String lang = langHandler.getLang(pageContext.getSession());

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
