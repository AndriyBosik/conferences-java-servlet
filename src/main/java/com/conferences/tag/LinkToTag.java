package com.conferences.tag;

import com.conferences.config.Defaults;
import com.conferences.helper.LangHelper;
import com.conferences.helper.LinkHelper;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.TagSupport;
import java.io.IOException;

public class LinkToTag extends TagSupport {

    private String href;

    private LangHelper langHelper;
    private LinkHelper linkHelper;

    public LinkToTag() {
        this.langHelper = new LangHelper();
        this.linkHelper = new LinkHelper();
    }

    @Override
    public int doStartTag() {
        String lang = langHelper.getLangFromSession(pageContext.getSession());

        JspWriter out = pageContext.getOut();

        String url = linkHelper.addLangToUrl(href, lang);

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
