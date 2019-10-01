package parser;

import exception.DukeException;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;


public class CommandParamsTest {

    @Test
    public void testCorrectParamValues() {
        CommandParams testParams = new CommandParams("name description goes here /a is a /b is b /c is c");
        assertEquals(testParams.getCommandName(), "name");
        assertEquals(testParams.getMainParam(), "description goes here");
        assertEquals(testParams.getParam("a"), "is a");
        assertEquals(testParams.getOptionalParam("a"), "is a");
        assertEquals(testParams.getParam("b"), "is b");
        assertEquals(testParams.getOptionalParam("b"), "is b");
        assertEquals(testParams.getParam("c"), "is c");
        assertEquals(testParams.getOptionalParam("c"), "is c");
        assertNull(testParams.getOptionalParam("d"));
        assertTrue(testParams.containsParams("a"));
        assertFalse(testParams.containsParams("d"));
    }

    @Test
    public void testCorrectNullParamValues() {
        CommandParams testParams = new CommandParams("noMainParam /a /b /c /d not null");
        assertNull(testParams.getMainParam());
        assertNull(testParams.getOptionalParam("a"));
        assertNull(testParams.getOptionalParam("b"));
        assertNull(testParams.getOptionalParam("c"));
        assertEquals(testParams.getOptionalParam("d"), "not null");
        assertNull(testParams.getOptionalParam("e"));

        try {
            testParams.getParam("a");
            fail();
        } catch (DukeException e) {
            assertEquals("☹ OOPS!!! You need to give me a value for a!", e.getMessage());
        }

        try {
            testParams.getParam("b");
            fail();
        } catch (DukeException e) {
            assertEquals("☹ OOPS!!! You need to give me a value for b!", e.getMessage());
        }

        try {
            testParams.getParam("c");
            fail();
        } catch (DukeException e) {
            assertEquals("☹ OOPS!!! You need to give me a value for c!", e.getMessage());
        }

        try {
            testParams.getParam("e");
            fail();
        } catch (DukeException e) {
            assertEquals("☹ OOPS!!! You need to give me a value for e!", e.getMessage());
        }
    }

    @Test
    public void testParamNotFoundException() {
        CommandParams testParams = new CommandParams("noParams");
        try {
            testParams.getParam("a");
            fail();
        } catch (DukeException e) {
            assertEquals("☹ OOPS!!! You need to give me a value for a!", e.getMessage());
        }
    }

    @Test
    public void testDuplicateParams() {
        try {
            CommandParams testParams = new CommandParams("sameParams /a first /a second");
            fail();
        } catch (DukeException e) {
            assertEquals("☹ OOPS!!! You specified a twice!", e.getMessage());
        }
    }
}
