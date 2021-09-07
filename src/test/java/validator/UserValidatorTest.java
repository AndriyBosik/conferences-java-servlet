package validator;

import com.conferences.config.ErrorKey;
import com.conferences.entity.User;
import com.conferences.validator.UserValidator;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import static org.junit.Assert.*;

public class UserValidatorTest {

    private static UserValidator validator;

    private User user;

    @BeforeClass
    public static void beforeTest() {
        validator = new UserValidator();
    }

    @Before
    public void before() {
        user = new User();
        user.setLogin("valid login");
        user.setName("name");
        user.setSurname("surname");
        user.setEmail("email@example.com");
        user.setPassword("pass");
    }

    @Test
    public void shouldAddFieldMinimumLengthErrorForNotValidPassword() {
        boolean isFieldMinimumLengthErrorPresent = validator.validate(user).stream()
            .anyMatch(error -> error.getErrorKey() == ErrorKey.FIELD_MINIMUM_LENGTH);
        assertTrue(isFieldMinimumLengthErrorPresent);
    }

    @Test
    public void shouldAddRequiredFieldErrorForNullPassword() {
        user.setPassword(null);
        boolean isRequiredFieldErrorPresent = validator.validate(user).stream()
            .anyMatch(error -> error.getErrorKey() == ErrorKey.REQUIRED_FIELD);
        assertTrue(isRequiredFieldErrorPresent);
    }

    @Test
    public void shouldAddNoErrorsForValidUser() {
        user.setPassword("password");
        assertEquals(0, validator.validate(user).size());
    }
}
