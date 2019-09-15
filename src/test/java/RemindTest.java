import Commands.Command;
import Commands.RemindersCommand;
import Commands.ViewScheduleCommand;
import ControlPanel.DukeException;
import ControlPanel.Storage;
import ControlPanel.Ui;
import org.junit.jupiter.api.Test;

import Tasks.*;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.io.*;

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
        storage = new Storage("C:/Users/Lenovo/Documents/sem1 1920/CS2113T/main/data/tasks-test.txt");
        tasks = new TaskList(storage.load());

        System.setOut(new PrintStream(outContent));
        Command RemCmdUpcoming =  new RemindersCommand("upcoming");
        RemCmdUpcoming.execute(tasks, ui, storage);
        //System.out.println("hello");
        assertEquals(" Got it. Your Upcoming Reminders: \n" + System.getProperty("line.separator")
                + " 1.[D][?] Duke Documentation (by: 17/9/2019 2359)\n" + System.getProperty("line.separator")
                + " 2.[T][?] some stuff\n" + System.getProperty("line.separator")
                + " 3.[P][?] IPPT (from: 18/9/2019 1400 to 19/9/2019 1800)\n" + System.getProperty("line.separator")
                ,outContent.toString());
        System.setOut(originalOut);
    }

    @Test
    public void testRemindersPast()throws ParseException, DukeException {

        ui = new Ui();
        storage = new Storage("C:/Users/Lenovo/Documents/sem1 1920/CS2113T/main/data/tasks-test.txt");
        tasks = new TaskList(storage.load());

        System.setOut(new PrintStream(outContent));
        Command RemCmdPast =  new RemindersCommand("past");
        RemCmdPast.execute(tasks, ui, storage);
        //System.out.println("hello");
        assertEquals(" Got it. Your Past Reminders: \n" + System.getProperty("line.separator")
                        + " 1.[E][?] code jam (at: 6/6/2011 1100 to 7/7/2011 1400)\n" + System.getProperty("line.separator")
                        + " 2.[P][?] H2 math revision (from: 12/9/2019 1400 to 14/9/2019 1800)\n" + System.getProperty("line.separator")
                ,outContent.toString());
        System.setOut(originalOut);
    }

    @Test
    public void testRemindersToday()throws ParseException, DukeException {

        ui = new Ui();
        storage = new Storage("C:/Users/Lenovo/Documents/sem1 1920/CS2113T/main/data/tasks-test.txt");
        tasks = new TaskList(storage.load());

        System.setOut(new PrintStream(outContent));
        Command RemCmdToday =  new RemindersCommand("today");
        RemCmdToday.execute(tasks, ui, storage);
        //System.out.println("hello");
        assertEquals(" Got it. Today's Reminders: \n" + System.getProperty("line.separator")
                        + " 1.[P][?] hackathon (from: 12/9/2019 1400 to 19/9/2019 1800)\n" + System.getProperty("line.separator")
                ,outContent.toString());
        System.setOut(originalOut);
    }

    @Test
    public void testViewSchedule()throws ParseException, DukeException {

        ui = new Ui();
        storage = new Storage("C:/Users/Lenovo/Documents/sem1 1920/CS2113T/main/data/tasks-test.txt");
        tasks = new TaskList(storage.load());

        System.setOut(new PrintStream(outContent));
        Command viewSchedule =  new ViewScheduleCommand("schedule /on 14/09/2019");
        viewSchedule.execute(tasks, ui, storage);
        //System.out.println("hello");
        assertEquals(" Got it. Your schedule for 14/09/2019: \n" + System.getProperty("line.separator")
                        + " 1.[P][?] H2 math revision (from: 12/9/2019 1400 to 14/9/2019 1800)\n" + System.getProperty("line.separator")
                        + " 2.[P][?] hackathon (from: 12/9/2019 1400 to 19/9/2019 1800)\n" + System.getProperty("line.separator")
                ,outContent.toString());
        System.setOut(originalOut);
    }

}
