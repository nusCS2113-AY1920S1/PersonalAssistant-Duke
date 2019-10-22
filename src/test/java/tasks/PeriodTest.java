package tasks;

import model.tasks.Period;
import org.junit.jupiter.api.Test;
import utils.DukeException;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class PeriodTest {

    @Test
    public void checkDataString() throws DukeException {
        String description = "This is a test Period";
        String start = "10/12/2019 1120";
        String end = "12/12/2019 1122";
        SimpleDateFormat ft = new SimpleDateFormat("dd/MM/yyyy hhmm");
        String result = "P | 0 | This is a test Period | 10/12/2019 1120 | 12/12/2019 1122";
        try {
            Date tempStart = ft.parse(start);
            Date tempEnd = ft.parse(end);
            Period temp = new Period(description, tempStart, tempEnd);
            assertEquals(result, temp.dataString());
        } catch (ParseException e) {
            throw new DukeException("Invalid date format, the correct format is: dd/MM/yyyy hhmm");
        }

    }
}
