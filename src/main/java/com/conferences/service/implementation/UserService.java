package com.conferences.service.implementation;

import com.conferences.dao.abstraction.IUserDao;
import com.conferences.entity.User;
import com.conferences.factory.DaoFactory;
import com.conferences.factory.ValidatorFactory;
import com.conferences.service.abstraction.IUserService;
import com.conferences.validator.IValidator;

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
    public boolean signUpUser(User user) {
        User dbUser = userDao.findByLoginOrEmail(user.getLogin(), user.getEmail());
        if (!userValidator.isValid(user) || dbUser != null || !hasAllowedRole(user)) {
            return false;
        }
        if (userValidator.isValid(user)) {
            return userDao.create(user);
        }
        return false;
    }

    @Override
    public boolean updateUserImagePath(User user) {
        return userDao.updateUserImagePath(user);
    }

    @Override
    public boolean updateUser(User user) {
        if (!userRequiredForUpdateDataValidator.isValid(user)) {
            return false;
        }
        return userDao.update(user);
    }

    private boolean hasAllowedRole(User user) {
        return user.getRole() != null && !"moderator".equals(user.getRole().getTitle());
    }
}
