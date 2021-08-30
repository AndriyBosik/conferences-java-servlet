package com.conferences.tag;

import com.conferences.config.Defaults;

import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.TagSupport;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Properties;

public class MessageTag extends TagSupport {

    private String value;

    @Override
    public int doStartTag() {
        String lang = (String) pageContext.getRequest().getAttribute(Defaults.CURRENT_LANG.toString());
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
