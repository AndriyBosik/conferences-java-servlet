package com.conferences.tag;

import com.conferences.config.Defaults;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.TagSupport;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class DateFormatterTag extends TagSupport {

    private LocalDateTime date;

    private String format;

    @Override
    public int doStartTag() throws JspException {
        initDate();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(format);
        String formattedDate = date.format(formatter);
        JspWriter out = pageContext.getOut();
        try {
            out.print(formattedDate);
        } catch (IOException exception) {
            exception.printStackTrace();
        }
        resetData();
        return SKIP_BODY;
    }

    private void initDate() {
        if (date == null) {
            date = LocalDateTime.now();
        }
    }

    private void resetData() {
        date = null;
    }

    public void setDate(LocalDateTime date) {
        this.date = date;
    }

    public void setFormat(String format) {
        this.format = format;
    }
}
