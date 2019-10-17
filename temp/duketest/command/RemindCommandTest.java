package duketest.command;

import duke.command.Command;
import duke.command.RemindCommand;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertFalse;

public class RemindCommandTest {
    @Test
    void checkRemind() {
        Command c = new RemindCommand();
        assertFalse(c.isExit);
    }
}
