/**import commands.Command;
import commands.RemindersCommand;
import commands.ViewScheduleCommand;
import controlpanel.DukeException;
import controlpanel.Ui;
import org.junit.jupiter.api.Test;
import tasks.TaskList;


import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.text.ParseException;
import java.nio.file.Path;
import java.nio.file.Paths;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class RemindTest {
    private Ui ui;
    private TaskList tasks;
    private Storage storage;
    private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
    private final PrintStream originalOut = System.out;



    @Test
    public void testRemindersUpcoming()throws ParseException, DukeException {

        ui = new Ui();
        Path currentDir = Paths.get("data/tasks-test.txt");
        String filePath = currentDir.toAbsolutePath().toString();
        storage = new Storage(filePath);
        tasks = new TaskList(storage.load());

        Command RemCmdUpcoming =  new RemindersCommand("upcoming");
        ui.clearOutputString();
        RemCmdUpcoming.execute(tasks, ui, storage);
        //System.out.println("hello");
        assertEquals(" Got it. Your Upcoming Reminders: \n"
                + " 1.[D][\u2718] Duke Documentation (by: 30/9/2019 2359)\n"
                + " 2.[T][\u2718] some stuff\n"
                + " 3.[P][\u2718] hackathon (from: 29/9/2019 1400 to 19/10/2019 1800)\n"
                , ui.getOutputString());
    }

    @Test
    public void testRemindersPast()throws ParseException, DukeException {

        ui = new Ui();
        Path currentDir = Paths.get("data/tasks-test.txt");
        String filePath = currentDir.toAbsolutePath().toString();
        storage = new Storage(filePath);
        tasks = new TaskList(storage.load());


        Command RemCmdPast =  new RemindersCommand("past");
        ui.clearOutputString();
        RemCmdPast.execute(tasks, ui, storage);
        //System.out.println("hello");
        assertEquals(" Got it. Your Past Reminders: \n"
                        + " 1.[E][\u2718] code jam (at: 6/6/2011 1100 to 7/7/2011 1400)\n"
                        + " 2.[D][\u2718] IPPT  (by: 16/9/2019 1800)\n"
                , ui.getOutputString());

    }

    @Test
    public void testRemindersToday()throws ParseException, DukeException {

        ui = new Ui();
        Path currentDir = Paths.get("data/tasks-test.txt");
        String filePath = currentDir.toAbsolutePath().toString();
        storage = new Storage(filePath);
        tasks = new TaskList(storage.load());

        Command RemCmdToday =  new RemindersCommand("today");
        ui.clearOutputString();
        RemCmdToday.execute(tasks, ui, storage);
        //System.out.println("hello");
        assertEquals(" Got it. Today's Reminders: \n"
                        + " 1.[P][\u2718] H2 math revision (from: 12/9/2019 1400 to 30/9/2019 1800)\n"
                , ui.getOutputString());
    }

    @Test
    public void testViewSchedule()throws ParseException, DukeException {

        ui = new Ui();
        Path currentDir = Paths.get("data/tasks-test.txt");
        String filePath = currentDir.toAbsolutePath().toString();
        storage = new Storage(filePath);
        tasks = new TaskList(storage.load());

        Command viewSchedule =  new ViewScheduleCommand("schedule /on 14/09/2019");
        ui.clearOutputString();
        viewSchedule.execute(tasks, ui, storage);
        //System.out.println("hello");
        assertEquals(" Got it. Your schedule for 14/09/2019: \n"
                        + " 1.[P][\u2718] H2 math revision (from: 12/9/2019 1400 to 30/9/2019 1800)\n"
                , ui.getOutputString());
    }

}
*/