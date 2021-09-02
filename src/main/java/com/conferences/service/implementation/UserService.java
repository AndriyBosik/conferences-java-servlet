package com.conferences.service.implementation;

import com.conferences.dao.abstraction.IRoleDao;
import com.conferences.dao.abstraction.IUserDao;
import com.conferences.dao.implementation.RoleDao;
import com.conferences.dao.implementation.UserDao;
import com.conferences.entity.User;
import com.conferences.service.abstraction.IUserService;
import com.conferences.validator.IValidator;
import com.conferences.validator.UserRequiredForUpdateDataValidator;
import com.conferences.validator.UserValidator;

import java.util.List;

public class UserService implements IUserService {

    private final IUserDao userDao;
    private final IValidator<User> userValidator;
    private final IValidator<User> userRequiredForUpdateDataValidator;

    public UserService() {
        userDao = new UserDao();
        userValidator = new UserValidator();
        userRequiredForUpdateDataValidator = new UserRequiredForUpdateDataValidator();
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
