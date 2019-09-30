//import Storage.Storage;
//import Tasks.Deadline;
//import Tasks.Task;
//import UI.Ui;
//import commands.DeadlineCommand;
//import commands.SnoozeCommand;
//import org.junit.jupiter.api.Test;
//import java.io.IOException;
//import java.text.ParseException;
//import java.text.SimpleDateFormat;
//import java.util.ArrayList;
//import Exception.DukeException;
//
//import static org.junit.jupiter.api.Assertions.assertEquals;
//
//public class RescheduleCommandTest {
//    @Test
//    public void testExecuteSnooze() throws ParseException,IOException,DukeException{
//        DeadlineCommand deadlineCommand = new DeadlineCommand();
//        SnoozeCommand snoozeCommand = new SnoozeCommand();
//        ArrayList<Task> tasks = new ArrayList<Task>();
//        Ui ui = new Ui();
//        Storage storage = new Storage();
//        ui.FullCommand = "deadline return book /by 2008-7-7 3:3:3";
//        deadlineCommand.execute(tasks,ui,storage);
//        ui.FullCommand = "reschedule 1 2019-9-18 5:5:5";
//        int index = Integer.parseInt(ui.FullCommand.split(" ")[1]) - 1;
//        String Decription = tasks.get(index).description;
//        ui.FullCommand="2019-9-18 5:5:5";
//        SimpleDateFormat fmt = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//        Deadline RescheduledDeadline = new Deadline(Decription, fmt.parse(ui.FullCommand));
//        tasks.remove(index);
//        tasks.add(RescheduledDeadline);
//        assertEquals(tasks.get(0).listFormat(),"[D]" + "[" + tasks.get(0).getStatusIcon() + "]" + "return book " + "(by:" + fmt.parse(ui.FullCommand) + ")");
//    }
//}