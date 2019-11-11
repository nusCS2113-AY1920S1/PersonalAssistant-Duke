package utiltests;

import org.junit.jupiter.api.Test;
import duke.util.DateHandler;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;


class DateHandlerTest {
    int day = 23;
    int month = 3;
    int year = 2019;

    @Test
    void testStringDate() {
        String format = "yyyy MMM dd";
        String changedDate = DateHandler.stringDate(format, day, month, year);
        assertEquals("2019 Mar 23", changedDate);
    }

    @Test
    void testDateCheck() {
        boolean dateExists;
        dateExists = DateHandler.dateCheck(day, month);
        assertTrue(dateExists);

        //test if Feb 40th is real date
        dateExists = DateHandler.dateCheck(40, 2);
        assertFalse(dateExists);

        //test if month 15 is real date
        dateExists = DateHandler.dateCheck(day, 15);
        assertFalse(dateExists);
    }

    @Test
    void testCorrectTime() {
        String timeFormat = "HHmm";
        String time = "0900";
        boolean correctTime = DateHandler.correctTime(timeFormat, time);
        assertTrue(correctTime);

        //test if time is wrong
        correctTime = DateHandler.correctTime(timeFormat, "10pm");
        assertFalse(correctTime);
    }

    @Test
    void testDateFormatter() {
        String newFormat = "dd MM yy";
        String date = "2019-09-12";
        String oldFormat = "yyyy-MM-dd";

        String newDate = DateHandler.dateFormatter(oldFormat, newFormat, date);
        assertEquals("12 09 19", newDate);

        String error = DateHandler.dateFormatter(oldFormat, newFormat, "year 1990");
        assertEquals("Error", error);
    }

}
