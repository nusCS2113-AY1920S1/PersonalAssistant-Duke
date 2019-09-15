import Commands.*;
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


public class TentativeTest {
    private Ui ui;
    private TaskList tasks;
    private Storage storage;
    private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
    private final PrintStream originalOut = System.out;

    public TentativeTest() {
        ui = new Ui();
        storage = new Storage("/Users/chengweixuanmacbook/Desktop/School/CS2113/main/data/tasks-test.txt");
        tasks = new TaskList(storage.load());
    }


    @Test
    public void testMultiEventCreation() throws ParseException, DukeException {
        System.setOut(new PrintStream(outContent));
        Command addMultiEvent = new AddCommand("multiEvent", "multiEvent event name /at 1/12/2009 2232 to 1/12/2009 2234 /or 2/12/2009 2232 to 2/12/2009 2234");
        addMultiEvent.execute(tasks, ui, storage);

        assertEquals(" Got it. I've added this task: \n" + System.getProperty("line.separator") +
                "     [E][✘] event name (at: 1/12/2009 2232 to 1/12/2009 2234 or 2/12/2009 2232 to 2/12/2009 2234 )\n"
             + System.getProperty("line.separator") +
                " Now you have 7 tasks in the list." + System.getProperty("line.separator"), outContent.toString());
        System.setOut(originalOut);
    }

    @Test
    public void chooseEventTime() throws ParseException, DukeException {
        System.setOut(new PrintStream(outContent));
        Command chooseTime = new ChooseEventTime("choose 7 1");
        chooseTime.execute(tasks, ui, storage);

        assertEquals(" Noted. The following timing has been chosen:\n" +
                "\n" +
                " [E][✘] event name (at: 1/12/2009 2232 to 1/12/2009 2234 )\n" + System.getProperty("line.separator"), outContent.toString());
        System.setOut(originalOut);

        Command clearChanges = new DeleteCommand(7);
        clearChanges.execute(tasks, ui, storage);
    }




}
