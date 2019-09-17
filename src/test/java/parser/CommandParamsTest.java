package parser;

import exception.DukeException;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class CommandParamsTest {

    @Test
    public void testCorrectParamValues() {
        CommandParams testParams = new CommandParams("name desc /a is a /b is b /c is c");
        assertEquals(testParams.getCommandType(), "name");
        assertEquals(testParams.getMainParam(), "desc");
        assertEquals(testParams.getParam("a"), "is a");
        assertEquals(testParams.getParam("b"), "is b");
        assertEquals(testParams.getParam("c"), "is c");
        assertTrue(testParams.containsParam("a"));
        assertFalse(testParams.containsParam("d"));
    }

    @Test
    public void testCorrectNullParamValues() {
        CommandParams testParams = new CommandParams("noMainParam /a /b /c /d not null");
        assertNull(testParams.getMainParam());
        assertNull(testParams.getParam("a"));
        assertNull(testParams.getParam("b"));
        assertNull(testParams.getParam("c"));
        assertNotNull(testParams.getParam("d"));
    }

    @Test
    public void testParamNotFoundException() {
        CommandParams testParams = new CommandParams("noParams");
        try {
            testParams.getParam("a");
        } catch (DukeException e) {
            assertEquals("☹ OOPS!!! You need to give me a value for a!", e.getMessage());
        }
    }

    @Test
    public void testDuplicateParams() {
        try {
            CommandParams testParams = new CommandParams("sameParams /a first /a second");
        } catch (DukeException e) {
            assertEquals("☹ OOPS!!! You specified a twice!", e.getMessage());
        }
    }
}
