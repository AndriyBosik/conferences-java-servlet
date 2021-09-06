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
import com.conferences.mapper.IMapper;
import com.conferences.model.FileFormData;
import com.conferences.model.FormError;
import com.conferences.service.abstraction.IUserService;
import com.conferences.utils.FileUtil;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

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

    private static final Logger LOGGER = LogManager.getLogger(ChangeAvatarCommand.class);
    private static final String AVATARS_IMAGES = "/resources/images/avatars/";

    private IUserService userService;
    private IFileHandler fileHandler;
    private IMapper<HttpServletRequest, FileFormData<Map<String, String>>> mapper;

    @Override
    public void init(ServletContext context, HttpServletRequest request, HttpServletResponse response, List<String> urlParams) throws IOException {
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
        LOGGER.info("Saving user avatar");
        if (fileHandler.saveFile(data.getFileItems(), context.getRealPath(AVATARS_IMAGES), avatarPath)) {
            LOGGER.info("Avatar saved");
            user.setImagePath(avatarPath);
            if (!userService.updateUserImagePath(user)) {
                errors.add(new FormError(ErrorKey.IMAGE_LOADING_FAILED));
            }
        } else {
            LOGGER.info("Avatar did not save");
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
