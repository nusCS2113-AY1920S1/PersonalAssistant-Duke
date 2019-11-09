package tests;

import duke.command.ArgCommand;
import duke.command.Parser;
import duke.exception.DukeException;
import duke.exception.DukeHelpException;
import duke.ui.context.UiContext;
import mocks.DoctorSpec;
import mocks.TestCommands;
import mocks.ValidEmptySpec;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

public class ParserTest {

    // TODO check if exceptions are thrown for incorrect input formats

    private Parser uut = new Parser(new UiContext(), new TestCommands());

    @Test
    public void parseCommand_fullSwitchNames_argumentsExtracted() {
        try {
            ArgCommand testCmd = (ArgCommand) uut.parse("doctor Hello -switch World -optswitch Optional"
                            + " -maybe berhabs -none ");
            String[] switchNames = {"switch", "optswitch", "maybe", "none"};
            String[] switchVals = {"World", "Optional", "berhabs", null};
            ArgCommand docCmd = new ArgCommand(DoctorSpec.getSpec(), "Hello", switchNames, switchVals);
            assertTrue(docCmd.equals(testCmd));
        } catch (DukeException excp) {
            fail("Exception thrown while extracting valid test command: " + excp.getMessage());
        }
    }

    @Test
    public void parseCommand_switchesChained_argumentsExtracted() {
        try {
            ArgCommand testCmd = (ArgCommand) uut.parse("doctor Hello -switch World -optswitch Optional -none-maybe");
            String[] switchNames = {"switch", "optswitch", "maybe", "none"};
            String[] switchVals = {"World", "Optional", null, null};
            ArgCommand docCmd = new ArgCommand(DoctorSpec.getSpec(), "Hello", switchNames, switchVals);
            assertTrue(docCmd.equals(testCmd));
        } catch (DukeException excp) {
            fail("Exception thrown while extracting test command with chained switches: " + excp.getMessage());
        }
    }

    @Test
    public void parseCommand_differentOrder_argumentsExtracted() {
        try {
            ArgCommand testCmd = (ArgCommand) uut.parse("doctor -switch World -optswitch Optional Hello");
            String[] switchNames = {"switch", "optswitch"};
            String[] switchVals = {"World", "Optional"};
            ArgCommand docCmd = new ArgCommand(DoctorSpec.getSpec(), "Hello", switchNames, switchVals);
            assertTrue(docCmd.equals(testCmd));
        } catch (DukeException excp) {
            fail("Exception thrown while extracting arguments in different order: " + excp.getMessage());
        }
    }

    @Test
    public void parseCommand_optionalOmitted_argumentsExtracted() {
        try {
            ArgCommand testCmd = (ArgCommand) uut.parse("doctor -switch World Hello");
            String[] switchNames = {"switch"};
            String[] switchVals = {"World"};
            ArgCommand docCmd = new ArgCommand(DoctorSpec.getSpec(), "Hello", switchNames, switchVals);
            assertTrue(docCmd.equals(testCmd));
        } catch (DukeException excp) {
            fail("Exception thrown when missing optional argument: " + excp.getMessage());
        }
    }

    @Test
    public void parseCommand_stringsAndEscapes_argumentsExtracted() {
        try {
            ArgCommand testCmd = (ArgCommand) uut.parse("doctor Hello\\\\World -switch \"double \\\" quote\""
                    + "-maybe \"escaped \\\\ backslash\"");
            String[] switchNames = {"switch", "maybe"};
            String[] switchVals = {"double \" quote", "escaped \\ backslash"};
            ArgCommand docCmd = new ArgCommand(DoctorSpec.getSpec(), "Hello\\World", switchNames, switchVals);
            assertTrue(docCmd.equals(testCmd));
        } catch (DukeException excp) {
            fail("Exception thrown when parsing strings and escapes: " + excp.getMessage());
        }
    }

    @Test
    public void parseCommand_validEmptyCommand_exceptionNotThrown() {
        try {
            ArgCommand testCmd = (ArgCommand) uut.parse("empty");
            String[] switchNames = {};
            String[] switchVals = {};
            ArgCommand emptyCmd = new ArgCommand(ValidEmptySpec.getSpec(), null, switchNames,
                    switchVals);
            assertTrue(emptyCmd.equals(testCmd));
        } catch (DukeException excp) {
            fail("Exception thrown when parsing valid empty command: " + excp.getMessage());
        }
    }

    @Test
    public void parseCommand_invalidEmptyCommand_exceptionThrown() {
        System.out.println(assertThrows(DukeHelpException.class, () -> {
            uut.parse("doctor       ");
        }).getMessage());
    }

    @Test
    public void parseCommand_argAfterNoArgSwitch_argumentsExtracted() {
        try {
            ArgCommand testCmd = (ArgCommand) uut.parse("doctor -none Hello -switch World -optswitch Optional "
                            + "-maybe berhabs ");
            String[] switchNames = {"switch", "optswitch", "maybe", "none"};
            String[] switchVals = {"World", "Optional", "berhabs", null};
            ArgCommand docCmd = new ArgCommand(DoctorSpec.getSpec(), "Hello", switchNames, switchVals);
            assertTrue(docCmd.equals(testCmd));
        } catch (DukeException excp) {
            fail("Exception thrown while extracting valid test command: " + excp.getMessage());
        }
    }

    @Test
    public void parseCommand_repeatedArgs_exceptionThrown() {
        System.out.println(assertThrows(DukeHelpException.class, () -> {
            uut.parse("doctor Hello -switch Goodbye World");
        }).getMessage());
        System.out.println(assertThrows(DukeHelpException.class, () -> {
            uut.parse("doctor Hello -switch Goodbye -switch World");
        }).getMessage());
    }

    @Test
    public void parseCommand_missingSwitch_exceptionThrown() {
        System.out.println(assertThrows(DukeHelpException.class, () -> {
            uut.parse("doctor Hello");
        }).getMessage());
    }

    @Test
    public void parseCommand_wrongSwitch_exceptionThrown() {
        System.out.println(assertThrows(DukeHelpException.class, () -> {
            uut.parse("doctor Hello -switch Goodbye -maybe -1");
        }).getMessage());
    }
}
