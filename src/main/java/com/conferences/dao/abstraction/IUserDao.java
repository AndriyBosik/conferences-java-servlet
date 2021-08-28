package com.conferences.dao.abstraction;

import com.conferences.entity.User;

import java.util.List;

public interface IUserDao extends IDao<Integer, User> {

    User findByLoginAndPasswordWithRole(String login, String password);

    List<User> findAllByRole(String role);
}
