
import duke.command.ByeCommand;
import duke.command.DeleteCommand;
import duke.command.DoneCommand;
import duke.command.FindCommand;
import duke.command.ListCommand;
import duke.command.NewDeadlineCommand;
import duke.command.NewEventCommand;
import duke.command.NewRecurringTaskCommand;
import duke.command.NewToDoCommand;
import duke.command.Parser;
import duke.command.SnoozeCommand;
import duke.exception.DukeException;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

public class ParserTest {

    // TODO check whether every task can be detected
    // TODO check if exception are thrown for incorrect input formats

    private Parser uut = new Parser();

    /*@Test
    public void parseCommands_validCommands_correctCommandsReturned() {
        try {
            assertEquals(uut.parse("list").getClass(), ListCommand.class);
            assertEquals(uut.parse("todo JUnit tests").getClass(), NewToDoCommand.class);
            assertEquals(uut.parse("event tutorial /at 12/09/2019 1400 /to 12/10/2019 1200").getClass(),
                        NewEventCommand.class);
       } catch (DukeException excp) {
            fail("Exception thrown while extracting commands!");
        }
    }*/
}
