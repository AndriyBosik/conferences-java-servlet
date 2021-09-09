package com.conferences.tag;

import com.conferences.config.Defaults;
import com.conferences.factory.HandlerFactory;
import com.conferences.handler.abstraction.ILinkHandler;
import com.conferences.handler.implementation.LinkHandler;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.TagSupport;
import java.io.IOException;

/**
 * <p>
 *     Defines custom tag to localize link
 * </p>
 *
 * @author Andriy
 * @version 1.0
 * @since 2021/09/09
 */
public class LinkToTag extends TagSupport {

    /**
     * <p>
     *     Link that must be transformed
     * </p>
     */
    private String href;

    /**
     * <p>
     *     Optional parameter. You should use it if you want to redirect user to page of another lang
     * </p>
     */
    private String toLang;

    /**
     * <p>
     *     Optional parameter. You should use it if you want to save GET parameters from url
     * </p>
     */
    private Boolean saveQueryString;

    private final ILinkHandler linkHandler;

    public LinkToTag() {
        this.linkHandler = HandlerFactory.getInstance().getLinkHandler();
    }

    /**
     * <p>
     *     Gets current language from request and concatenates it with passed href
     * </p>
     * @return SKIP_BODY
     */
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

    /**
     * <p>
     *     Inits toLang if it was not passed
     * </p>
     */
    private void initToLang() {
        if (toLang == null || "".equals(toLang)) {
            toLang = (String) pageContext.getRequest().getAttribute(Defaults.CURRENT_LANG.toString());
        }
    }

    /**
     * <p>
     *     Inits saveQueryString if it was now passed
     * </p>
     */
    private void initSaveQueryString() {
        if (saveQueryString == null) {
            saveQueryString = false;
        }
    }

    /**
     * <p>
     *     Adds query string to new url if saveQueryString=true
     * </p>
     * @param url new url that may be concatenated with query string
     * @return url concatenated with query string if saveQueryString=true
     */
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

    /**
     * <p>
     *     Resets optional parameters to initial values
     * </p>
     */
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
