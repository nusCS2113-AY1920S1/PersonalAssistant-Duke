package utiltests;

import org.junit.jupiter.api.Test;
import duke.util.DateHandler;

import static org.junit.jupiter.api.Assertions.assertEquals;


class DateChangerTest {

    @Test
    void testDate() {
        String format = "yyyy MMM dd";
        int day = 23;
        int month = 3;
        int year = 2019;
        String changedDate = DateHandler.stringDate(format, day, month, year);
        assertEquals("2019 Mar 23", changedDate);
    }

}
