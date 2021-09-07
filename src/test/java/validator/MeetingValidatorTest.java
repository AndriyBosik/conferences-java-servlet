package validator;

import com.conferences.entity.Meeting;
import com.conferences.model.FormError;
import com.conferences.validator.MeetingValidator;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import java.time.LocalDateTime;
import java.util.List;

import static org.junit.Assert.*;

public class MeetingValidatorTest {

    private static MeetingValidator meetingValidator;

    private Meeting meeting;

    @BeforeClass
    public static void beforeTest() {
        meetingValidator = new MeetingValidator();
    }

    @Before
    public void before() {
        meeting = new Meeting();
        meeting.setTitle("Meeting title");
        meeting.setDescription("Meeting description");
        meeting.setAddress("Meeting address");
        meeting.setDate(LocalDateTime.now().plusDays(1));
    }

    @Test
    public void shouldReturnZeroErrorsForValidMeeting() {
        List<FormError> errors = meetingValidator.validate(meeting);
        assertEquals(0, errors.size());
    }

    @Test
    public void shouldReturnErrorsForMeetingWithoutTitle() {
        meeting.setTitle("");
        List<FormError> errors = meetingValidator.validate(meeting);
        assertNotEquals(0, errors.size());
    }

    @Test
    public void shouldReturnErrorsForMeetingDayBeforeNow() {
        meeting.setDate(meeting.getDate().minusYears(1));
        List<FormError> errors = meetingValidator.validate(meeting);
        assertNotEquals(0, errors.size());
    }

}
