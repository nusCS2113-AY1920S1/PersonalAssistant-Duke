import Interface.DukeException;
import Tasks.Deadline;
import Tasks.Event;
import Tasks.TaskList;
import org.junit.jupiter.api.Test;

import java.text.ParseException;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class SnoozeTest {
    @Test
    public void SnoozeTest() throws ParseException, DukeException {
        String w1 = "[D][\u2718] project (by: Sun 03/02/2019 07:00 AM)" + "\n" +
                "[E][\u2718] swimming (at: Sun 03/02/2019 09:00 AM)";
        TaskList temp = new TaskList();
        temp.addTask(new Event("swimming", "Sun 03/02/2019 08:00 AM"));
        temp.addTask(new Deadline("project", "Sun 03/02/2019 07:00 AM"));
        temp.snoozeTask(0, "Sun 03/02/2019 09:00 AM");
        String w2 = temp.getTask(0) + "\n" + temp.getTask(1);
        assertEquals(w1, w2);
    }
}
