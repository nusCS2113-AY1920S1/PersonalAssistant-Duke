package duke.task;

import duke.core.DukeException;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;


public class DeadlineTest {

    /**
     * Test the Deadline.toString()
     */
    @Test
    public void deadlineStringTest() throws DukeException {
        assertEquals( "[D][\u2718] abc (by: 2nd of December 1996, 12PM)", new Deadline("abc", "02/12/1996 1235").toString(),"Deadline toString() is not expected");
    }

    /**
     * Test the Deadline.writeTxt()
     */
    @Test
    public void writeFormatTest() throws DukeException {
        assertEquals( "D | 0 | deadlineTest | 02/12/1996 1235 | ONCE",new Deadline("deadlineTest", "02/12/1996 1235").writeTxt(), "The writeToFile format is not expected");
    }

    /**
     * Test the Deadline.isDone() after a new Deadline object is being initialized
     */
    @Test
    public void doneStatusTest() throws DukeException {
        assertFalse(new Deadline("abc", "02/12/1996 1235").isDone(), "The newly created Deadline should not be done");
    }

    /**
     * A general test case to test Deadline class
     * Test steps:
     * 1. Create a Deadline object
     * 2. Verify Deadline.isdone(), Deadline.toString(), Deadline.writeTxt()
     * 3. Mark the Deadline object to isDone status.
     * 4. Repeat step 2
     *
     * @throws DukeException if markAsDone is applied to a done task, throw exception with log
     */
    @Test
    public void deadlineTestCase() throws DukeException {
        // Create a new deadline and check its toString() and writeTxt()
        Deadline deadline = new Deadline("deadlineTest", "02/12/1996 1235");
        assertFalse(deadline.isDone(), "The newly created deadline should not be done");
        assertEquals( "[D][\u2718] deadlineTest (by: 2nd of December 1996, 12PM)",deadline.toString(), "The writeToFile format is not expected");
        assertEquals( "D | 0 | deadlineTest | 02/12/1996 1235 | ONCE",deadline.writeTxt(), "The writeToFile format is not expected");

        // Mark the deadline as done and check its toString() and writeTxt()
        deadline.markAsDone();
        assertTrue(deadline.isDone(), "The deadline should be marked as done");
        assertEquals("[D][\u2713] deadlineTest (by: 2nd of December 1996, 12PM)", deadline.toString(), "The deadline.toString() is not expected");
        assertEquals( "D | 1 | deadlineTest | 02/12/1996 1235 | ONCE",deadline.writeTxt(), "The writeToFile format is not expected");
    }
}
