/**import commands.*;
import controlpanel.DukeException;
import controlpanel.Ui;
import org.junit.jupiter.api.Test;

import tasks.*;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.ParseException;
import java.io.*;

import static org.junit.jupiter.api.Assertions.assertEquals;


public class TentativeTest {
    private Ui ui;
    private TaskList tasks;
    private Storage storage;
    private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
    private final PrintStream originalOut = System.out;

    public TentativeTest() {
        Path currentDir = Paths.get("data/tasks-test.txt");
        String filePath = currentDir.toAbsolutePath().toString();
        storage = new Storage(filePath);
        tasks = new TaskList(storage.load());
        ui = new Ui();
    }


    @Test
    public void testMultiEventCreation() throws ParseException, DukeException {
        ui.clearOutputString();
        Command addMultiEvent = new AddCommand("multiEvent", "multiEvent event name /at 1/12/2009 2232 to 1/12/2009 2234 /or 2/12/2009 2232 to 2/12/2009 2234");
        addMultiEvent.execute(tasks, ui, storage);

        assertEquals(" Got it. I've added this task: \n" +
                "     [E][✘] event name (at: 1/12/2009 2232 to 1/12/2009 2234 or 2/12/2009 2232 to 2/12/2009 2234 )\n"
                + " Now you have " + tasks.lengthOfList() + " tasks in the list.\n", ui.getOutputString());
    }

    @Test
    public void chooseEventTime() throws ParseException, DukeException {
        ui.clearOutputString();
        Command chooseTime = new ChooseEventTime("choose " + tasks.lengthOfList() + " 1");
        chooseTime.execute(tasks, ui, storage);

        assertEquals(" Noted. The following timing has been chosen:\n" +
                " [E][✘] event name (at: 1/12/2009 2232 to 1/12/2009 2234 )\n", ui.getOutputString());

        Command clearChanges = new DeleteCommand(tasks.lengthOfList());
        clearChanges.execute(tasks, ui, storage);
    }




}
*/