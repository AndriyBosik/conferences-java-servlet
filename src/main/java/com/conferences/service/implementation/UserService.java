package com.conferences.service.implementation;

import com.conferences.dao.abstraction.IUserDao;
import com.conferences.dao.implementation.UserDao;
import com.conferences.entity.User;
import com.conferences.service.abstraction.IUserService;

import java.util.List;

public class UserService implements IUserService {

    private IUserDao userDao;

    public UserService() {
        userDao = new UserDao();
    }

    @Override
    public User getByLoginAndPasswordWithRole(String login, String password) {
        return userDao.findByLoginAndPasswordWithRole(login, password);
    }

    @Override
    public List<User> getUsersByRoleTitleWithRole(String roleTitle) {
        return userDao.findAllByRole(roleTitle);
    }
}
