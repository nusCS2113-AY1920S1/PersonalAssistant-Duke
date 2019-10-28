import chronologer.exception.ChronologerException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import chronologer.parser.ParserFactory;
import chronologer.task.Deadline;

import java.time.LocalDateTime;

public class DeadlineTest {
    private LocalDateTime fromDate = LocalDateTime.of(2001, 1, 1, 1, 0);
    private Deadline deadline = new Deadline("test", fromDate);

    @Test
    public void testDeadlineCreation() {
        String title = deadline.getDescription();
        LocalDateTime date = deadline.getStartDate();
        Assertions.assertEquals(title, "test");
        Assertions.assertEquals(date, fromDate);
        Assertions.assertEquals(deadline.toString(), "[\u2605\u2605][D][\u2718] test (by: 01/01/2001 0100)");//Test
    }

    @Test
    public void whenExceptionThrown() {
        Assertions.assertThrows(ChronologerException.class, () -> {
            ParserFactory.parse("deadline");
        });
    }

    @Test
    public void testDeadlinePostponement() {
        LocalDateTime newFromDate = LocalDateTime.of(2003, 1, 1, 1, 0);
        deadline.setStartDate(newFromDate);
        Assertions.assertEquals(deadline.getStartDate(), newFromDate);
    }

}
