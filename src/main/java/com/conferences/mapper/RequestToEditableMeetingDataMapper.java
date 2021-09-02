package com.conferences.mapper;

import com.conferences.config.Defaults;
import com.conferences.entity.Meeting;

import javax.servlet.http.HttpServletRequest;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;

public class RequestToEditableMeetingDataMapper implements IMapper<HttpServletRequest, Meeting> {

    @Override
    public Meeting map(HttpServletRequest request) {
        Meeting meeting = new Meeting();
        meeting.setId(Integer.parseInt(request.getParameter("id")));
        meeting.setAddress(request.getParameter("address"));

        String strDate = request.getParameter("date") + " " + request.getParameter("hours") + ":" + request.getParameter("minutes");
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(Defaults.DATE_FORMAT.toString());
        LocalDateTime date = LocalDateTime.parse(strDate, formatter);

        meeting.setDate(date);
        return meeting;
    }
}
