import duke.command.*;
import duke.exception.DukeException;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

public class ParserTest {

    // TODO test failure modes
    // this is going to take a lot of typing

    private Parser uut = new Parser();

    @Test
    public void parseCommands_validCommands_correctCommandsReturned() {
        try {
            assertEquals(uut.parse("list").getClass(), ListCommand.class);
            assertEquals(uut.parse("todo JUnit tests").getClass(), NewToDoCommand.class);
            assertEquals(uut.parse("event tutorial /at 12/09/2019 1400").getClass(), NewEventCommand.class);
            assertEquals(uut.parse("deadline submission /by 12/09/2019 1400").getClass(),
                    NewDeadlineCommand.class);
            assertEquals(uut.parse("done 1").getClass(), DoneCommand.class);
            assertEquals(uut.parse("bye").getClass(), ByeCommand.class);
            assertEquals(uut.parse("delete 1").getClass(), DeleteCommand.class);
            assertEquals(uut.parse("find u").getClass(), FindCommand.class);
        } catch (DukeException excp) {
            fail("Exception thrown while extracting commands!");
        }
    }
}
