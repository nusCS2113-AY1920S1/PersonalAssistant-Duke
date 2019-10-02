
package duke.task;

import duke.core.DukeException;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;


public class EventTest {
    /**
     * Test the Event.toString()
     */
    @Test
    public void EventStringTest() throws DukeException {
        assertEquals("[E][\u2718] eventTest (at: 2nd of December 1996, 12PM)", new Event("eventTest", "02/12/1996 1235").toString(), "toString result is not expected");
    }

    /**
     * Test the Event.writeTxt()
     */
    @Test
    public void writeFormatTest() throws DukeException {
        assertEquals( "E | 0 | test | 02/12/1996 1235 | ONCE",new Event("test", "02/12/1996 1235").writeTxt(), "The writeToFile format is not expected");
    }

    /**
     * Test the Event.isDone() after a new Event object is being initialized
     */
    @Test
    public void doneStatusTest() throws DukeException {
        assertFalse(new Event("test", "02/12/1996 1235").isDone(), "The newly created Deadline should not be done");
    }

    /**
     * A general test case to test Event class
     * Test steps:
     * 1. Create a Event object
     * 2. Verify Event.isdone(), Event.toString(), Event.writeTxt()
     * 3. Mark the Event object to isDone status.
     * 4. Repeat step 2
     *
     * @throws DukeException if markAsDone is applied to a done task, throw exception with log
     */
    @Test
    public void eventTestCase() throws DukeException {
        // Creata a new event and check its toString() and writeTxt()
        Event event = new Event("eventTest", "02/12/1996 1235");
        assertFalse(event.isDone(), "The newly created event should not be done");
        assertEquals( "[E][\u2718] eventTest (at: 2nd of December 1996, 12PM)",event.toString(), "The writeToFile format is not expected");
        assertEquals( "E | 0 | eventTest | 02/12/1996 1235 | ONCE",event.writeTxt(), "The writeToFile format is not expected");

        // Mark the event as done and check its toString() and writeTxt()
        event.markAsDone();
        assertTrue(event.isDone(), "The event should be marked as done");
        assertEquals("[E][\u2713] eventTest (at: 2nd of December 1996, 12PM)", event.toString(), "The event.toString() is not expected");
        assertEquals( "E | 1 | eventTest | 02/12/1996 1235 | ONCE",event.writeTxt(), "The writeToFile format is not expected");
    }
}