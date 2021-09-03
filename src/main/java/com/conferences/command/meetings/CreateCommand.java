package com.conferences.command.meetings;

import com.conferences.command.FrontCommand;
import com.conferences.config.Defaults;
import com.conferences.config.Page;
import com.conferences.entity.Meeting;
import com.conferences.entity.User;
import com.conferences.factory.HandlerFactory;
import com.conferences.factory.MapperFactory;
import com.conferences.factory.ServiceFactory;
import com.conferences.handler.abstraction.IFileHandler;
import com.conferences.mapper.IMapper;
import com.conferences.model.FileFormData;
import com.conferences.service.abstraction.IMeetingService;
import com.conferences.utils.FileUtil;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.time.LocalDateTime;

public class CreateCommand extends FrontCommand {

    private static final String MEETINGS_IMAGES = "/resources/images/meetings/";

    private final IFileHandler fileHandler;
    private final IMeetingService meetingService;
    private final IMapper<HttpServletRequest, FileFormData<Meeting>> fileFormMeetingMapper;

    public CreateCommand() {
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

            if (meetingService.saveMeeting(meeting)) {
                if (!fileHandler.saveFile(data.getFileItems(), context.getRealPath(MEETINGS_IMAGES), meeting.getImagePath())) {
                    // TODO(update meeting image_path to default image)
                }
                redirect(Page.MEETING.getUrl() + meeting.getId());
            } else {
                // TODO(fill cookies with inputted data and redirect with error message)
            }
        } else {
            // TODO(Bad request)
        }

//        response.sendRedirect(Pages.MEETINGS_LIST.toString());
    }

    private String generateMeetingImagePath() {
        User user = (User) request.getSession().getAttribute(Defaults.USER.toString());
        String filename = Defaults.MEETING_IMAGE_PREFIX + "_" + LocalDateTime.now() + "_" + user.getId();
        return FileUtil.removeFileForbiddenSymbols(filename);
    }
}
