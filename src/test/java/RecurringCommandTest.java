//import Storage.Storage;
//import Tasks.Deadline;
//import Tasks.Task;
//import UI.Ui;
//import commands.DeadlineCommand;
//import commands.RecurringCommand;
//import org.junit.jupiter.api.Test;
//
//import Exception.DukeException;
//import java.io.IOException;
//import java.text.ParseException;
//import java.util.ArrayList;
//
//import static org.junit.jupiter.api.Assertions.assertEquals;
//
//public class RecurringCommandTest  {
//
//    @Test
//    void test() throws ParseException, IOException, DukeException {
//        ArrayList<Task> list = new ArrayList<>();
//        Ui ui = new Ui();
//        Storage storage = new Storage();
//        RecurringCommand testR = new RecurringCommand();
//        assertEquals("I've automatically added this weekly task again:\n[D][?]yearly assignment (by:01 Jan 2020 01:01:01)",
//                testR.AddRecurring(list, "[D][?]yearly assignment (by:01 Jan 2019 01:01:01)" ,storage));
//    }
//}
