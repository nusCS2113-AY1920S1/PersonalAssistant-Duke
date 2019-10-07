/**import commands.Command;
import commands.RescheduleCommand;
import controlpanel.DukeException;
import controlpanel.Ui;
import org.junit.jupiter.api.Test;
import tasks.TaskList;

import java.text.ParseException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

public class RescheduleTest {

    private Ui ui;
    private TaskList tasks;
    private Storage storage;

    public RescheduleTest() {
        ui = new Ui();
        storage = new Storage("D:/main/data/tasks.txt");
        tasks = new TaskList(storage.load());
    }

    @Test
    public void testIsExit() {
        Command rescheduleCmd = new
                RescheduleCommand("reschedule event 1 /at 6/6/2011 1100 to 7/7/2011 1400");
        assertFalse(rescheduleCmd.isExit());
    }

    @Test
    public void testRescheduleOutput() throws ParseException, DukeException {

        Command rescheduleCmd = new
                RescheduleCommand("reschedule deadline 4 /by 16/9/2019 1800");
        ui.clearOutputString();
        rescheduleCmd.execute(tasks, ui, storage);
        assertEquals(" Noted. I've removed this task:\n"
                        + "  " + tasks.getCheckList().get(4-1).toString() + "\n"
                        + " Now you have 7 tasks in the list.\n" + " Got it. I've added this task: \n"
                        + "     " + tasks.getCheckList().get(8-1).toString() + "\n"
                        + " Now you have 8 tasks in the list.\n"
                , ui.getOutputString());
    }
}
*/