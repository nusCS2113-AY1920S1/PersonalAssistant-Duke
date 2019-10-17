package duketest.command;

import duke.command.SnoozeCommand;
import duke.exceptions.DukeException;

import java.util.ArrayList;
import java.util.List;

import temp.storage.FileHandling;
import duke.tasks.TaskList;
import duke.ui.Ui;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class SnoozeCommandTest {
    private final TaskList taskList = new TaskList();
    private final Ui ui = new Ui();
    private final FileHandling storage = new FileHandling("storeData.txt");

    @Test
    public void checkValidCommand() {
        List<String> splitInput = new ArrayList<>();
        splitInput.add("snooze");
        SnoozeCommand obj = new SnoozeCommand(splitInput, "snooze");

        assertThrows(DukeException.class,() ->
                obj.execute(taskList, ui, storage));
    }

    @Test
    public void checkValidFormat() {
        List<String> splitInput = new ArrayList<>();
        splitInput.add("reschedule");
        splitInput.add("1");
        SnoozeCommand obj = new SnoozeCommand(splitInput,"reschedule");
        assertThrows(DukeException.class,() ->
                obj.execute(taskList, ui, storage));
    }
}