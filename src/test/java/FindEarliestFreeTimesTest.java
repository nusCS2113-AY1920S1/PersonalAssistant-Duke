import Commands.Command;
import Interface.*;
import Tasks.*;
import org.junit.jupiter.api.Test;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Calendar;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class FindEarliestFreeTimesTest {
    Ui ui = new Ui();
    Storage storage = new Storage();

    @Test
    public void FindEarliestFreeTimesTestEmptyList() throws Exception {
        String duration = "2";
        Date date = new Date();
        DateFormat format = new SimpleDateFormat("E dd/MM/yyyy hh:mm a");
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.HOUR, Integer.parseInt(duration));
        Date outDate = calendar.getTime();
        String startDate = format.format(date);
        String endDate = format.format(outDate);
        String expected ="You are available at: \n" + startDate + "until" + endDate;
        TaskList todoList = new TaskList();
        TaskList eventList = new TaskList();
        TaskList deadlineList = new TaskList();
        Command c = Parser.parse("when is the nearest day in which I have a "+ duration +" hour free slot?");
        String actual = c.execute(todoList,eventList,deadlineList, ui, storage);

        actual = actual.substring(0,"You are available at: \n".length()) + startDate + "until" + endDate;
        assertEquals(expected, actual);
    }
}