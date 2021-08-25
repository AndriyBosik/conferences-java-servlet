package com.conferences.service.abstraction;

import com.conferences.entity.User;

public interface IUserService {

    User getByLoginAndPasswordWithRole(String login, String password);

}
