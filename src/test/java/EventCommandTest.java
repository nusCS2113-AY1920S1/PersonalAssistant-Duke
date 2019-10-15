import gazeeebo.Storage.Storage;
import gazeeebo.Tasks.Event;
import gazeeebo.Tasks.Task;
import gazeeebo.UI.Ui;
import gazeeebo.commands.EventCommand;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import gazeeebo.Exception.DukeException;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Stack;

import static org.junit.jupiter.api.Assertions.*;
class EventCommandTest {
    //creating a stream to hold the output
    private ByteArrayOutputStream output = new ByteArrayOutputStream();
    private PrintStream mine = new PrintStream(output);
    //saving the original System.out
    private PrintStream original = System.out;

    @BeforeEach
    void setupStream() {
        //tell java to print to my own stream
        System.setOut(mine);
    }

    @AfterEach
    void restoreStream(){
        System.out.flush();
        System.setOut(original);
    }

    @Test
    void execute_scheduleAnomalies_clash1() throws IOException {
        //start time of clashing events are the same
        Ui ui = new Ui();
        Storage storage = new Storage();
        ui.FullCommand = "event sleep/at 2019-12-12 03:03:03-04:04:04";
        EventCommand ec = new EventCommand();
        ArrayList<Task> tasks = new ArrayList<Task>();
        Stack<String> CommandStack = new Stack<>();
        ArrayList<Task> deletedTask = new ArrayList<Task>();

        Event e = new Event("eat", "2019-12-12 03:03:03-04:04:04");
        tasks.add(e);
        try {
            ec.execute(tasks, ui, storage, CommandStack, deletedTask);
        } catch (ParseException | DukeException e1) {
            e1.printStackTrace();
        }
        assertEquals("The following event(s) clash with your current event:\r\n" +
                "1.[E][ND]eat(at:12 Dec 2019 03:03:03-04:04:04)\r\n\r\n" +
                "Got it. I've added this task:\r\n" +
                "[E][ND]sleep(at:12 Dec 2019 03:03:03-04:04:04)\r\n" +
                "Now you have 2 tasks in the list.\r\n", output.toString());
    }

    @Test
    void execute_scheduleAnomalies_clash2() throws IOException {
        //start time of new event being added is earlier than old event but clash still occurs
        Ui ui = new Ui();
        Storage storage = new Storage();
        ui.FullCommand = "event sleep/at 2019-12-12 02:03:03-03:10:04";
        EventCommand ec = new EventCommand();
        ArrayList<Task> tasks = new ArrayList<Task>();
        Stack<String> CommandStack = new Stack<>();
        ArrayList<Task> deletedTask = new ArrayList<Task>();
        Event e = new Event("eat", "2019-12-12 03:03:03-04:04:04");
        tasks.add(e);
        try {
            ec.execute(tasks, ui, storage, CommandStack, deletedTask);
        } catch (ParseException | DukeException e1) {
            e1.printStackTrace();
        }
        assertEquals("The following event(s) clash with your current event:\r\n" +
                "1.[E][ND]eat(at:12 Dec 2019 03:03:03-04:04:04)\r\n\r\n" +
                "Got it. I've added this task:\r\n" +
                "[E][ND]sleep(at:12 Dec 2019 02:03:03-03:10:04)\r\n" +
                "Now you have 2 tasks in the list.\r\n", output.toString());
    }

    @Test
    void execute_scheduleAnomalies_clash3() throws IOException {
        //start time of new event being added is after that of old event but clash still occurs
        Ui ui = new Ui();
        Storage storage = new Storage();
        ui.FullCommand = "event sleep/at 2019-12-12 03:10:03-03:50:04";
        EventCommand ec = new EventCommand();
        ArrayList<Task> tasks = new ArrayList<Task>();
        Stack<String> CommandStack = new Stack<>();
        ArrayList<Task> deletedTask = new ArrayList<Task>();
        Event e = new Event("eat", "2019-12-12 03:03:03-04:04:04");
        tasks.add(e);
        try {
            ec.execute(tasks, ui, storage, CommandStack, deletedTask);
        } catch (ParseException | DukeException e1) {
            e1.printStackTrace();
        }
        assertEquals("The following event(s) clash with your current event:\r\n" +
                "1.[E][ND]eat(at:12 Dec 2019 03:03:03-04:04:04)\r\n\r\n" +
                "Got it. I've added this task:\r\n" +
                "[E][ND]sleep(at:12 Dec 2019 03:10:03-03:50:04)\r\n" +
                "Now you have 2 tasks in the list.\r\n", output.toString());
    }

    @Test
    void execute_scheduleAnomalies_noClash() throws IOException {
        Ui ui = new Ui();
        Storage storage = new Storage();
        ui.FullCommand = "event sleep/at 2019-12-12 12:03:03-14:10:04";
        EventCommand ec = new EventCommand();
        ArrayList<Task> tasks = new ArrayList<Task>();
        Stack<String> CommandStack = new Stack<>();
        ArrayList<Task> deletedTask = new ArrayList<Task>();
        Event e = new Event("eat", "2019-12-12 03:03:03-04:04:04");
        tasks.add(e);
        try {
            ec.execute(tasks, ui, storage, CommandStack, deletedTask);
        } catch (ParseException | DukeException e1) {
            e1.printStackTrace();
        }
        assertEquals("Got it. I've added this task:\r\n" +
                "[E][ND]sleep(at:12 Dec 2019 12:03:03-14:10:04)\r\n" +
                "Now you have 2 tasks in the list.\r\n", output.toString());
    }

}
