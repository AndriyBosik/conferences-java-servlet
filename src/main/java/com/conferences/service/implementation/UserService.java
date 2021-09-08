package com.conferences.service.implementation;

import com.conferences.config.ErrorKey;
import com.conferences.dao.abstraction.IUserDao;
import com.conferences.entity.User;
import com.conferences.factory.DaoFactory;
import com.conferences.factory.ValidatorFactory;
import com.conferences.model.FormError;
import com.conferences.service.abstraction.IUserService;
import com.conferences.validator.IValidator;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;

public class UserService implements IUserService {

    private final IUserDao userDao;
    private final IValidator<User> userRequiredForUpdateDataValidator;

    public UserService() {
        userDao = DaoFactory.getInstance().getUserDao();
        userRequiredForUpdateDataValidator = ValidatorFactory.getInstance().getUserRequiredForUpdateDataValidator();
    }

    @Override
    public List<User> getUsersByRoleTitleWithRole(String roleTitle) {
        return userDao.findAllByRole(roleTitle);
    }

    @Override
    public boolean updateUserImagePath(User user) {
        return userDao.updateUserImagePath(user);
    }

    @Override
    public List<FormError> updateUser(User user) {
        List<FormError> errors = userRequiredForUpdateDataValidator.validate(user);
        if (errors.isEmpty() && !userDao.update(user)) {
            errors.add(new FormError(ErrorKey.UPDATING_ERROR));
        }
        return errors;
    }
}
