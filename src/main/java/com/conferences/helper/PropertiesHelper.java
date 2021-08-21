package com.conferences.helper;

import com.conferences.tag.MessageTag;

import javax.servlet.jsp.JspWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Properties;

public class PropertiesHelper {

    public String getPropertyValue(String filename, String lang, String key) {
        String propertiesFilename = filename + "_" + lang + ".properties";
        Properties properties = new Properties();
        try {
            properties.load(new InputStreamReader(PropertiesHelper.class.getClassLoader().getResourceAsStream(propertiesFilename), "UTF-8"));
            return properties.getProperty(key);
        } catch (IOException exception) {
            exception.printStackTrace();
        }
        return "";
    }

}
