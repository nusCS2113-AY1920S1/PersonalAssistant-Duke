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
    public void remindTestTodo() throws Exception {
        String expected ="Here are your tasks for this week:\n" + "1.[T][\u2718] buy bread\n";
        TaskList temp = new TaskList();
        temp.addTask(new Todo("buy bread"));
        Command c = Parser.parse("remind");
        String actual = c.execute(temp, ui, storage);
        assertEquals(expected, actual);
    }

    @Test
    public void remindTestWithDate() throws Exception {
        Date date = new Date();
        DateFormat format = new SimpleDateFormat("E dd/MM/yyyy hh:mm a");
        String currentDate = format.format(date);
        String expected ="Here are your tasks for this week:\n" + "1.[E][\u2718] exam (at: " + currentDate + ")\n";
        TaskList temp = new TaskList();
        temp.addTask(new Event("exam", currentDate));
        Command c = Parser.parse("remind");
        String actual = c.execute(temp, ui, storage);
        assertEquals(expected, actual);
    }

    @Test
    public void remindTestWithDoneTasks() throws Exception {
        Date date = new Date();
        DateFormat format = new SimpleDateFormat("E dd/MM/yyyy hh:mm a");
        String currentDate = format.format(date);
        String expected = "There are no upcoming tasks this week.\n";
        TaskList temp = new TaskList();
        temp.addTask(new Event("exam", currentDate));
        temp.markAsDone(0);
        Command c = Parser.parse("remind");
        String actual = c.execute(temp, ui, storage);
        assertEquals(expected, actual);
    }
}
