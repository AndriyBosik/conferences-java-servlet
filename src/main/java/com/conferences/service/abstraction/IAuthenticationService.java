package com.conferences.service.abstraction;

import com.conferences.entity.User;
import com.conferences.model.FormError;

import java.util.List;

public interface IAuthenticationService {

    User getByLoginAndPasswordWithRole(String login, String password);

    List<FormError> signUpUser(User user);
}
