package com.conferences.handler.abstraction;

import com.conferences.entity.User;
import com.conferences.model.UserData;

public interface IUserDataSaver {

    UserData saveUserData(User user);

    void restoreUserData(User user, UserData data);

}
