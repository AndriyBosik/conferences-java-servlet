package com.conferences.dao.abstraction;

import com.conferences.entity.User;

public interface IUserDao extends IDao<Integer, User> {

    User findByLoginAndPasswordWithRole(String login, String password);

}
