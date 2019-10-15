import exception.DukeException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import parser.ParserFactory;
import task.Deadline;
import task.Task;

import java.time.LocalDateTime;
import java.util.ArrayList;

public class DeadlineTest {
    LocalDateTime fromDate = LocalDateTime.of(2001, 1, 1, 1, 0);
    Deadline deadline = new Deadline("test", fromDate);

    @Test
    public void testDeadlineCreation() {
        String title = deadline.description;
        LocalDateTime date = deadline.startDate;
        Assertions.assertEquals(title, "test");
        Assertions.assertEquals(date, fromDate);
        Assertions.assertEquals(deadline.toString(), "[\u2605\u2605][D][\u2718] test(by: 01/01/2001 0100)");//Test
    }

    @Test
    public void whenExceptionThrown() {
        Assertions.assertThrows(DukeException.class, () -> {
            ParserFactory.parse("deadline");
        });
    }

    @Test
    public void testDeadlinePostponement() {
        LocalDateTime newFromDate = LocalDateTime.of(2003, 1, 1, 1, 0);
        deadline.setStartDate(newFromDate);
        Assertions.assertEquals(deadline.startDate, newFromDate);
    }

}
