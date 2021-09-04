package com.conferences.command.users;

import com.conferences.command.FrontCommand;
import com.conferences.config.Defaults;
import com.conferences.config.ErrorKey;
import com.conferences.config.FormKeys;
import com.conferences.entity.User;
import com.conferences.factory.HandlerFactory;
import com.conferences.factory.MapperFactory;
import com.conferences.factory.ServiceFactory;
import com.conferences.handler.abstraction.IFileHandler;
import com.conferences.handler.implementation.FileHandler;
import com.conferences.mapper.IMapper;
import com.conferences.mapper.RequestToFileFormMeetingMapper;
import com.conferences.mapper.SimpleFileFormMapper;
import com.conferences.model.FileFormData;
import com.conferences.model.FormError;
import com.conferences.service.abstraction.IUserService;
import com.conferences.service.implementation.UserService;
import com.conferences.utils.FileUtil;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class ChangeAvatarCommand extends FrontCommand {

    private static final String AVATARS_IMAGES = "/resources/images/avatars/";

    private IUserService userService;
    private IFileHandler fileHandler;
    private IMapper<HttpServletRequest, FileFormData<Map<String, String>>> mapper;

    @Override
    public void init(ServletContext context, HttpServletRequest request, HttpServletResponse response, List<String> urlParams) {
        super.init(context, request, response, urlParams);
        userService = ServiceFactory.getInstance().getUserService();
        fileHandler = HandlerFactory.getInstance().getFileHandler();
        mapper = MapperFactory.getInstance().getSimpleFileFormMapper();
    }

    @Override
    public void process() throws ServletException, IOException {
        User user = (User) request.getSession().getAttribute(Defaults.USER.toString());
        FileFormData<Map<String, String>> data = mapper.map(request);
        String fileExtension = "." + FileUtil.getFileExtension(data.getItem().get("avatar"));
        String avatarPath = generateUserAvatarName(user) + fileExtension;
        List<FormError> errors = new ArrayList<>();
        if (fileHandler.saveFile(data.getFileItems(), context.getRealPath(AVATARS_IMAGES), avatarPath)) {
            user.setImagePath(avatarPath);
            if (!userService.updateUserImagePath(user)) {
                errors.add(new FormError(ErrorKey.IMAGE_LOADING_FAILED));
            }
        } else {
            errors.add(new FormError(ErrorKey.IMAGE_LOADING_FAILED));
        }
        saveErrorsToSession(FormKeys.AVATAR_ERRORS, errors);
        redirectBack();
    }

    private String generateUserAvatarName(User user) {
        String filename = Defaults.USER_AVATAR_PREFIX + "_" + LocalDateTime.now() + "_" + user.getId();
        return FileUtil.removeFileForbiddenSymbols(filename);
    }
}
