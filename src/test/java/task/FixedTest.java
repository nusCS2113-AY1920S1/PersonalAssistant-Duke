package task;

import exception.DukeException;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertFalse;

class FixedTest {
    private Fixed testFixed = new Fixed("Sleep /need 8 hours");
    private Fixed testFixedOverload = new Fixed("0", "Sleep","8 hours");

    FixedTest() throws DukeException {
    }

    @Test
    void testExceptionThrown() {
        try {
            assertEquals("/ast", new Fixed("Sleep /need /need 8 hours"));
            fail();
        } catch (Exception e) {
            assertEquals("Too many /need in String", e.getMessage());
        }

        try {
            assertEquals("/ast", new Fixed("Sleep Work"));
            fail();
        } catch (Exception e) {
            assertEquals("Please use /need to indicate the duration needed"
                    + " to complete this task", e.getMessage());
        }
    }

    @Test
    void testGetType() {
        assertEquals("F", testFixed.getType());
        assertEquals("F", testFixedOverload.getType());
    }

    @Test
    void testGetAfter() {
        assertEquals("8 hours", testFixed.getAfter());
        assertEquals("8 hours", testFixedOverload.getAfter());
    }

    @Test
    void testGetStatusIcon() throws DukeException {
        assertEquals("N", testFixed.getStatusIcon());
        assertEquals("N", testFixedOverload.getStatusIcon());
        assertFalse(testFixed.checkCompletion());
        assertFalse(testFixedOverload.checkCompletion());
        testFixed.markDone();
        testFixedOverload.markDone();
        assertTrue(testFixed.checkCompletion());
        assertTrue(testFixedOverload.checkCompletion());
        assertEquals("Y", testFixed.getStatusIcon());
        assertEquals("Y", testFixedOverload.getStatusIcon());

        try {
            testFixed.markDone();
            fail();
        } catch (Exception e) {
            assertEquals("But good sir, this task is already done!", e.getMessage());
        }

        try {
            testFixedOverload.markDone();
            fail();
        } catch (Exception e) {
            assertEquals("But good sir, this task is already done!", e.getMessage());
        }
    }

    @Test
    void testToList() {
        assertEquals("[F][N] Sleep (Need: 8 hours)", testFixed.toList());
        assertEquals("[F][N] Sleep (Need: 8 hours)", testFixedOverload.toList());
    }

    @Test
    void testGetDescription() {
        assertEquals("Sleep", testFixed.getDescription());
        assertEquals("Sleep", testFixedOverload.getDescription());
    }
}