package task;

import exception.DukeException;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertFalse;

class AfterTest {
    private After testAfter = new After("Sleep /after Work");
    private After testAfterOverload = new After("0", "Sleep", "Work");

    AfterTest() throws DukeException {
    }

    @Test
    void testExceptionThrown() {
        try {
            assertEquals("/ast", new After("Sleep /after /after Work"));
            fail();
        } catch (Exception e) {
            assertEquals("Too many /after in String", e.getMessage());
        }

        try {
            assertEquals("/ast", new After("Sleep Work"));
            fail();
        } catch (Exception e) {
            assertEquals("Please use /after to indicate what needs to be done before doing "
                    + "this task", e.getMessage());
        }
    }

    @Test
    void testGetType() {
        assertEquals("A", testAfter.getType());
        assertEquals("A", testAfterOverload.getType());
    }

    @Test
    void testGetAfter() {
        assertEquals("Work", testAfter.getAfter());
        assertEquals("Work", testAfterOverload.getAfter());
    }

    @Test
    void testGetStatusIcon() throws DukeException {
        assertEquals("N", testAfter.getStatusIcon());
        assertEquals("N", testAfterOverload.getStatusIcon());
        assertFalse(testAfter.checkCompletion());
        assertFalse(testAfterOverload.checkCompletion());
        testAfter.markDone();
        testAfterOverload.markDone();
        assertTrue(testAfter.checkCompletion());
        assertTrue(testAfterOverload.checkCompletion());
        assertEquals("Y", testAfter.getStatusIcon());
        assertEquals("Y", testAfterOverload.getStatusIcon());

        try {
            testAfter.markDone();
            fail();
        } catch (Exception e) {
            assertEquals("But good sir, this task is already done!", e.getMessage());
        }

        try {
            testAfterOverload.markDone();
            fail();
        } catch (Exception e) {
            assertEquals("But good sir, this task is already done!", e.getMessage());
        }
    }

    @Test
    void testToList() {
        assertEquals("[A][N] Sleep (After: Work)", testAfter.toList());
        assertEquals("[A][N] Sleep (After: Work)", testAfterOverload.toList());
    }

    @Test
    void testGetDescription() {
        assertEquals("Sleep", testAfter.getDescription());
        assertEquals("Sleep", testAfterOverload.getDescription());
    }
}