import duke.DateTime;
import duke.exceptions.DukeException;
import org.junit.jupiter.api.Test;

import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class DateTimeTest {

    @Test
    public void correctDateInputShouldCreateReusableString() throws DukeException {

        com.joestelmach.natty.Parser parser = new com.joestelmach.natty.Parser();
        List dates = parser.parse("05/02/2020 12:00").get(0).getDates();
        Date temp = (Date) dates.get(0);
        DateTime test = new DateTime(temp);

        assertEquals("05/02/2020 12:00", test.toString());

        dates = parser.parse(test.toString()).get(0).getDates();
        temp = (Date) dates.get(0);
        DateTime test2 = new DateTime(temp);

        assertEquals("05/02/2020 12:00", test2.toString());

        dates = parser.parse("06/12/2300 5pm").get(0).getDates();
        temp = (Date) dates.get(0);
        DateTime test3 = new DateTime(temp);

        assertEquals("06/12/2300 17:00", test3.toString());

        dates = parser.parse(test3.toString()).get(0).getDates();
        temp = (Date) dates.get(0);
        DateTime test4 = new DateTime(temp);

        assertEquals("06/12/2300 17:00", test4.toString());
    }

    @Test
    public void parseDifferentDateFormattedString() throws DukeException {
        com.joestelmach.natty.Parser parser = new com.joestelmach.natty.Parser();
        List dates = parser.parse("the day before 12/16/2019 4pm").get(0).getDates();
        Date temp = (Date) dates.get(0);

        DateTime test = new DateTime(temp);

        assertEquals("12/15/2019 16:00", test.toString());

        dates = parser.parse("16 December 1800 8am to 15th January 2019 9pm").get(0).getDates();
        temp = (Date) dates.get(0);
        DateTime test2 = new DateTime(temp);
        temp = (Date) dates.get(1);
        DateTime test3 = new DateTime(temp);

        assertEquals("12/16/1800 08:00", test2.toString());
        assertEquals("01/15/2019 21:00", test3.toString());


        dates = parser.parse("from christmas 2019 6am to christmas eve 2020 23:59").get(0).getDates();
        temp = (Date) dates.get(0);
        DateTime santaVacationStartDate = new DateTime(temp);
        temp = (Date) dates.get(1);
        DateTime santaVacationEndDate = new DateTime(temp);

        assertEquals("12/25/2019 06:00", santaVacationStartDate.toString());
        assertEquals("12/24/2020 23:59", santaVacationEndDate.toString());
    }
}
