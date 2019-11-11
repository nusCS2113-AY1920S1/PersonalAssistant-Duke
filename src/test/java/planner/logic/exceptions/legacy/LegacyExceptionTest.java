//@@author namiwa

package planner.logic.exceptions.legacy;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class LegacyExceptionTest {

    private final String test = "test";
    private ModTestException modTestException = new ModTestException();
    private ModException modException = new ModException();
    private ModException modExWithMsg = new ModException(test);
    private ModException modExWithMsgAndCause = new ModException(test, modTestException);
    private ModCommandException modCommandException = new ModCommandException();
    private ModEmptyCommandException modEmptyCommandException = new ModEmptyCommandException();
    private ModEmptyListException modEmptyListException = new ModEmptyListException();
    private ModInvalidIndexException modInvalidIndexException = new ModInvalidIndexException();
    private ModInvalidTimeException modInvalidTimeException = new ModInvalidTimeException();
    private ModInvalidTimePeriodException modInvalidTimePeriodException = new ModInvalidTimePeriodException();
    private ModMissingArgumentException modMissingArgumentException = new ModMissingArgumentException(test);
    private ModNoTimeException modNoTimeException = new ModNoTimeException();
    private ModOutOfBoundException modOutOfBoundException = new ModOutOfBoundException();
    private ModScheduleException modScheduleException = new ModScheduleException();
    private ModTimeIntervalTooCloseException modTimeIntervalTooCloseException = new ModTimeIntervalTooCloseException();
    private ModMultipleValuesForSameArgumentException modMultipleValuesForSameArgumentException =
            new ModMultipleValuesForSameArgumentException();

    @Test
    void testModException() {
        assertEquals("Error: ", modException.getMessage());
        assertEquals("Error: test", modExWithMsg.getMessage());
        assertNotNull(modExWithMsgAndCause);
    }

    @Test
    void testModTestException() {
        assertNotNull(modTestException);
        assertEquals("Error: This is a test Exception!", modTestException.getMessage());
    }

    @Test
    void testModCommandException() {
        assertEquals("Error: Must be a valid command!", modCommandException.getMessage());
    }

    @Test
    void testModEmptyCommandException() {
        assertEquals("Error: Command cannot be empty!", modEmptyCommandException.getMessage());
    }

    @Test
    void testModEmptyListException() {
        assertEquals("Error: There are no tasks in the list!", modEmptyListException.getMessage());
    }

    @Test
    void testModInvalidIndexException() {
        assertEquals("Error: Invalid Index!", modInvalidIndexException.getMessage());
    }

    @Test
    void testModInvalidTimeException() {
        assertEquals("Error: Invalid time and date format!", modInvalidTimeException.getMessage());
    }

    @Test
    void testModInvalidTimePeriodException() {
        assertNotNull(modInvalidTimePeriodException);
    }

    @Test
    void testModMissingArgumentException() {
        assertNotNull(modMissingArgumentException);
    }

    @Test
    void testModMultipleValues() {
        assertEquals("Error: Cannot set multiple values for the same argument!",
                modMultipleValuesForSameArgumentException.getMessage());
    }

    @Test
    void testModNotTimeException() {
        assertEquals("Error: Cannot set time for this task!", modNoTimeException.getMessage());
    }

    @Test
    void testModOutOfBoundsException() {
        assertEquals("Error: Index out of bound, try something else!", modOutOfBoundException.getMessage());
    }

    @Test
    void testModScheduleException() {
        assertEquals("Error: Module clashes with existing tasks!", modScheduleException.getMessage());
    }

    @Test
    void testModTimeIntervalTooCloseException() {
        assertEquals("Error: That time interval is too close!",
                modTimeIntervalTooCloseException.getMessage());
    }
}

