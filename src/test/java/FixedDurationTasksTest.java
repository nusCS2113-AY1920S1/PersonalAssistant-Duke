import Commons.Storage;
import Commons.UserInteraction;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class FixedDurationTasksTest {
    UserInteraction ui = new UserInteraction();
    Storage storage = new Storage();
/*
    @Test
    public void FindEarliestFreeTimesTestEmptyList() throws Exception {
        Date date = new Date();
        DateFormat format = new SimpleDateFormat("E dd/MM/yyyy hh:mm a");
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.HOUR, 2);
        Date outDate = calendar.getTime();
        String startDate = format.format(date);
        String endDate = format.format(outDate);
        String expected ="You are available at: \n" + startDate + "until" + endDate;
        TaskList todoList = new TaskList();
        TaskList eventList = new TaskList();
        TaskList deadlineList = new TaskList();
        Command c = Parser.parse("when is the nearest day in which I have a 2 hour free slot?");
        String actual = c.execute(todoList,eventList,deadlineList, ui, storage);
        actual = actual.substring(0,"You are available at: \n".length()) + startDate + "until" + endDate;
        assertEquals(expected, actual);
    }*/
}