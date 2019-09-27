package task;

import exception.DukeException;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertFalse;

class EventTest {

    private Event testEvent = new Event("Sleep /at 01-01-1970 2200");
    private Event testEventOverload = new Event("0", "Sleep", "01-01-1970 2200");

    EventTest() throws DukeException {
    }

    @Test
    void testExceptions() {
        //Test wrong date format
        try {
            assertEquals("Buffer", new Event("Deadline /at 012312 23123"));
            fail();
        } catch (Exception e) {
            assertEquals("Error! Please enter date in the format DD-MM-YYYY 2359.", e.getMessage());
        }

        //Test too many /at without a date following
        try {
            assertEquals("Buffer", new Event("Deadline /at /at 01-01-1970 2200"));
            fail();
        } catch (Exception e) {
            assertEquals("Error! Please enter date in the format DD-MM-YYYY 2359.", e.getMessage());
        }

        //Test no /at
        try {
            assertEquals("Buffer", new Event("Deadline 01-01-1970 2200"));
            fail();
        } catch (Exception e) {
            assertEquals("Please use /at to indicate date", e.getMessage());
        }

        //Test no /to
        try {
            testEvent.snooze("12-12-2013 2345");
            fail();
        } catch (Exception e) {
            assertEquals("Please use /to to indicate date", e.getMessage());
        }

        //Test too many /to
        try {
            testEvent.snooze("/to /to 12-12-2013 2345");
            fail();
        } catch (Exception e) {
            assertEquals("Too many /to in String", e.getMessage());
        }
    }

    @Test
    void testToList() {
        assertEquals("[E][N] Sleep (At: 01-01-1970 2200)", testEvent.toList());
        assertEquals("[E][N] Sleep (At: 01-01-1970 2200)", testEventOverload.toList());
    }

    @Test
    void testSnooze() throws DukeException {
        testEvent.snooze("/to 12-12-2013 2345");
        testEventOverload.snooze("/to 12-12-2013 2345");
        assertEquals("12-12-2013 2345", testEvent.getDueDate());
        assertEquals("12-12-2013 2345", testEventOverload.getDueDate());
        assertEquals("[E][N] Sleep (At: 12-12-2013 2345)", testEvent.toList());
        assertEquals("[E][N] Sleep (At: 12-12-2013 2345)", testEventOverload.toList());
    }

    @Test
    void testGetStatusIcon() throws DukeException {
        assertEquals("N", testEvent.getStatusIcon());
        assertEquals("N", testEventOverload.getStatusIcon());
        assertFalse(testEvent.checkCompletion());
        assertFalse(testEventOverload.checkCompletion());
        testEvent.markDone();
        testEventOverload.markDone();
        assertTrue(testEvent.checkCompletion());
        assertTrue(testEventOverload.checkCompletion());
        assertEquals("Y", testEvent.getStatusIcon());
        assertEquals("Y", testEventOverload.getStatusIcon());

        try {
            testEvent.markDone();
            fail();
        } catch (Exception e) {
            assertEquals("But good sir, this task is already done!", e.getMessage());
        }

        try {
            testEventOverload.markDone();
            fail();
        } catch (Exception e) {
            assertEquals("But good sir, this task is already done!", e.getMessage());
        }
    }


    @Test
    void testGetType() {
        assertEquals("E", testEvent.getType());
        assertEquals("E", testEventOverload.getType());
    }

    @Test
    void testGetDescription() {
        assertEquals("Sleep", testEvent.getDescription());
        assertEquals("Sleep", testEventOverload.getDescription());
    }

    @Test
    void testGetDueDate() {
        assertEquals("01-01-1970 2200", testEvent.getDueDate());
        assertEquals("01-01-1970 2200", testEventOverload.getDueDate());
    }

    @Test
    void testTentative() throws DukeException {
        Event testTentativeEvent = new Event("Sleep /at 01-01-1970 2200 "
                                                                + "/at 02-02-1971 2200 "
                                                                + "/at 03-03-1972 2200 "
                                                                + "/at 04-04-1973 2200");
        assertTrue(testTentativeEvent.tentativeExists());
        assertEquals("01-01-1970 2200 OR 02-02-1971 2200 OR 03-03-1972 2200 "
                + "OR 04-04-1973 2200", testTentativeEvent.getDueDate());
        assertEquals("Tue Feb 02 22:00:00 SGT 1971", testTentativeEvent.getTentativeDate(1).toString());
        assertEquals("Fri Mar 03 22:00:00 SGT 1972", testTentativeEvent.getTentativeDate(2).toString());
        assertEquals("Wed Apr 04 22:00:00 SGT 1973", testTentativeEvent.getTentativeDate(3).toString());
        assertFalse(testTentativeEvent.outsideTentative(2)); //Check if a request is outside tentative index
        assertTrue(testTentativeEvent.outsideTentative(26));
        testTentativeEvent.clearTentative();
        assertFalse(testTentativeEvent.tentativeExists());
    }

}