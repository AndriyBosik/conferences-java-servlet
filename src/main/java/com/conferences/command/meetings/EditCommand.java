package com.conferences.command.meetings;

import com.conferences.command.FrontCommand;
import com.conferences.entity.Meeting;
import com.conferences.mapper.IMapper;
import com.conferences.mapper.RequestToEditableMeetingDataMapper;
import com.conferences.service.abstraction.IMeetingService;
import com.conferences.service.implementation.MeetingService;
import com.conferences.validator.IValidator;
import com.conferences.validator.MeetingEditableDataValidator;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;

public class EditCommand extends FrontCommand {

    private final IMeetingService meetingService;
    private final IMapper<HttpServletRequest, Meeting> meetingMapper;
    private final IValidator<Meeting> validator;

    public EditCommand() {
        meetingService = new MeetingService();
        meetingMapper = new RequestToEditableMeetingDataMapper();
        validator = new MeetingEditableDataValidator();
    }

    @Override
    public void process() throws ServletException, IOException {
        Meeting meeting = meetingMapper.map(request);

        if (meetingService.updateMeetingEditableData(meeting)) {
            redirectBack();
        } else {
            // TODO(Something wrong with data)
        }
    }
}
