package com.conferences.mapper;

import com.conferences.config.Defaults;
import com.conferences.entity.Meeting;
import com.conferences.handler.abstraction.IEncodingHandler;
import com.conferences.handler.implementation.EncodingHandler;
import com.conferences.utils.TimeUtils;

import javax.servlet.http.HttpServletRequest;
import java.sql.Time;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;

public class RequestToEditableMeetingDataMapper implements IMapper<HttpServletRequest, Meeting> {

    private final IEncodingHandler encodingHandler;

    public RequestToEditableMeetingDataMapper() {
        encodingHandler = new EncodingHandler();
    }

    @Override
    public Meeting map(HttpServletRequest request) {
        Meeting meeting = new Meeting();
        meeting.setId(Integer.parseInt(request.getParameter("id")));
        meeting.setAddress(encodingHandler.getUTF8ValueFromRequest(request, "address"));

        String strDate =
                request.getParameter("date") + " " +
                TimeUtils.addZeroToBegin(request.getParameter("hours")) + ":" +
                TimeUtils.addZeroToBegin(request.getParameter("minutes"));
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(Defaults.DATE_FORMAT.toString());
        LocalDateTime date = LocalDateTime.parse(strDate, formatter);

        meeting.setDate(date);
        return meeting;
    }
}
