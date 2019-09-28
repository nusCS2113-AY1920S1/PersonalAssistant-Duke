import commands.Command;
import commands.FreeTimeCommand;
import controlpanel.DukeException;
import controlpanel.Storage;
import controlpanel.Ui;
import org.junit.jupiter.api.Test;
import tasks.TaskList;

import java.text.ParseException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

public class FreeTimeTest {

    private Ui ui;
    private TaskList tasks;
    private Storage storage;

    public FreeTimeTest() {
        ui = new Ui();
        storage = new Storage("D:/main/data/tasks-test.txt");
        tasks = new TaskList(storage.load());
    }

    @Test
    public void testIsExit() throws ParseException {
        Command freeTimeCmd = new FreeTimeCommand("2/2/2019 1400", 3);
        assertFalse(freeTimeCmd.isExit());

    }

    @Test
    public void testFindFreeTime() throws ParseException, DukeException {
        ui.clearOutputString();
        Command freeTimeCmd = new FreeTimeCommand("1/1/2020 0000", 2);
        freeTimeCmd.execute(tasks, ui, storage);
        assertEquals("  The nearest time slot: \n" +
                "    1/1/2020 0000 ~ 1/1/2020 0200 is available\n",ui.getOutputString());
    }

}
