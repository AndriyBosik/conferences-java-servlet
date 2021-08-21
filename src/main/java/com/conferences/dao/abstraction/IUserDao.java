package com.conferences.dao.abstraction;

import com.conferences.model.User;

public interface IUserDao extends IDao<Integer, User> {

    User findByLoginAndPasswordWithRole(String login, String password);

}
