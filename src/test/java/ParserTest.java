import duke.command.ByeCommand;
import duke.command.Command;
import duke.command.HomeNewCommand;
import duke.command.Parser;
import duke.exception.DukeException;
import duke.ui.Context;
import mocks.DoctorCommand;
import mocks.TestCommands;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

public class ParserTest {

    // TODO move checks for individual commands to CommandTest
    // TODO check if exceptions are thrown for incorrect input formats

    private Parser uut = new Parser(Context.HOME, new TestCommands());

    @Test
    public void parseCommands_validHomeCommands_correctCommandsReturned() {
        Parser actualParser = new Parser(Context.HOME);
        try {
            assertEquals(ByeCommand.class, actualParser.parse("bye").getClass());
            assertEquals(actualParser.parse("new -n Hello -b 100 -a world").getClass(),
                    HomeNewCommand.class);
        } catch (DukeException excp) {
            fail("Exception thrown while extracting valid commands!");
        }
    }

    @Test
    public void parseCommands_fullSwitchNames_argumentsExtracted() {
        try {
            Command testCmd = uut.parse("doctor Hello -switch World -optswitch Optional");
            DoctorCommand docCmd = (DoctorCommand) testCmd;
            assertEquals("Hello", docCmd.getArg());
            assertEquals("World", docCmd.getSwitchVal("switch"));
            assertEquals("Optional", docCmd.getSwitchVal("optswitch"));
        } catch (DukeException excp) {
            fail("Exception thrown while extracting valid test command!");
        }
    }

    @Test
    public void parseCommands_differentOrder_argumentsExtracted() {
        try {
            Command testCmd = uut.parse("doctor -switch World -optswitch Optional Hello");
            DoctorCommand docCmd = (DoctorCommand) testCmd;
            assertEquals("Hello", docCmd.getArg());
            assertEquals("World", docCmd.getSwitchVal("switch"));
            assertEquals("Optional", docCmd.getSwitchVal("optswitch"));
        } catch (DukeException excp) {
            fail("Exception thrown while extracting arguments in different order!");
        }
    }

    @Test
    public void parseCommands_optionalOmitted_argumentsExtracted() {
        try {
            Command testCmd = uut.parse("doctor -switch World Hello");
            DoctorCommand docCmd = (DoctorCommand) testCmd;
            assertEquals("Hello", docCmd.getArg());
            assertEquals("World", docCmd.getSwitchVal("switch"));
        } catch (DukeException excp) {
            fail("Exception thrown when missing optional argument!");
        }
    }

    @Test
    public void parseCommands_stringsAndEscapes_argumentsExtracted() {
        try {
            Command testCmd = uut.parse("doctor \"Hello\\\\World\" -switch \"double \\\" quote\"");
            DoctorCommand docCmd = (DoctorCommand) testCmd;
            assertEquals("Hello\\World", docCmd.getArg());
            assertEquals("double \" quote", docCmd.getSwitchVal("switch"));
        } catch (DukeException excp) {
            fail("Exception thrown when parsing strings and escapes!");
        }
    }
}
