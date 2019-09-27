import commands.Command;
import commands.RescheduleCommand;
import controlpanel.DukeException;
import controlpanel.Storage;
import controlpanel.Ui;
import org.junit.jupiter.api.Test;
import tasks.TaskList;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.text.ParseException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

public class RescheduleTest {

    private Ui ui;
    private TaskList tasks;
    private Storage storage;
    private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
    private final PrintStream originalOut = System.out;

    @Test
    public void testIsExit() {
        Command rescheduleCmd = new
                RescheduleCommand("reschedule event 1 /at 6/6/2011 1100 to 7/7/2011 1400");
        assertFalse(rescheduleCmd.isExit());
    }

    @Test
    public void testRescheduleOutput() throws ParseException, DukeException {
        ui = new Ui();
        storage = new Storage("D:/main/data/tasks-test.txt");
        tasks = new TaskList(storage.load());
        Command rescheduleCmd = new
                RescheduleCommand("reschedule deadline 4 /by 16/9/2019 1800");
        System.setOut(new PrintStream(outContent));
        rescheduleCmd.execute(tasks, ui, storage);
        assertEquals(" Noted. I've removed this task:\n"
                        + "  [D][?] Duke Documentation (by: 17/9/2019 2359)\n"
                        + " Now you have 5 tasks in the list.\n" + " Got it. I've added this task: \n"
                        + "  [D][?] Duke Documentation (by: 16/9/2019 1800)\n"
                        + " Now you have 6 tasks in the list.\n"
                , outContent.toString());
    }
}
