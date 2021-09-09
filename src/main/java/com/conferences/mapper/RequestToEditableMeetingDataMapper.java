package com.conferences.mapper;

import com.conferences.config.Defaults;
import com.conferences.entity.Meeting;
import com.conferences.util.TimeUtil;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * {@inheritDoc}
 */
public class RequestToEditableMeetingDataMapper implements IMapper<HttpServletRequest, Meeting> {

    /**
     * <p>
     *     Maps {@link HttpServletRequest} to editable {@link Meeting} data that contains meeting address and date
     * </p>
     */
    @Override
    public Meeting map(HttpServletRequest request) {
        Meeting meeting = new Meeting();
        meeting.setId(Integer.parseInt(request.getParameter("id")));
        meeting.setAddress(request.getParameter("address"));

        String strDate =
                request.getParameter("date") + " " +
                TimeUtil.addZeroToBegin(Integer.parseInt(request.getParameter("hours"))) + ":" +
                TimeUtil.addZeroToBegin(Integer.parseInt(request.getParameter("minutes")));
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(Defaults.DATE_FORMAT.toString());
        LocalDateTime date = LocalDateTime.parse(strDate, formatter);

        meeting.setDate(date);
        return meeting;
    }
}
