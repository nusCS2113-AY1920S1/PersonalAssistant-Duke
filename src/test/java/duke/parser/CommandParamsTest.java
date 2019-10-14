package duke.parser;

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
        CommandParams testParams = new CommandParams("name description goes here /a is a /b is b /c is c");
        assertEquals(testParams.getCommandName(), "name");
        assertEquals(testParams.getMainParam(), "description goes here");
        assertEquals(testParams.getParam("a"), "is a");
        assertEquals(testParams.getParam("a"), "is a");
        assertEquals(testParams.getParam("b"), "is b");
        assertEquals(testParams.getParam("b"), "is b");
        assertEquals(testParams.getParam("c"), "is c");
        assertEquals(testParams.getParam("c"), "is c");
        assertNull(testParams.getParam("d"));
        assertTrue(testParams.containsParams("a"));
        assertFalse(testParams.containsParams("d"));
    }

    @Test
    public void testCorrectNullParamValues() throws DukeException {
        CommandParams testParams = new CommandParams("noMainParam /a /b /c /d not null");
        assertNull(testParams.getMainParam());
        assertNull(testParams.getParam("a"));
        assertNull(testParams.getParam("b"));
        assertNull(testParams.getParam("c"));
        assertEquals(testParams.getParam("d"), "not null");
        assertNull(testParams.getParam("e"));

        try {
            testParams.getParam("a");
            fail();
        } catch (DukeRuntimeException e) {
            assertEquals("☹ OOPS!!! You need to give me a value for a!", e.getMessage());
        }

        try {
            testParams.getParam("b");
            fail();
        } catch (DukeRuntimeException e) {
            assertEquals("☹ OOPS!!! You need to give me a value for b!", e.getMessage());
        }

        try {
            testParams.getParam("c");
            fail();
        } catch (DukeRuntimeException e) {
            assertEquals("☹ OOPS!!! You need to give me a value for c!", e.getMessage());
        }

        try {
            testParams.getParam("e");
            fail();
        } catch (DukeRuntimeException e) {
            assertEquals("☹ OOPS!!! You need to give me a value for e!", e.getMessage());
        }
    }

    @Test
    public void testParamNotFoundException() throws DukeException {
        CommandParams testParams = new CommandParams("noParams");
        try {
            testParams.getParam("a");
            fail();
        } catch (DukeRuntimeException e) {
            assertEquals("☹ OOPS!!! You need to give me a value for a!", e.getMessage());
        }
    }

    @Test
    public void testDuplicateParams() throws DukeException {
        try {
            CommandParams testParams = new CommandParams("sameParams /a first /a second");
            fail();
        } catch (DukeRuntimeException e) {
            assertEquals("☹ OOPS!!! You specified a twice!", e.getMessage());
        }
    }
}
