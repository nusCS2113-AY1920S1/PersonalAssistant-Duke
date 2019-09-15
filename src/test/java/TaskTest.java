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


public class TaskTest {
    private Ui ui;
    private TaskList tasks;
    private Storage storage;
    private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
    private final PrintStream originalOut = System.out;



    @Test
    public void dummyTest(){
        assertEquals(2, 2);
    }

    @Test
    public void testgetByAt()throws ParseException {

        SimpleDateFormat simpleDateFormat  = new SimpleDateFormat("d/M/yyyy HHmm");
        Date startDate = simpleDateFormat.parse("3/2/2019 1300");
        Date endDate = simpleDateFormat.parse("6/2/2019 1300");
        assertEquals("3/2/2019 1300",

                new Deadline("do assignments", startDate).getBy());

        assertEquals("6/2/2019 1300",

                new Events("marquee", startDate,endDate).getEndAt());

        assertEquals("3/2/2019 1300",

                new Events("marquee", startDate,endDate).getStartAt());

    }

    @Test
    public void testRemindersUpcoming()throws ParseException, DukeException {

        ui = new Ui();
        storage = new Storage("C:/Users/Lenovo/Documents/sem1 1920/CS2113T/main/data/tasks.txt");
        tasks = new TaskList(storage.load());

        System.setOut(new PrintStream(outContent));
        Command RemCmdUpcoming =  new RemindersCommand("upcoming");
        RemCmdUpcoming.execute(tasks, ui, storage);
        //System.out.println("hello");
        assertEquals(" Got it. Your Upcoming Deadlines and Events: \n" + System.getProperty("line.separator")
                + " 1.[D][?] send keegs off (by: 19/9/2019 2300)\n" + System.getProperty("line.separator")
                ,outContent.toString());
        System.setOut(originalOut);
    }

    @Test
    public void testRemindersPast()throws ParseException, DukeException {

        ui = new Ui();
        storage = new Storage("C:/Users/Lenovo/Documents/sem1 1920/CS2113T/main/data/tasks.txt");
        tasks = new TaskList(storage.load());

        System.setOut(new PrintStream(outContent));
        Command RemCmdPast =  new RemindersCommand("past");
        RemCmdPast.execute(tasks, ui, storage);
        //System.out.println("hello");
        assertEquals(" Got it. Your Past Deadlines and Events: \n" + System.getProperty("line.separator")
                        + " 1.[D][?] do assignments (by: 3/2/2019 1300)\n" + System.getProperty("line.separator")
                ,outContent.toString());
        System.setOut(originalOut);
    }

    @Test
    public void testRemindersToday()throws ParseException, DukeException {

        ui = new Ui();
        storage = new Storage("C:/Users/Lenovo/Documents/sem1 1920/CS2113T/main/data/tasks.txt");
        tasks = new TaskList(storage.load());

        System.setOut(new PrintStream(outContent));
        Command RemCmdToday =  new RemindersCommand("today");
        RemCmdToday.execute(tasks, ui, storage);
        //System.out.println("hello");
        assertEquals(" Got it. Today's Deadlines and Events: \n" + System.getProperty("line.separator")
                        + " 1.[E][?] marquee (at: 12/9/2019 1400 to 14/9/2019 1600)\n" + System.getProperty("line.separator")
                ,outContent.toString());
        System.setOut(originalOut);
    }

    @Test
    public void testViewSchedule()throws ParseException, DukeException {

        ui = new Ui();
        storage = new Storage("C:/Users/Lenovo/Documents/sem1 1920/CS2113T/main/data/tasks.txt");
        tasks = new TaskList(storage.load());

        System.setOut(new PrintStream(outContent));
        Command viewSchedule =  new ViewScheduleCommand("schedule /on 14/09/2019");
        viewSchedule.execute(tasks, ui, storage);
        //System.out.println("hello");
        assertEquals(" Got it. Your schedule for 14/09/2019: \n" + System.getProperty("line.separator")
                        + " 1.[E][?] marquee (at: 12/9/2019 1400 to 14/9/2019 1600)\n" + System.getProperty("line.separator")
                ,outContent.toString());
        System.setOut(originalOut);
    }

}
