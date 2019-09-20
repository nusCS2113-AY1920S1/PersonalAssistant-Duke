package java;

import CustomExceptions.DukeException;
import Model_Classes.Deadline;
import Operations.Parser;
import org.junit.jupiter.api.Test;
import java.util.Date;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class DeadlineTest {
    private static final Parser parser = new Parser();
    private static Date by;

    static {
        try {
            by = parser.formatDate("22/12/2019 18:00");
        } catch (DukeException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testStringConversion() {
        assertEquals("[D][\u2718] homework (by: Sun Dec 22 18:00:00 SGT 2019)", new Deadline("homework", by).toString());
    }
}