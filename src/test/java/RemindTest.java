import Commands.Command;
import Interface.*;
import Tasks.*;
import org.junit.jupiter.api.Test;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class RemindTest {
    Ui ui = new Ui();
    Storage storage = new Storage();

    @Test
    public void remindTestWithDate() throws Exception {
        Date date = new Date();
        TaskList todos = new TaskList();
        TaskList events = new TaskList();
        TaskList deadlines = new TaskList();
        DateFormat format = new SimpleDateFormat("E dd/MM/yyyy");
        String currentDate = format.format(date);
        String start = "06:00 PM ";
        String end = "08:00 PM";
        String expected ="Here are your tasks for this week:\n" + "1.[E][\u2718] exam (at: " + currentDate
                + " time: " + start + " to " + end + ")\n";
        events.addTask(new Event("exam", currentDate, start, end));
        Command c = Parser.parse("remind");
        String actual = c.execute(todos, events, deadlines, ui, storage);
        assertEquals(expected, actual);
    }

    @Test
    public void remindTestWithDoneTasks() throws Exception {
        Date date = new Date();
        TaskList todos = new TaskList();
        TaskList events = new TaskList();
        TaskList deadlines = new TaskList();
        DateFormat format = new SimpleDateFormat("E dd/MM/yyyy");
        String currentDate = format.format(date);
        String start = "06:00 PM ";
        String end = "08:00 PM";
        String expected = "There are no upcoming tasks this week.\n";
        TaskList temp = new TaskList();
        events.addTask(new Event("exam", currentDate, start, end));
        events.markAsDone(0);
        Command c = Parser.parse("remind");
        String actual = c.execute(todos, events, deadlines, ui, storage);
        assertEquals(expected, actual);
    }
}
