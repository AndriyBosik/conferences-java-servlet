package util;

import com.conferences.util.StringUtil;
import org.junit.Test;

import static org.junit.Assert.*;

public class StringUtilTest {

    @Test
    public void shouldReturnTrueForNullString() {
        assertTrue(StringUtil.isNullOrEmpty(null));
    }

    @Test
    public void shouldReturnTrueForZeroLengthString() {
        assertTrue(StringUtil.isNullOrEmpty(""));
    }

    @Test
    public void shouldReturnTrueForStringContainingOnlySpaces() {
        assertTrue(StringUtil.isNullOrEmpty("         "));
    }

    @Test
    public void shouldReturnFalseForNotEmptyString() {
        assertFalse(StringUtil.isNullOrEmpty("  string  value     "));
    }

    @Test
    public void shouldReturnTrueForArrayOfNullsAndEmptyStrings() {
        assertTrue(StringUtil.isNullOrEmptyAll(null, "", null, "     ", null, null, "   ", null));
    }

    @Test
    public void shouldReturnFalseForArrayContainingOnlyOneNotNullValue() {
        assertFalse(StringUtil.isNullOrEmptyAll(null, null, null, "value", null, null, null));
    }

    @Test
    public void shouldReturnFalseForArrayContainingAllNotNullValues() {
        assertFalse(StringUtil.isNullOrEmptyAll("value", "another value", "one more value"));
    }

    @Test
    public void shouldReturnFalseForDifferentArrayValues() {
        assertFalse(StringUtil.isNullOrEmptyAll("value", null, null, "another value", null, null, "one more value"));
    }

}
