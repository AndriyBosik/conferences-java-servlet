package com.conferences.mapper;

import com.conferences.entity.Meeting;

import javax.servlet.http.HttpServletRequest;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class RequestToEditableMeetingDataMapper implements IMapper<HttpServletRequest, Meeting> {

    @Override
    public Meeting map(HttpServletRequest request) {
        Meeting meeting = new Meeting();
        meeting.setId(Integer.parseInt(request.getParameter("id")));
        meeting.setAddress(request.getParameter("address"));

        String strDate = request.getParameter("date") + " " + request.getParameter("hours") + ":" + request.getParameter("minutes");
        Date date = null;
        try {
            date = new SimpleDateFormat("dd-MM-yyyy HH:mm").parse(strDate);
        } catch (ParseException exception) {
            exception.printStackTrace();
        }

        meeting.setDate(date);
        return meeting;
    }
}
