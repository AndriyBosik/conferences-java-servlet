package validator;

import com.conferences.config.ErrorKey;
import com.conferences.entity.ReportTopic;
import com.conferences.model.FormError;
import com.conferences.validator.ReportTopicValidator;
import org.junit.*;

import java.util.List;

import static org.junit.Assert.*;

public class ReportTopicValidatorTest {

    private static ReportTopicValidator validator;

    private ReportTopic reportTopic;

    @BeforeClass
    public static void beforeTest() {
        validator = new ReportTopicValidator();
    }

    @Before
    public void before() {
        reportTopic = new ReportTopic();
        reportTopic.setTitle("");
    }

    @Test
    public void shouldAddErrorForNullTitle() {
        reportTopic.setTitle(null);
        List<FormError> errors = validator.validate(reportTopic);
        boolean isRequiredFieldErrorPresent = errors.stream().anyMatch(error -> error.getErrorKey() == ErrorKey.REQUIRED_FIELD);
        assertTrue(isRequiredFieldErrorPresent);
    }

    @Test
    public void shouldAddErrorIfTitleDoesNotHaveMinimumLength() {
        reportTopic.setTitle("test");
        List<FormError> errors = validator.validate(reportTopic);
        boolean isFieldMinimumLengthPresent = errors.stream().anyMatch(error -> error.getErrorKey() == ErrorKey.FIELD_MINIMUM_LENGTH);
        assertTrue(isFieldMinimumLengthPresent);
    }

    @Test
    public void shouldAddNoErrorsForValidModel() {
        reportTopic.setTitle("valid title");
        List<FormError> errors = validator.validate(reportTopic);
        assertEquals(0, errors.size());
    }
}
