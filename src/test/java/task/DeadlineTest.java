package task;

import exception.DukeException;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertFalse;

class DeadlineTest {

    private Deadline testDeadline = new Deadline("Sleep /by 01-01-1970 2200");
    private Deadline testDeadlineOverload = new Deadline("0", "Sleep", "01-01-1970 2200");

    DeadlineTest() throws DukeException {
    }

    @Test
    void testExceptions() {
        //Test wrong date format
        try {
            assertEquals("Buffer", new Deadline("Deadline /by 012312 23123"));
            fail();
        } catch (Exception e) {
            assertEquals("Error! Please enter date in the format DD-MM-YYYY 2359.", e.getMessage());
        }

        //Test too many /by
        try {
            assertEquals("Buffer", new Deadline("Deadline /by /by 01-01-1970 2200"));
            fail();
        } catch (Exception e) {
            assertEquals("Too many /by in String", e.getMessage());
        }

        //Test no /by
        try {
            assertEquals("Buffer", new Deadline("Deadline 01-01-1970 2200"));
            fail();
        } catch (Exception e) {
            assertEquals("Please use /by to indicate date", e.getMessage());
        }

        //Test no /to
        try {
            testDeadline.snooze("12-12-2013 2345");
            fail();
        } catch (Exception e) {
            assertEquals("Please use /to to indicate date", e.getMessage());
        }

        //Test too many /to
        try {
            testDeadline.snooze("/to /to 12-12-2013 2345");
            fail();
        } catch (Exception e) {
            assertEquals("Too many /to in String", e.getMessage());
        }
    }

    @Test
    void testToList() {
        assertEquals("[D][N] Sleep (By: 01-01-1970 2200)", testDeadline.toList());
        assertEquals("[D][N] Sleep (By: 01-01-1970 2200)", testDeadlineOverload.toList());
    }

    @Test
    void testSnooze() throws DukeException {
        testDeadline.snooze("/to 12-12-2013 2345");
        testDeadlineOverload.snooze("/to 12-12-2013 2345");
        assertEquals("12-12-2013 2345", testDeadline.getDueDate());
        assertEquals("12-12-2013 2345", testDeadlineOverload.getDueDate());
        assertEquals("[D][N] Sleep (By: 12-12-2013 2345)", testDeadline.toList());
        assertEquals("[D][N] Sleep (By: 12-12-2013 2345)", testDeadlineOverload.toList());
    }

    @Test
    void testGetStatusIcon() throws DukeException {
        assertEquals("N", testDeadline.getStatusIcon());
        assertEquals("N", testDeadlineOverload.getStatusIcon());
        assertFalse(testDeadline.checkCompletion());
        assertFalse(testDeadlineOverload.checkCompletion());
        testDeadline.markDone();
        testDeadlineOverload.markDone();
        assertTrue(testDeadline.checkCompletion());
        assertTrue(testDeadlineOverload.checkCompletion());
        assertEquals("Y", testDeadline.getStatusIcon());
        assertEquals("Y", testDeadlineOverload.getStatusIcon());

        try {
            testDeadline.markDone();
            fail();
        } catch (Exception e) {
            assertEquals("But good sir, this task is already done!", e.getMessage());
        }

        try {
            testDeadlineOverload.markDone();
            fail();
        } catch (Exception e) {
            assertEquals("But good sir, this task is already done!", e.getMessage());
        }
    }


    @Test
    void testGetType() {
        assertEquals("D", testDeadline.getType());
        assertEquals("D", testDeadlineOverload.getType());
    }

    @Test
    void testGetDescription() {
        assertEquals("Sleep", testDeadline.getDescription());
        assertEquals("Sleep", testDeadlineOverload.getDescription());
    }

    @Test
    void testGetDueDate() {
        assertEquals("01-01-1970 2200", testDeadline.getDueDate());
        assertEquals("01-01-1970 2200", testDeadlineOverload.getDueDate());
    }
}