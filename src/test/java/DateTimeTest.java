import duke.DateTime;
import duke.exceptions.DukeException;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

public class DateTimeTest {

    @Test
    public void correctDateInputShouldCreateReusableString() throws DukeException {
        DateTime test = new DateTime("01/01/900 1200");
        DateTime test2 = new DateTime(test.toString());
        assertEquals(test2.toString(), "01/01/0900 1200");

        DateTime test3 = new DateTime("01/01/1900 1200");
        DateTime test4 = new DateTime(test3.toString());
        assertEquals(test4.toString(), "01/01/1900 1200");
    }

    @Test
    public void incorrectDateFormatsShouldThrowDateFormatException() {
        try {
            new DateTime("01011900 1200");
        } catch (DukeException e) {
            assertEquals(e.getMessage(), "Invalid Date Format\n\n"
                    + "Please enter valid dates and times, as DD/MM/YYYY HHMM");
        }

        try {
            new DateTime("01011900 2500");
        } catch (DukeException e) {
            assertEquals(e.getMessage(), "Invalid Date Format\n\n"
                    + "Please enter valid dates and times, as DD/MM/YYYY HHMM");
        }

        try {
            new DateTime("01/01/1900 2500");
        } catch (DukeException e) {
            assertNotEquals(e.getMessage(), "Invalid Date Format\n\n"
                    + "Please enter valid dates and times, as DD/MM/YYYY HHMM");
        }

        try {
            new DateTime("12/10/1934 12:00");
        } catch (DukeException e) {
            assertEquals(e.getMessage(), "Invalid Date Format\n\n"
                    + "Please enter valid dates and times, as DD/MM/YYYY HHMM");
        }

        try {
            new DateTime("11.10.127 10:00");
        } catch (DukeException e) {
            assertEquals(e.getMessage(), "Invalid Date Format\n\n"
                    + "Please enter valid dates and times, as DD/MM/YYYY HHMM");
        }
    }

    @Test
    public void incorrectDateValuesShouldThrowDateValueException() {

        try {
            new DateTime("00/01/1900 2359");
        } catch (DukeException e) {
            assertEquals(e.getMessage(), "Invalid Date Values\n"
                    + "Please enter valid dates and times, as DD/MM/YYYY HHMM");
        }

        try {
            new DateTime("10/13/1946 2459");
        } catch (DukeException e) {
            assertEquals(e.getMessage(), "Invalid Date Values\n"
                    + "Please enter valid dates and times, as DD/MM/YYYY HHMM");
        }

        try {
            new DateTime("10/13/1946 2259");
        } catch (DukeException e) {
            assertEquals(e.getMessage(), "Invalid Date Values\n"
                    + "Please enter valid dates and times, as DD/MM/YYYY HHMM");
        }

        try {
            new DateTime("10/11/001 2159");
        } catch (DukeException e) {
            assertNotEquals(e.getMessage(), "Invalid Date Values\n"
                    + "Please enter valid dates and times, as DD/MM/YYYY HHMM");
        }

        try {
            new DateTime("10/11/1946 2459");
        } catch (DukeException e) {
            assertEquals(e.getMessage(), "Invalid Date Values\n"
                    + "Please enter valid dates and times, as DD/MM/YYYY HHMM");
        }

        try {
            new DateTime("10/13/1946 0269");
        } catch (DukeException e) {
            assertEquals(e.getMessage(), "Invalid Date Values\n"
                    + "Please enter valid dates and times, as DD/MM/YYYY HHMM");
        }
    }
}
