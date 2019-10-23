package duke.task;

import java.text.ParseException;
import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Test;

//@@author talesrune
class DeadlineTest {

    @Test
    void deadlineTest() throws ParseException {
        Task task = new Deadline("homework", "08/04/2019 1000");
        assertEquals("[D][X] homework (by: 8th of April 2019, 10AM)", task.toString());
    }
}