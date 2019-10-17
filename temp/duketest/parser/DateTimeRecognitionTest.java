package duketest.parser;

import duke.exceptions.DukeException;
import duke.parser.DateTimeRecognition;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class DateTimeRecognitionTest {

    @Test
    public void checkDateFormat() {
        DateTimeRecognition dateA = new DateTimeRecognition("qwrfEFER");
        assertThrows(DukeException.class,() -> dateA.dateTime());
        DateTimeRecognition dateB = new DateTimeRecognition("1800");
        assertThrows(DukeException.class,() -> dateB.dateTime());

    }
}
