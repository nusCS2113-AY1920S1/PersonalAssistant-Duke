package task;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

public class DeadlineTest {

    @Test
    @SuppressWarnings("checkstyle:AvoidEscapedUnicodeCharacters")
    public void testStringConversion() {
        Deadline testDeadline = new Deadline("do the homework", "02/05/2019 1800");
        assertEquals("[D][\u2718] do the homework (by: 02/05/2019 1800)(NONE)",
                testDeadline.toString());

        testDeadline.setDoAfterDate("01/05/2019 1800");
        assertEquals("[D][\u2718] do the homework (after: 01/05/2019 1800) (by: 02/05/2019 1800)(NONE)",
                testDeadline.toString());

        testDeadline.markAsDone();
        assertEquals("[D][\u2713] do the homework (after: 01/05/2019 1800) (by: 02/05/2019 1800)(NONE)",
                testDeadline.toString());
    }

    @Test
    public void testStorageStringConversion() {

        assertEquals("D | 0 | 0 | do the homework | null | 02/05/2019 1800 | NONE",
                new Deadline("do the homework", "02/05/2019 1800").toStorageString());
      
        Deadline testDeadline = new Deadline("do the homework", "02/05/2019 1800 | NONE");
        assertEquals("D | 0 | 0 | do the homework | null | 02/05/2019 1800 | NONE",
                testDeadline.toStorageString());

        testDeadline.setDoAfterDate("01/05/2019 1800");
        assertEquals("D | 0 | 0 | do the homework | 01/05/2019 1800 | 02/05/2019 1800 | NONE",
                testDeadline.toStorageString());

        testDeadline.markAsDone();
        assertEquals("D | 1 | 0 | do the homework | 01/05/2019 1800 | 02/05/2019 1800 | NONE",
                testDeadline.toStorageString());
    }



    @Test
    @SuppressWarnings("checkstyle:AvoidEscapedUnicodeCharacters")
    public void toString_noHourInfo_exceptionThrown() {
        try {
            assertEquals("[D][\u2718] do the homework (by: 02/05/2019) ",
                    new Deadline("do the homework", "02/05/2019").toString());
            fail();
        } catch (Exception e) {
            assertEquals("☹ OOPS!!! Incorrect time format.", e.getMessage());
        }
    }
}
