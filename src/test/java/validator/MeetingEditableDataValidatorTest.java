package validator;

import com.conferences.config.ErrorKey;
import com.conferences.entity.Meeting;
import com.conferences.model.FormError;
import com.conferences.validator.MeetingEditableDataValidator;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import java.time.LocalDateTime;
import java.util.List;

import static org.junit.Assert.*;

public class MeetingEditableDataValidatorTest {

    private static MeetingEditableDataValidator validator;
    private Meeting meeting;

    @BeforeClass
    public static void beforeTest() {
        validator = new MeetingEditableDataValidator();
    }

    @Before
    public void before() {
        meeting = new Meeting();
        meeting.setAddress("");
        meeting.setDate(LocalDateTime.now().plusDays(1));
    }

    @Test
    public void shouldAddErrorsForEmptyAddress() {
        List<FormError> errors = validator.validate(meeting);
        boolean requiredFieldErrorPresent = errors.stream().anyMatch(error -> error.getErrorKey() == ErrorKey.FIELD_MINIMUM_LENGTH);
        assertTrue(requiredFieldErrorPresent);
    }

    @Test
    public void shouldAddErrorsForEmptyDate() {
        meeting.setDate(null);
        List<FormError> errors = validator.validate(meeting);
        boolean futureDateErrorPresence = errors.stream().anyMatch(error -> error.getErrorKey() == ErrorKey.FIELD_MINIMUM_LENGTH);;
        assertTrue(futureDateErrorPresence);
    }

    @Test
    public void shouldAddErrorIfAddressDoesNotHaveMinimumLength() {
        meeting.setAddress("test");
        List<FormError> errors = validator.validate(meeting);
        boolean hasMinimumLengthError = errors.stream().anyMatch(error -> error.getErrorKey() == ErrorKey.FIELD_MINIMUM_LENGTH);
        assertTrue(hasMinimumLengthError);
    }

    @Test
    public void shouldAddNoErrorsForValidMeeting() {
        meeting.setAddress("valid address");
        List<FormError> errors = validator.validate(meeting);
        assertEquals(0, errors.size());
    }

}
