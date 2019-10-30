import duke.command.Command;
import duke.command.Parser;
import duke.exception.DukeException;
import duke.ui.context.UiContext;
import mocks.DoctorCommand;
import mocks.TestCommands;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

public class ParserTest {

    // TODO check if exceptions are thrown for incorrect input formats

    private Parser uut = new Parser(new UiContext(), new TestCommands());

    @Test
    public void parseCommands_fullSwitchNames_argumentsExtracted() {
        try {
            Command testCmd = uut.parse("doctor Hello -switch World -optswitch Optional -maybe berhabs -none");
            DoctorCommand docCmd = (DoctorCommand) testCmd;
            assertEquals("Hello", docCmd.getArg());
            assertEquals("World", docCmd.getSwitchVal("switch"));
            assertEquals("Optional", docCmd.getSwitchVal("optswitch"));
            assertEquals("berhabs", docCmd.getSwitchVal("maybe"));
            assertTrue(docCmd.getSwitchVals().containsKey("none"));
        } catch (DukeException excp) {
            fail("Exception thrown while extracting valid test command!");
        }
    }

    @Test
    public void parseCommands_optionalArgOmitted_argumentsExtracted() {
        try {
            Command testCmd = uut.parse("doctor Hello -switch World -maybe -none");
            DoctorCommand docCmd = (DoctorCommand) testCmd;
            assertEquals("Hello", docCmd.getArg());
            assertEquals("World", docCmd.getSwitchVal("switch"));
            assertTrue(docCmd.getSwitchVals().containsKey("maybe"));
            assertTrue(docCmd.getSwitchVals().containsKey("none"));
        } catch (DukeException excp) {
            fail("Exception thrown when missing optional argument!");
        }
    }

    @Test
    public void parseCommands_optionalSwitchOmitted_argumentsExtracted() {
        try {
            Command testCmd = uut.parse("doctor Hello -switch World");
            DoctorCommand docCmd = (DoctorCommand) testCmd;
            assertEquals("Hello", docCmd.getArg());
            assertEquals("World", docCmd.getSwitchVal("switch"));
        } catch (DukeException excp) {
            fail("Exception thrown when missing optional switch!");
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
    public void parseCommands_stringsAndEscapes_argumentsExtracted() {
        try {
            Command testCmd = uut.parse("doctor \"Hello\\\\World\" -switch\"double \\\" quote\" -none"
                            + "-maybe escaped\\\\backslash");
            DoctorCommand docCmd = (DoctorCommand) testCmd;
            assertEquals("Hello\\World", docCmd.getArg());
            assertEquals("double \" quote", docCmd.getSwitchVal("switch"));
            assertEquals("escaped\\backslash", docCmd.getSwitchVal("maybe"));
            assertTrue(docCmd.getSwitchVals().containsKey("none"));
        } catch (DukeException excp) {
            fail("Exception thrown when parsing strings and escapes!");
        }
    }
}
