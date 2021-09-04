package com.conferences.service.implementation;

import com.conferences.config.ErrorKey;
import com.conferences.dao.abstraction.IUserDao;
import com.conferences.entity.User;
import com.conferences.factory.DaoFactory;
import com.conferences.factory.ValidatorFactory;
import com.conferences.model.FormError;
import com.conferences.service.abstraction.IUserService;
import com.conferences.validator.IValidator;

import java.util.ArrayList;
import java.util.List;

public class UserService implements IUserService {

    private final IUserDao userDao;
    private final IValidator<User> userValidator;
    private final IValidator<User> userRequiredForUpdateDataValidator;

    public UserService() {
        userDao = DaoFactory.getInstance().getUserDao();
        userValidator = ValidatorFactory.getInstance().getUserValidator();
        userRequiredForUpdateDataValidator = ValidatorFactory.getInstance().getUserRequiredForUpdateDataValidator();
    }

    @Override
    public User getByLoginAndPasswordWithRole(String login, String password) {
        return userDao.findByLoginAndPasswordWithRole(login, password);
    }

    @Override
    public List<User> getUsersByRoleTitleWithRole(String roleTitle) {
        return userDao.findAllByRole(roleTitle);
    }

    @Override
    public List<FormError> signUpUser(User user) {
        List<FormError> errors = new ArrayList<>();
        User dbUser = userDao.findByLoginOrEmail(user.getLogin(), user.getEmail());
        if (dbUser != null) {
            errors.add(new FormError(ErrorKey.EXISTING_USER));
        }
        if (!hasAllowedRole(user)) {
            errors.add(new FormError(ErrorKey.INVALID_ROLE));
        }
        List<FormError> validationErrors = userValidator.validate(user);
        errors.addAll(validationErrors);
        if (!errors.isEmpty()) {
            return errors;
        }
        if (!userDao.create(user)) {
            errors.add(new FormError(ErrorKey.REGISTRATION_ERROR));
        }
        return errors;
    }

    @Override
    public boolean updateUserImagePath(User user) {
        return userDao.updateUserImagePath(user);
    }

    @Override
    public List<FormError> updateUser(User user) {
        List<FormError> errors = userRequiredForUpdateDataValidator.validate(user);
        if (errors.isEmpty() && !userDao.update(user)) {
            errors.add(new FormError(ErrorKey.UPDATING_ERROR));
        }
        return errors;
    }

    private boolean hasAllowedRole(User user) {
        return user.getRole() != null && !"moderator".equals(user.getRole().getTitle());
    }
}
