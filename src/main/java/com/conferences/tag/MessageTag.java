package com.conferences.tag;

import com.conferences.config.Defaults;

import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.TagSupport;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Properties;

/**
 * <p>
 *     Defines custom jstl tag that can be used to localize messages
 * </p>
 *
 * @author Andriy
 * @version 1.0
 * @since 2021/09/09
 */
public class MessageTag extends TagSupport {

    /**
     * <p>
     *     Key from properties file
     * </p>
     */
    private String value;

    /**
     * <p>
     *     Gets value from properties file and prints it
     * </p>
     * @return SKIP_BODY
     */
    @Override
    public int doStartTag() {
        String lang = (String) pageContext.getRequest().getAttribute(Defaults.CURRENT_LANG.toString());
        lang = lang == null ? Defaults.DEFAULT_LANG.toString() : lang;
        String propertiesFilename = "messages_" + lang + ".properties";
        JspWriter out = pageContext.getOut();
        Properties properties = new Properties();
        try {
            properties.load(new InputStreamReader(MessageTag.class.getClassLoader().getResourceAsStream(propertiesFilename), "UTF-8"));
            String propertyValue = properties.getProperty(value);
            out.print(propertyValue == null ? "" : propertyValue);
        } catch (IOException exception) {
            exception.printStackTrace();
        }
        return SKIP_BODY;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
