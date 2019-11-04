package tests;

import duke.command.Command;
import duke.command.home.HomeNewCommand;
import duke.command.Parser;
import duke.exception.DukeException;
import duke.ui.context.UiContext;
import mocks.DoctorCommand;
import mocks.TestCommands;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

public class ParserTest {

    // TODO check if exceptions are thrown for incorrect input formats

    private Parser uut = new Parser(new UiContext(), new TestCommands());

    @Test
    public void parseCommands_fullSwitchNames_argumentsExtracted() {
        try {
            Command testCmd = uut.parse("doctor Hello -switch World -optswitch Optional -maybe berhabs -none ");
            DoctorCommand docCmd = (DoctorCommand) testCmd;
            assertEquals("Hello", docCmd.getArg());
            assertEquals("World", docCmd.getSwitchVal("switch"));
            assertEquals("Optional", docCmd.getSwitchVal("optswitch"));
            assertEquals("berhabs", docCmd.getSwitchVal("maybe"));
            assertTrue(docCmd.getSwitchVals().containsKey("none"));
        } catch (DukeException excp) {
            fail("Exception thrown while extracting valid test command" + excp.getMessage());
        }
    }

    @Test
    public void parseCommands_switchesChained_argumentsExtracted() {
        try {
            Command testCmd = uut.parse("doctor Hello -switch World -optswitch Optional -none-maybe");
            DoctorCommand docCmd = (DoctorCommand) testCmd;
            assertEquals("Hello", docCmd.getArg());
            assertEquals("World", docCmd.getSwitchVal("switch"));
            assertEquals("Optional", docCmd.getSwitchVal("optswitch"));
            assertTrue(docCmd.getSwitchVals().containsKey("maybe"));
            assertTrue(docCmd.getSwitchVals().containsKey("none"));
        } catch (DukeException excp) {
            fail("Exception thrown while extracting test command with chained switches: " + excp.getMessage());
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
            fail("Exception thrown while extracting arguments in different order: " + excp.getMessage());
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
            fail("Exception thrown when missing optional argument: " + excp.getMessage());
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
            fail("Exception thrown when parsing strings and escapes: " + excp.getMessage());
        }
    }
}
