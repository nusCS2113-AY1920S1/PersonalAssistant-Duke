import duke.command.ByeCommand;
import duke.command.Command;
import duke.command.NewPatientCommand;
import duke.command.Parser;
import duke.exception.DukeException;
import mocks.DoctorCommand;
import mocks.TestCommands;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

public class ParserTest {

    // TODO check whether every task can be detected
    // TODO check if exception are thrown for incorrect input formats

    private Parser uut = new Parser(new TestCommands());

    @Test
    public void parseCommands_validCommands_correctCommandsReturned() {
        Parser actualParser = new Parser();
        try {
            assertEquals(actualParser.parse("bye").getClass(), ByeCommand.class);
            assertEquals(actualParser.parse("new -n Hello -b 100 -a world").getClass(),
                    NewPatientCommand.class);
        } catch (DukeException excp) {
            fail("Exception thrown while extracting valid commands!");
        }
    }

    @Test
    public void parseCommands_fullSwitchNames_argumentsExtracted() {
        try {
            Command testCmd = uut.parse("doctor Hello -switch World -optswitch Optional");
            DoctorCommand docCmd = (DoctorCommand) testCmd;
            assertEquals(docCmd.getArg(), "Hello");
            assertEquals(docCmd.getSwitchVal("switch"), "World");
            assertEquals(docCmd.getSwitchVal("optswitch"), "Optional");
        } catch (DukeException excp) {
            fail("Exception thrown while extracting valid commands!");
        }
    }
}
