package duke.task;

import java.text.ParseException;
import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Test;

//@@author talesrune
class EventTest {

    @Test
    void eventTest() throws ParseException {
        Task task = new Event("party", "18/04/2019 1005");
        assertEquals("[E][X] party (at: 18th of April 2019, 10:05 AM)", task.toString());
    }
}