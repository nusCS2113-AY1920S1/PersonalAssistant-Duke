package duke.parser;

import duke.command.CommandParams;
import duke.exception.DukeException;
import duke.exception.DukeRuntimeException;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;


public class CommandParamsTest {

    @Test
    public void testCorrectParamValues() throws DukeException {
        CommandParams testParams = new CommandParams("add 2.12 /description hello /tag a b c");
        assertEquals(testParams.getCommand().getName(), "add");
        assertEquals(testParams.getMainParam(), "2.12");
        assertEquals(testParams.getParam("description"), "hello");
        assertEquals(testParams.getParam("tag"), "a b c");
        assertTrue(testParams.containsParams("tag"));
        assertFalse(testParams.containsParams("time"));
    }

    @Test
    public void testCorrectNullParamValues() throws DukeException {
        CommandParams testParams = new CommandParams("add /description /tag not null");
        assertNull(testParams.getMainParam());
        assertEquals(testParams.getParam("tag"), "not null");

        try {
            testParams.getParam("description");
            fail();
        } catch (DukeException e) {
            assertEquals(
                String.format(DukeException.MESSAGE_COMMAND_PARAM_MISSING_VALUE, "description"), e.getMessage());
        }
    }

    @Test
    public void testParamNotFoundException() throws DukeException {
        CommandParams testParams = new CommandParams("add");
        try {
            testParams.getParam("a");
            fail();
        } catch (DukeException e) {
            assertEquals(
                String.format(DukeException.MESSAGE_COMMAND_PARAM_MISSING_VALUE, "a"), e.getMessage());
        }
    }

    @Test
    public void testDuplicateParams() throws DukeException {
        try {
            CommandParams testParams = new CommandParams("add /time /time");
            fail();
        } catch (DukeException e) {
            assertEquals(
                String.format(DukeException.MESSAGE_COMMAND_PARAM_DUPLICATE, "time"), e.getMessage());
        }
    }

    @Test
    public void testAbbreviationFunctionality() throws DukeException {
        try {
            CommandParams testParams = new CommandParams("b");
            fail();
        } catch (DukeException e) {
            assertEquals(
                DukeException.MESSAGE_COMMAND_NAME_UNKNOWN, e.getMessage());
        }

        CommandParams testParams = new CommandParams("ad /d description");
        assertEquals(testParams.getCommand().getName(), "add");
        assertEquals(testParams.getParam("description"), "description");
    }
}
