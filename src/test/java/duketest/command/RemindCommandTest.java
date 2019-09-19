package duketest.command;

import duke.command.ByeCommand;
import duke.command.Command;
import duke.command.RemindCommand;
import duke.exceptions.DukeException;
import duke.storage.FileHandling;
import duke.tasks.TaskList;
import duke.ui.Ui;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class RemindCommandTest {
    @Test
    void checkRemind() throws DukeException {
        Command c = new RemindCommand();
        assertFalse(c.isExit);
    }

}
