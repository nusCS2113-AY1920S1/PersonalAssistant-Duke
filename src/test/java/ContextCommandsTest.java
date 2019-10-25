import duke.command.ByeCommand;
import duke.command.HomeHelpCommand;
import duke.command.HomeNewCommand;
import duke.command.Parser;
import duke.exception.DukeException;
import duke.ui.Context;
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
                    HomeNewCommand.class);
            assertEquals(actualParser.parse("help").getClass(),
                    HomeHelpCommand.class);
        } catch (DukeException excp) {
            fail("Exception thrown while extracting valid commands!");
        }
    }
}
