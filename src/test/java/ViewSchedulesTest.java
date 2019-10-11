import Commands.Command;
import Interface.*;
import Tasks.*;
import org.junit.jupiter.api.Test;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ViewSchedulesTest {
    Ui ui = new Ui();
    Storage storage = new Storage();

    @Test
    public void viewScheduleWithDeadlineTask() throws Exception {
        Date date = new Date();
        DateFormat format = new SimpleDateFormat("E dd/MM/yyyy hh:mm a");
        String currentDate = format.format(date);
        String expected = "Here is your schedule which have been categorised into TODO, DEADLINE and EVENTS\n" +
                "Here is your schedule!\n" + "DEADLINE Task\n" + "1.[D][\u2718] return book (by: Fri 01/02/2019 01:00PM)";
        TaskList todoList = new TaskList();
        TaskList eventList = new TaskList();
        TaskList deadlineList = new TaskList();
        deadlineList.addTask(new Deadline("return book", currentDate));
        Command c = Parser.parse("show schedule");
        String actual = c.execute(todoList, eventList, deadlineList, ui, storage);
        assertEquals(expected, actual);
    }

    @Test
    public void viewScheduleWithTodoTask() throws Exception {
        String expected = "Here is your schedule which have been categorised into TODO, DEADLINE and EVENTS\n" +
                "Here is your schedule!\n" + "TODO Task\n" + "1.[T][\u2718] read book";
        TaskList todoList = new TaskList();
        TaskList eventList = new TaskList();
        TaskList deadlineList = new TaskList();
        todoList.addTask(new Todo("read book"));
        Command c = Parser.parse("show schedule");
        String actual = c.execute(todoList, eventList, deadlineList, ui, storage);
        assertEquals(expected, actual);
    }

    @Test
    public void viewScheduleWithEventTask() throws Exception {
        Date date = new Date();
        DateFormat format = new SimpleDateFormat("E dd/MM/yyyy hh:mm a");
        String currentDate = format.format(date);
        String expected = "Here is your schedule which have been categorised into TODO, DEADLINE and EVENTS\n" +
                "Here is your schedule!n" + "EVENT Task\n" + "1.[E][\u2718] project meeting (at: Fri 01/02/2019" +
                "time: 1500 to 1600";
        TaskList todoList = new TaskList();
        TaskList eventList = new TaskList();
        TaskList deadlineList = new TaskList();
        eventList.addTask(new Event("project meeting", currentDate, "1500", "1600"));
        Command c = Parser.parse("show schedule");
        String actual = c.execute(todoList, eventList, deadlineList, ui, storage);
        assertEquals(expected, actual);
    }
}
