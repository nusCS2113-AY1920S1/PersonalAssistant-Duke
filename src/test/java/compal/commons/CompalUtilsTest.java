package compal.commons;

import org.junit.jupiter.api.Test;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import static org.junit.jupiter.api.Assertions.assertEquals;

//@@author yueyeah
public class CompalUtilsTest {
    @Test
    void util_convertValidStringToDate_success() {
        String testDateStr = "01/01/2019";
        Date expectedDate = new GregorianCalendar(2019, Calendar.JANUARY, 1).getTime();
        Date testDate = CompalUtils.stringToDate(testDateStr);
        assertEquals(expectedDate, testDate);
    }

    @Test
    void util_convertValidDateToString_success() {
        Date testDate = new GregorianCalendar(2019, Calendar.FEBRUARY, 1).getTime();
        String testDateStr = CompalUtils.dateToString(testDate);
        String expectedDateStr = "01/02/2019";
        assertEquals(expectedDateStr, testDateStr);
    }

    @Test
    void util_timeIsInSequence_success() {
        String startTime = "1800";
        String endTime = "1900";
        boolean startTimeBeforeEndTime = CompalUtils.isTimeInSequence(startTime, endTime);
        assertEquals(true, startTimeBeforeEndTime);
    }

    @Test
    void util_incrementDateByCorrectNumberOfDays_success() {
        Date testDate = new GregorianCalendar(2019, Calendar.MARCH, 15).getTime();
        Date expectedDate = new GregorianCalendar(2019, Calendar.MARCH, 22).getTime();
        Date newtestDate = CompalUtils.incrementDateByDays(testDate, 7);
        assertEquals(newtestDate, expectedDate);
    }
}
