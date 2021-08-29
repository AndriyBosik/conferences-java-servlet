package com.conferences.service.implementation;

import com.conferences.dao.abstraction.IRoleDao;
import com.conferences.dao.abstraction.IUserDao;
import com.conferences.dao.implementation.RoleDao;
import com.conferences.dao.implementation.UserDao;
import com.conferences.entity.User;
import com.conferences.service.abstraction.IUserService;
import com.conferences.validator.IValidator;
import com.conferences.validator.UserValidator;

import java.util.List;

public class UserService implements IUserService {

    private final IUserDao userDao;
    private final IValidator<User> userValidator;

    public UserService() {
        userDao = new UserDao();
        userValidator = new UserValidator();
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

    private boolean hasAllowedRole(User user) {
        return user.getRole() != null && !"moderator".equals(user.getRole().getTitle());
    }
}
