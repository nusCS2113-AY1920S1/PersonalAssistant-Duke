import Tasks.Event;
import Tasks.TaskList;
import org.junit.jupiter.api.Test;

import java.text.ParseException;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class SnoozeTest {
    @Test
    public void SnoozeTest() throws ParseException, DukeException {
        String w1 = "[E][\u2718] project (at: Sun 05/02/2019 time: 07:00 AM to 11:00 AM)" + "\n" +
                "[E][\u2718] swimming (at: Sun 03/02/2019 time: 09:00 AM to 10:00 AM)";
        TaskList temp = new TaskList();
        temp.addTask(new Event("swimming", "Sun 02/02/2019",  "10:00 AM", "11:30 AM"));
        temp.addTask(new Event("project", "Sun 05/02/2019", "07:00 AM", "11:00 AM"));
        //temp.snoozeTask(temp, 1, "Sun 03/02/2019", "09:00 AM", "10:00 AM");
        //new SnoozeCommand(1, "Sun 03/02/2019", "09:00 AM", "10:00 AM" );
        String w2 = temp.getTask(0) + "\n" + temp.getTask(1);
        assertEquals(w1, w2);
    }
}
