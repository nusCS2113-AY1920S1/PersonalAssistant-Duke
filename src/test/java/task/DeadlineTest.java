package task;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

public class DeadlineTest {

    @Test
    @SuppressWarnings("checkstyle:AvoidEscapedUnicodeCharacters")
    public void testStringConversion() {
        assertEquals("[D][\u2718] do the homework (by: Thu May 02 18:00:00 SGT 2019)",
                new Deadline("do the homework", "02/05/2019 1800").toString());
    }

    @Test

    public void testStorageStringConversion() {
        assertEquals("D | 0 | do the homework | Thu May 02 18:00:00 SGT 2019",
                new Deadline("do the homework", "02/05/2019 1800").toStorageString());
    }

    @Test
    @SuppressWarnings("checkstyle:AvoidEscapedUnicodeCharacters")
    public void toString_noHourInfo_exceptionThrown() {
        try {
            assertEquals("[D][\u2718] do the homework (by: Thu May 02 18:00:00 SGT 2019)",
                    new Deadline("do the homework", "02/05/2019").toString());
            fail();
        } catch (Exception e) {
            assertEquals("☹ OOPS!!! Incorrect time format.", e.getMessage());
        }
    }
}
