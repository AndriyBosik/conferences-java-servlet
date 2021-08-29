package com.conferences.validator;

import com.conferences.entity.User;

import java.util.regex.Pattern;

public class UserValidator implements IValidator<User> {

    private static final String EMAIL_REGEX = "^[\\w-\\.]+@([\\w-]+\\.)+[\\w-]{2,4}$";
    private static final Pattern EMAIL_PATTERN = Pattern.compile(EMAIL_REGEX);

    @Override
    public boolean isValid(User model) {
        return  model.getLogin() != null && model.getLogin().length() >= 4 &&
                model.getPassword() != null && model.getPassword().length() >= 5 &&
                model.getSurname() != null && model.getSurname().length() >= 2 &&
                model.getName() != null && model.getName().length() >= 2 &&
                model.getEmail() != null && isCorrectEmail(model.getEmail());
    }

    private boolean isCorrectEmail(String email) {
        return EMAIL_PATTERN.matcher(email).matches();
    }
}
