//import spinbox.Parser;
//import spinbox.Storage;
//import spinbox.lists.TaskList;
//import spinbox.Ui;
//import spinbox.commands.Command;
//import spinbox.exceptions.SpinBoxException;
//import spinbox.items.tasks.Event;
//
//import org.junit.jupiter.api.Test;
//
//import static org.junit.jupiter.api.Assertions.assertEquals;
//
//
//public class SnoozeTest {
//
//    @Test
//    public void snoozeCommand_rescheduleDeadline() throws SpinBoxException {
//        TaskList tasks = new TaskList();
//        Ui ui = new Ui();
//        Storage storage = new Storage("data/dukeTest.txt");
//
//        Command addInitialTask = new Parser().parse("deadline TestDeadline /by 09/19/2019 12:00");
//        addInitialTask.execute(tasks, storage, ui);
//        assertEquals("09/19/2019 12:00", tasks.getList().get(0).getStartDate().toString());
//        Command rescheduleTask = new Parser().parse("snooze 1 09/20/2019 13:11");
//        rescheduleTask.execute(tasks, storage, ui);
//        assertEquals("09/20/2019 13:11", tasks.getList().get(0).getStartDate().toString());
//    }
//
//    @Test
//    public void snoozeCommand_rescheduleEvent() throws SpinBoxException {
//        TaskList tasks = new TaskList();
//        Ui ui = new Ui();
//        Storage storage = new Storage("data/dukeTest.txt");
//        Event testEvent;
//
//        Command addInitialTask = new Parser()
//                .parse("event TestEvent /at 09/19/2019 12:00 to 10/20/2019 00:01");
//        addInitialTask.execute(tasks, storage, ui);
//        testEvent = (Event) tasks.getList().get(0);
//        assertEquals("E | 0 | TestEvent | 09/19/2019 12:00 | 10/20/2019 00:01",
//                testEvent.storeString());
//
//
//        Command rescheduleTask = new Parser()
//                .parse("snooze 1 09/20/2019 13:11 to 10/20/2019 00:01");
//        rescheduleTask.execute(tasks, storage, ui);
//        assertEquals("E | 0 | TestEvent | 09/20/2019 13:11 | 10/20/2019 00:01",
//                testEvent.storeString());
//    }
//}
