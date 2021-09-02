package com.conferences.handler.implementation;

import com.conferences.entity.User;
import com.conferences.handler.abstraction.IUserDataSaver;
import com.conferences.model.UserData;

public class UserDataSaver implements IUserDataSaver {

    @Override
    public UserData saveUserData(User user) {
        UserData data = new UserData();
        data.setLogin(user.getLogin());
        data.setPassword(user.getPassword());
        data.setEmail(user.getEmail());
        data.setName(user.getName());
        data.setSurname(user.getSurname());
        return data;
    }

    @Override
    public void restoreUserData(User user, UserData data) {
        user.setLogin(data.getLogin());
        user.setPassword(data.getPassword());
        user.setEmail(data.getEmail());
        user.setName(data.getName());
        user.setSurname(data.getSurname());
    }
}
