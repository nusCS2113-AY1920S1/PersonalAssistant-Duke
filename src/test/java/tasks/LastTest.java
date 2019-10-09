package tasks;

import org.junit.jupiter.api.Test;
import utils.DukeException;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class LastTest {
    @Test
    public void checkToString() {
        String description = "This is a test Last";
        String duration = "1 hr";
        String result = "[L][✘] This is a test Last (last: 1 hr)";

            Last temp = new Last(description, duration);
            assertEquals(result, temp.toString());
    }

    @Test
    public void checkDataString() throws DukeException {
        String description = "This is a test Last";
        String duration = "1 hr";

        String result = "L | 0 | This is a test Last | 1 hr";

            Last temp = new Last(description, duration);
            assertEquals(result, temp.dataString());


    }


}
