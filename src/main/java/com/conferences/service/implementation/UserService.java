package com.conferences.service.implementation;

import com.conferences.dao.abstraction.IUserDao;
import com.conferences.dao.implementation.UserDao;
import com.conferences.model.User;
import com.conferences.service.abstraction.IUserService;

public class UserService implements IUserService {

    private IUserDao userDao;

    public UserService() {
        userDao = new UserDao();
    }

    @Override
    public User getByLoginAndPasswordWithRole(String login, String password) {
        return userDao.findByLoginAndPasswordWithRole(login, password);
    }
}
