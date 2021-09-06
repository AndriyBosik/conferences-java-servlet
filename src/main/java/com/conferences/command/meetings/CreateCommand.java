package com.conferences.command.meetings;

import com.conferences.command.FrontCommand;
import com.conferences.config.Defaults;
import com.conferences.config.ErrorKey;
import com.conferences.config.FormKeys;
import com.conferences.config.Page;
import com.conferences.entity.Meeting;
import com.conferences.entity.User;
import com.conferences.factory.HandlerFactory;
import com.conferences.factory.MapperFactory;
import com.conferences.factory.ServiceFactory;
import com.conferences.factory.ValidatorFactory;
import com.conferences.handler.abstraction.IFileHandler;
import com.conferences.mapper.IMapper;
import com.conferences.model.FileFormData;
import com.conferences.model.FormError;
import com.conferences.service.abstraction.IMeetingService;
import com.conferences.utils.FileUtil;
import com.conferences.validator.IValidator;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class CreateCommand extends FrontCommand {

    private static final String MEETINGS_IMAGES = "/resources/images/meetings/";
    private static final String DEFAULT_IMAGE_PATH = "__default__.jpg";

    private IFileHandler fileHandler;
    private IMeetingService meetingService;
    private IMapper<HttpServletRequest, FileFormData<Meeting>> fileFormMeetingMapper;

    @Override
    public void init(ServletContext context, HttpServletRequest request, HttpServletResponse response, List<String> urlParams) throws IOException {
        super.init(context, request, response, urlParams);
        this.fileHandler = HandlerFactory.getInstance().getFileHandler();
        this.meetingService = ServiceFactory.getInstance().getMeetingService();
        this.fileFormMeetingMapper = MapperFactory.getInstance().getRequestToFileFormMeetingMapper();
    }

    @Override
    public void process() throws ServletException, IOException {
        FileFormData<Meeting> data = fileFormMeetingMapper.map(request);
        if (data != null) {
            Meeting meeting = data.getItem();
            String imageExtension = "." + FileUtil.getFileExtension(meeting.getImagePath());
            meeting.setImagePath(generateMeetingImagePath() + imageExtension);
            List<FormError> errors = new ArrayList<>();
            if (!fileHandler.saveFile(data.getFileItems(), context.getRealPath(MEETINGS_IMAGES), meeting.getImagePath())) {
                meeting.setImagePath(DEFAULT_IMAGE_PATH);
                errors.add(new FormError(ErrorKey.IMAGE_LOADING_FAILED));
            }
            List<FormError> meetingsErrors = meetingService.saveMeeting(meeting);
            errors.addAll(meetingsErrors);
            if (!errors.isEmpty()) {
                saveErrorsToSession(FormKeys.MEETING_ERRORS, meetingsErrors);
                Map<String, String> meetingFields = new HashMap<>();
                meetingFields.put("title", meeting.getTitle());
                meetingFields.put("address", meeting.getAddress());
                meetingFields.put("description", meeting.getDescription());
                saveFieldValuesToSession(FormKeys.MEETING_FIELDS, meetingFields);
                redirectBack();
                return;
            }

            redirect(Page.MEETING.getUrl() + meeting.getId());
        } else {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST);
        }
    }

    private String generateMeetingImagePath() {
        User user = (User) request.getSession().getAttribute(Defaults.USER.toString());
        String filename = Defaults.MEETING_IMAGE_PREFIX + "_" + LocalDateTime.now() + "_" + user.getId();
        return FileUtil.removeFileForbiddenSymbols(filename);
    }
}
