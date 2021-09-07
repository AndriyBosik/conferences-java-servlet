package util;

import com.conferences.util.TimeUtil;
import org.junit.Test;

import static org.junit.Assert.*;

public class TimeUtilTest {

    @Test
    public void shouldAppendZeroToBeginOfZero() {
        String result = TimeUtil.addZeroToBegin("0");
        assertEquals("00", result);
    }

    @Test
    public void shouldNotAppendZeroToTwoDigitNumber() {
        String result = TimeUtil.addZeroToBegin("10");
        assertEquals("10", result);
    }

}
