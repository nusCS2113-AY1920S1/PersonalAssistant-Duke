package task;

import exception.DukeException;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertFalse;

class WithinTest {
    private Within testWithin = new Within("Sleep /between Monday and Friday");
    private Within testWithinOverload = new Within("0", "Sleep", "Monday and Friday");

    WithinTest() throws DukeException {
    }

    @Test
    void testExceptionThrown() {
        try {
            assertEquals("/ast", new Within("Sleep /between /between Monday and Friday"));
            fail();
        } catch (Exception e) {
            assertEquals("Too many /between in String", e.getMessage());
        }

        try {
            assertEquals("/ast", new Within("Sleep Monday and Friday"));
            fail();
        } catch (Exception e) {
            assertEquals("Please use /between to indicate the window to complete this task", e.getMessage());
        }
    }

    @Test
    void testGetType() {
        assertEquals("W", testWithin.getType());
        assertEquals("W", testWithinOverload.getType());
    }

    @Test
    void testGetWithin() {
        assertEquals("Monday and Friday", testWithin.getAfter());
        assertEquals("Monday and Friday", testWithinOverload.getAfter());
    }

    @Test
    void testGetStatusIcon() throws DukeException {
        assertEquals("N", testWithin.getStatusIcon());
        assertEquals("N", testWithinOverload.getStatusIcon());
        assertFalse(testWithin.checkCompletion());
        assertFalse(testWithinOverload.checkCompletion());
        testWithin.markDone();
        testWithinOverload.markDone();
        assertTrue(testWithin.checkCompletion());
        assertTrue(testWithinOverload.checkCompletion());
        assertEquals("Y", testWithin.getStatusIcon());
        assertEquals("Y", testWithinOverload.getStatusIcon());

        try {
            testWithin.markDone();
            fail();
        } catch (Exception e) {
            assertEquals("But good sir, this task is already done!", e.getMessage());
        }

        try {
            testWithinOverload.markDone();
            fail();
        } catch (Exception e) {
            assertEquals("But good sir, this task is already done!", e.getMessage());
        }
    }

    @Test
    void testToList() {
        assertEquals("[W][N] Sleep (Between: Monday and Friday)", testWithin.toList());
        assertEquals("[W][N] Sleep (Between: Monday and Friday)", testWithinOverload.toList());
    }

    @Test
    void testGetDescription() {
        assertEquals("Sleep", testWithin.getDescription());
        assertEquals("Sleep", testWithinOverload.getDescription());
    }
}