package task;

import exception.DukeException;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertFalse;

class RecurringTest {
    private Recurring testRecurring = new Recurring("Sleep /every 16 hours");
    private Recurring testRecurringOverload = new Recurring("0", "Sleep","16 hours");

    RecurringTest() throws DukeException {
    }

    @Test
    void testExceptionThrown() {
        try {
            assertEquals("/ast", new Recurring("Sleep /every /every 16 hours"));
            fail();
        } catch (Exception e) {
            assertEquals("Too many /every in String", e.getMessage());
        }

        try {
            assertEquals("/ast", new Recurring("Sleep Work"));
            fail();
        } catch (Exception e) {
            assertEquals("Please use /every to indicate frequency of task", e.getMessage());
        }
    }

    @Test
    void testGetType() {
        assertEquals("R", testRecurring.getType());
        assertEquals("R", testRecurringOverload.getType());
    }

    @Test
    void testGetAfter() {
        assertEquals("16 hours", testRecurring.getAfter());
        assertEquals("16 hours", testRecurringOverload.getAfter());
    }

    @Test
    void testGetStatusIcon() throws DukeException {
        assertEquals("N", testRecurring.getStatusIcon());
        assertEquals("N", testRecurringOverload.getStatusIcon());
        assertFalse(testRecurring.checkCompletion());
        assertFalse(testRecurringOverload.checkCompletion());
        testRecurring.markDone();
        testRecurringOverload.markDone();
        assertTrue(testRecurring.checkCompletion());
        assertTrue(testRecurringOverload.checkCompletion());
        assertEquals("Y", testRecurring.getStatusIcon());
        assertEquals("Y", testRecurringOverload.getStatusIcon());

        try {
            testRecurring.markDone();
            fail();
        } catch (Exception e) {
            assertEquals("But good sir, this task is already done!", e.getMessage());
        }

        try {
            testRecurringOverload.markDone();
            fail();
        } catch (Exception e) {
            assertEquals("But good sir, this task is already done!", e.getMessage());
        }
    }

    @Test
    void testToList() {
        assertEquals("[R][N] Sleep (Every: 16 hours)", testRecurring.toList());
        assertEquals("[R][N] Sleep (Every: 16 hours)", testRecurringOverload.toList());
    }

    @Test
    void testGetDescription() {
        assertEquals("Sleep", testRecurring.getDescription());
        assertEquals("Sleep", testRecurringOverload.getDescription());
    }
}