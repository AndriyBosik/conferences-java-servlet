package com.conferences.command.meetings;

import com.conferences.command.FrontCommand;
import com.conferences.entity.Meeting;
import com.conferences.factory.MapperFactory;
import com.conferences.factory.ServiceFactory;
import com.conferences.factory.ValidatorFactory;
import com.conferences.mapper.IMapper;
import com.conferences.mapper.RequestToEditableMeetingDataMapper;
import com.conferences.service.abstraction.IMeetingService;
import com.conferences.service.implementation.MeetingService;
import com.conferences.validator.IValidator;
import com.conferences.validator.MeetingEditableDataValidator;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

public class EditCommand extends FrontCommand {

    private final IMeetingService meetingService;
    private final IMapper<HttpServletRequest, Meeting> meetingMapper;;

    public EditCommand() {
        meetingService = ServiceFactory.getInstance().getMeetingService();
        meetingMapper = MapperFactory.getInstance().getRequestToEditableMeetingDataMapper();
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
