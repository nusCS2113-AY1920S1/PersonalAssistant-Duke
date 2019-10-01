import Commands.Command;
import Interface.Parser;
import Interface.Storage;
import Interface.Ui;
import Tasks.TaskList;
import Tasks.Todo;
import org.junit.jupiter.api.Test;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class DoWithinPeriodTasksTest {
    Ui ui = new Ui();
    Storage storage = new Storage(System.getProperty("user,dir") + "\\data\\duke.txt");

    @Test
    public void doWithinPeriodTask1() throws Exception {
        Date date = new Date();
        DateFormat format = new SimpleDateFormat("dd/MM/yyyy");
        String currentDate = format.format(date);
        String expected = "Reminder have been set for: fishing . Start Date: " + currentDate + " End Date: 05/02/2020\n";
        TaskList todoList = new TaskList();
        TaskList eventList = new TaskList();
        TaskList deadlineList = new TaskList();
        todoList.addTask(new Todo("fishing"));
        Command c = Parser.parse("fishing (from: " + currentDate + " to 05/02/2020)");
        String actual = c.execute(todoList, eventList, deadlineList, ui, storage);
        assertEquals(expected, actual);
    }
}
