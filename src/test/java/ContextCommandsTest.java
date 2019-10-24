import duke.command.ByeCommand;
import duke.command.Command;
import duke.command.NewPatientCommand;
import duke.command.Parser;
import duke.exception.DukeException;
import duke.ui.Context;
import mocks.DoctorCommand;
import mocks.TestCommands;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

/**
 * Tests for the parser's ability to correctly identify Commands in different contexts.
 */
public class ContextCommandsTest {

    @Test
    public void parseCommands_validHomeCommands_correctCommandsReturned() {
        Parser actualParser = new Parser(Context.HOME);
        try {
            assertEquals(ByeCommand.class, actualParser.parse("bye").getClass());
            assertEquals(actualParser.parse("new -n Hello -b 100 -a world").getClass(),
                    NewPatientCommand.class);
        } catch (DukeException excp) {
            fail("Exception thrown while extracting valid commands!");
        }
    }
}
