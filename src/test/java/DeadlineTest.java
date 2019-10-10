import exception.DukeException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import parser.Parser;
import task.Deadline;

import java.time.LocalDateTime;

public class DeadlineTest {
    LocalDateTime fromDate = LocalDateTime.of(2001,1,1,1,0);
    Deadline deadline = new Deadline("testing deadline", fromDate);

    @Test
    public void testDeadlineCreation() {
        String title = deadline.description;
        LocalDateTime date = deadline.startDate;
        Assertions.assertEquals(title, "testing deadline");
        Assertions.assertEquals(date, fromDate);
        Assertions.assertEquals(deadline.toString(), "[\u2605\u2605][D][\u2718] testing deadline(by: 01/01/2001 0100)");
    }

    @Test
    public void whenExceptionThrown() {
        Assertions.assertThrows(DukeException.class, () -> {
            Parser.parse("deadline");
        });
    }

}
