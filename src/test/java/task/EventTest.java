package task;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

public class EventTest {

    @Test
    @SuppressWarnings("checkstyle:AvoidEscapedUnicodeCharacters")
    public void testStringConversion() {
        assertEquals("[E][\u2718] do the homework (at: Thu May 02 18:00:00 SGT 2019 - Thu May 02 19:00:00 SGT 2019)",
                new Event("do the homework", "02/05/2019 1800", "02/05/2019 1900").toString());
    }

    @Test
    @SuppressWarnings("checkstyle:AvoidEscapedUnicodeCharacters")
    public void testStorageStringConversion() {
        assertEquals("E | 0 | do the homework | 02/05/2019 1800 | 02/05/2019 1900",
                new Event("do the homework", "02/05/2019 1800", "02/05/2019 1900").toStorageString());
    }

    @Test
    @SuppressWarnings({"checkstyle:AvoidEscapedUnicodeCharacters", "checkstyle:LineLength"})
    public void toString_noHourInfo_exceptionThrown() {
        try {
            assertEquals("[E][\u2718] do the homework (at: Thu May 02 18:00:00 SGT 2019 - Thu May 02 19:00:00 SGT 2019)",
                    new Event("do the homework", "02/05/2019", "02/05/2019").toString());
            fail();
        } catch (Exception e) {
            assertEquals("☹ OOPS!!! Incorrect time format.", e.getMessage());
        }
    }
}
