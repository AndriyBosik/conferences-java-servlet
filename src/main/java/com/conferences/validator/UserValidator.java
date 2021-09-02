package com.conferences.validator;

import com.conferences.entity.User;

public class UserValidator extends UserRequiredForUpdateDataValidator {

    @Override
    public boolean isValid(User model) {
        return  super.isValid(model) &&
                model.getPassword() != null && model.getPassword().length() >= 5;
    }
}
