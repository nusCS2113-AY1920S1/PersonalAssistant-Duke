import spinbox.DateTime;
import spinbox.exceptions.SpinBoxException;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class DateTimeTest {

    @Test
    public void correctDateInputShouldCreateReusableString() throws SpinBoxException {
        DateTime test = new DateTime("05/02/2020 12:00");
        assertEquals("05/02/2020 12:00", test.toString());

        DateTime test2 = new DateTime(test.toString());
        assertEquals("05/02/2020 12:00", test2.toString());


        DateTime test3 = new DateTime("06/12/2300 5pm");
        assertEquals("06/12/2300 17:00", test3.toString());

        DateTime test4 = new DateTime(test3.toString());
        assertEquals("06/12/2300 17:00", test4.toString());
    }

    @Test
    public void parseDifferentDateFormattedString() throws SpinBoxException {
        DateTime test = new DateTime("the day before 12/16/2019 4pm");
        assertEquals("12/15/2019 16:00", test.toString());

        DateTime test2 = new DateTime("16 December 1800 8am to 15th January 2019 9pm", 0);
        DateTime test3 = new DateTime("16 December 1800 8am to 15th January 2019 9pm",1);
        assertEquals("12/16/1800 08:00", test2.toString());
        assertEquals("01/15/2019 21:00", test3.toString());


        DateTime santaVacationStartDate = new DateTime("from christmas 2019 6am to christmas eve 2020 23:59", 0);
        DateTime santaVacationEndDate = new DateTime("from christmas 2019 6am to christmas eve 2020 23:59", 1);
        assertEquals("12/25/2019 06:00", santaVacationStartDate.toString());
        assertEquals("12/24/2020 23:59", santaVacationEndDate.toString());
    }
}
