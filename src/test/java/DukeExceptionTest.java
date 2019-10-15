import duke.exceptions.DukeException;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * Represents a test for DukeException class.
 */
public class DukeExceptionTest {
    @Test
    public void dukeExceptionTest() {
        System.out.println("Start dukeExceptionTest");

        String outcome = test();
        assertEquals("Something went wrong.",outcome);

        System.out.println("Passed dukeExceptionTest");
    }

    public String test() {
        String str = "Failed dukeExceptionTest";
        try {
            if (1 != 0) {
                throw new DukeException("Something went wrong.");
            }
        } catch (DukeException e) {
            str = e.getMessage();
        }
        return str;
    }
}
