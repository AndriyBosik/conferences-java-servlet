package validator;

import com.conferences.config.ErrorKey;
import com.conferences.entity.User;
import com.conferences.validator.UserRequiredForUpdateDataValidator;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import static org.junit.Assert.*;

public class UserRequiredForUpdateDataValidatorTest {

    private static UserRequiredForUpdateDataValidator validator;

    private User user;

    @BeforeClass
    public static void beforeTest() {
        validator = new UserRequiredForUpdateDataValidator();
    }

    @Before
    public void before() {
        user = new User();
        user.setLogin("userlogin");
        user.setSurname("bosyk");
        user.setName("andrew");
        user.setEmail("userlogin@example.com");
    }

    @Test
    public void shouldAddNoErrorsForValidUser() {
        assertEquals(0, validator.validate(user).size());
    }

    @Test
    public void shouldAddRequiredFieldErrorForEmptyLogin() {
        user.setLogin("");
        boolean isRequiredFieldErrorPresent = validator.validate(user).stream()
            .anyMatch(error -> error.getErrorKey() == ErrorKey.REQUIRED_FIELD);
        assertTrue(isRequiredFieldErrorPresent);
    }

    @Test
    public void shouldAddFieldMinimumLengthErrorForNotValidLogin() {
        user.setLogin("txt");
        boolean isFieldMinimumLengthErrorPresent = validator.validate(user).stream()
                .anyMatch(error -> error.getErrorKey() == ErrorKey.FIELD_MINIMUM_LENGTH);
        assertTrue(isFieldMinimumLengthErrorPresent);
    }

    @Test
    public void shouldAddRequiredFieldErrorForEmptySurname() {
        user.setSurname("");
        boolean isRequiredFieldErrorPresent = validator.validate(user).stream()
                .anyMatch(error -> error.getErrorKey() == ErrorKey.REQUIRED_FIELD);
        assertTrue(isRequiredFieldErrorPresent);
    }

    @Test
    public void shouldAddFieldMinimumLengthErrorForNotValidSurname() {
        user.setSurname("s");
        boolean isFieldMinimumLengthErrorPresent = validator.validate(user).stream()
                .anyMatch(error -> error.getErrorKey() == ErrorKey.FIELD_MINIMUM_LENGTH);
        assertTrue(isFieldMinimumLengthErrorPresent);
    }

    @Test
    public void shouldAddRequiredFieldErrorForEmptyName() {
        user.setName("");
        boolean isRequiredFieldErrorPresent = validator.validate(user).stream()
                .anyMatch(error -> error.getErrorKey() == ErrorKey.REQUIRED_FIELD);
        assertTrue(isRequiredFieldErrorPresent);
    }

    @Test
    public void shouldAddFieldMinimumLengthErrorForNotValidName() {
        user.setName("s");
        boolean isFieldMinimumLengthErrorPresent = validator.validate(user).stream()
                .anyMatch(error -> error.getErrorKey() == ErrorKey.FIELD_MINIMUM_LENGTH);
        assertTrue(isFieldMinimumLengthErrorPresent);
    }

    @Test
    public void shouldAddRequiredFieldErrorForEmptyEmail() {
        user.setEmail("");
        boolean isRequiredFieldErrorPresent = validator.validate(user).stream()
                .anyMatch(error -> error.getErrorKey() == ErrorKey.REQUIRED_FIELD);
        assertTrue(isRequiredFieldErrorPresent);
    }

    @Test
    public void shouldAddInvalidEmailErrorForNotValidEmail() {
        user.setEmail("email");
        boolean isInvalidEmailErrorPresent = validator.validate(user).stream()
                .anyMatch(error -> error.getErrorKey() == ErrorKey.INVALID_EMAIL);
        assertTrue(isInvalidEmailErrorPresent);
    }

}
