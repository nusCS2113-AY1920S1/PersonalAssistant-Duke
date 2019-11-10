package unit;

import spinbox.DateTime;

import org.junit.jupiter.api.Test;
import spinbox.exceptions.DateFormatException;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class  DateTimeTest {

    @Test
    public void dateTimeCreation_variousDateTimeString_successfulCreationAndExpectedStringOutput()
            throws DateFormatException {
        DateTime test = new DateTime("05/02/2020 12:00");
        assertEquals("05/02/2020 12:00", test.toString());

        DateTime test2 = new DateTime(test.toString());
        assertEquals("05/02/2020 12:00", test2.toString());


        DateTime test3 = new DateTime("06/12/2300 17:00");
        assertEquals("06/12/2300 17:00", test3.toString());

        DateTime test4 = new DateTime(test3.toString());
        assertEquals("06/12/2300 17:00", test4.toString());
    }

    @Test
    public void dateTimeCreation_differentlyFormattedDateTimeString_successfulCreationAndExpectedStringOutput()
            throws DateFormatException {
        DateTime test = new DateTime("the day before 12/16/2019 4pm");
        assertEquals("12/15/2019 16:00", test.toString());
    }

    @Test
    public void getDayOfMonth_DateTimeString_expectedDay() throws DateFormatException {
        DateTime test = new DateTime("10/16/2019 12:33");
        assertEquals(16, test.getDayOfMonth());
    }

    @Test
    public void getDayOfWeek_DateTimeString_expectedDay() throws DateFormatException {
        DateTime test = new DateTime("10/16/2019 12:33");
        assertEquals(4, test.getDayOfWeek());
    }

    @Test
    public void getHour_DateTimeString_expectedHour() throws DateFormatException {
        String date = "10/16/2019 12:33";
        DateTime test = new DateTime(date);
        assertEquals(12, test.getHour());
    }

    @Test
    public void getStartOfTheWeek_DateTimeString_expectedDateTimeString() throws DateFormatException {
        String date = "10/16/2019";
        DateTime test = new DateTime(date + " 00:00");
        assertEquals("10/13/2019 00:00", test.getStartOfTheWeek().toString());
    }

    @Test
    public void getEndOfTheWeek_DateTimeString_expectedDateTimeString() throws DateFormatException {
        String date = "10/16/2019";
        DateTime test = new DateTime(date + " 23:59");
        assertEquals("10/19/2019 23:59", test.getEndOfTheWeek().toString());
    }

    @Test
    public void getStartOfTheMonth_DateTimeString_expectedDateTimeString() throws DateFormatException {
        String date = "10/16/2019";
        DateTime test = new DateTime(date + " 00:00");
        assertEquals("10/01/2019 00:00", test.getStartOfTheMonth().toString());
    }

    @Test
    public void getEndOfTheMonth_DateTimeString_expectedDateTimeString() throws DateFormatException {
        String date = "10/16/2019";
        DateTime test = new DateTime(date + " 23:59");
        assertEquals("10/31/2019 23:59", test.getEndOfTheMonth().toString());
    }
}
