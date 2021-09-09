package com.conferences.service.implementation;

import com.conferences.config.ErrorKey;
import com.conferences.dao.abstraction.IUserDao;
import com.conferences.entity.User;
import com.conferences.factory.DaoFactory;
import com.conferences.factory.HandlerFactory;
import com.conferences.factory.ValidatorFactory;
import com.conferences.handler.abstraction.IEncryptor;
import com.conferences.model.FormError;
import com.conferences.service.abstraction.IAuthService;
import com.conferences.validator.IValidator;

import java.util.ArrayList;
import java.util.List;

/**
 * {@inheritDoc}
 */
public class AuthService implements IAuthService {

    private final IEncryptor encryptor;
    private final IUserDao userDao;
    private final IValidator<User> userValidator;

    public AuthService() {
        encryptor = HandlerFactory.getInstance().getEncryptor();
        userDao = DaoFactory.getInstance().getUserDao();
        userValidator = ValidatorFactory.getInstance().getUserValidator();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public User getByLoginAndPasswordWithRole(String login, String password) {
        User user = userDao.findByLoginWithRole(login);
        if (user != null && encryptor.check(password, user.getPassword())) {
            return user;
        }
        return null;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<FormError> signUpUser(User user) {
        List<FormError> errors = new ArrayList<>();
        User dbUser = userDao.findByLoginOrEmail(user.getLogin(), user.getEmail());
        if (dbUser != null) {
            errors.add(new FormError(ErrorKey.EXISTING_USER));
        }
        if (!hasAllowedRoleForSigningUp(user)) {
            errors.add(new FormError(ErrorKey.INVALID_ROLE));
        }
        List<FormError> validationErrors = userValidator.validate(user);
        errors.addAll(validationErrors);
        if (!errors.isEmpty()) {
            return errors;
        }
        user.setPassword(encryptor.encrypt(user.getPassword()));
        if (!userDao.create(user)) {
            errors.add(new FormError(ErrorKey.REGISTRATION_ERROR));
        }
        return errors;
    }

    /**
     * <p>
     *     Checks whatever user has allowed role for signing up
     * </p>
     * @param user user with his role
     * @return true if user's role is allowed for signing up, false otherwise
     */
    private boolean hasAllowedRoleForSigningUp(User user) {
        return user.getRole() != null && !"moderator".equals(user.getRole().getTitle());
    }
}
