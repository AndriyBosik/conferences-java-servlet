package com.conferences.command.meetings;

import com.conferences.command.FrontCommand;
import com.conferences.config.FormKeys;
import com.conferences.entity.Meeting;
import com.conferences.factory.MapperFactory;
import com.conferences.factory.ServiceFactory;
import com.conferences.factory.ValidatorFactory;
import com.conferences.mapper.IMapper;
import com.conferences.mapper.RequestToEditableMeetingDataMapper;
import com.conferences.model.FormError;
import com.conferences.service.abstraction.IMeetingService;
import com.conferences.service.implementation.MeetingService;
import com.conferences.validator.IValidator;
import com.conferences.validator.MeetingEditableDataValidator;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class EditCommand extends FrontCommand {

    private IMeetingService meetingService;
    private IMapper<HttpServletRequest, Meeting> meetingMapper;

    @Override
    public void init(ServletContext context, HttpServletRequest request, HttpServletResponse response, List<String> urlParams) {
        super.init(context, request, response, urlParams);
        meetingService = ServiceFactory.getInstance().getMeetingService();
        meetingMapper = MapperFactory.getInstance().getRequestToEditableMeetingDataMapper();
    }

    @Override
    public void process() throws ServletException, IOException {
        Meeting meeting = meetingMapper.map(request);

        List<FormError> errors = meetingService.updateMeetingEditableData(meeting);
        if (!errors.isEmpty()) {
            saveErrorsToSession(FormKeys.UPDATED_MEETING_ERRORS, errors);
            Map<String, String> values = new HashMap<>();
            values.put("address", meeting.getAddress());
            saveFieldValuesToSession(FormKeys.UPDATED_MEETING_FIELDS, values);
        }
        redirectBack();
    }
}
