import chronologer.exception.ChronologerException;
import chronologer.parser.ParserFactory;
import chronologer.task.Event;
import chronologer.task.Priority;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;


/**
 * Basic unit test for Event tasks.
 *
 * @author Tan Yi Xiang
 * @version 1.0
 */
class EventTest {

    private static final String EVENT = "EVENT";
    private LocalDateTime fromDate = LocalDateTime.of(2001, 1, 1, 1, 0);
    private LocalDateTime toDate = LocalDateTime.of(2001, 2, 2, 1, 0);
    private Event event = new Event("Test event", fromDate, toDate);

    @Test
    void testEventCreation() {
        String title = event.getDescription();
        LocalDateTime startDate = event.getStartDate();
        LocalDateTime endDate = event.getEndDate();
        Assertions.assertEquals(title, "Test event");
        Assertions.assertEquals(startDate, fromDate);
        Assertions.assertEquals(EVENT, event.getType());
        Assertions.assertEquals(endDate, toDate);
        Assertions.assertEquals(event.toString(),
            "[\u2605\u2605][E][\u2718] Test event(at: 01/01/2001 0100-02/02/2001 0100)");//Test
    }

    @Test
    void testEventEditing() {
        event.setIgnored(true);
        Assertions.assertTrue(event.isIgnored());
        event.setIgnored(false);
        Assertions.assertFalse(event.isIgnored());

        event.setDone(true);
        Assertions.assertTrue(event.isDone());
        event.setDone(false);
        Assertions.assertFalse(event.isDone());

        Priority priority = Priority.getPriorityLevel("HIGH");
        event.setPriority(priority);
        Assertions.assertEquals(event.toString(),
            "[\u2605\u2605\u2605][E][\u2718] Test event(at: 01/01/2001 0100-02/02/2001 0100)");//Test

        String comment = "This is a test";
        event.setComment(comment);
        Assertions.assertEquals(comment, event.getComment());
    }

    @Test
    void testEventPostponement() {
        LocalDateTime newFromDate = LocalDateTime.of(2003, 1, 1, 1, 0);
        LocalDateTime newEndDate = LocalDateTime.of(2003, 2, 2, 2, 0);

        event.setStartDate(newFromDate);
        event.setEndDate(newEndDate);
        Assertions.assertEquals(event.toString(),
            "[\u2605\u2605][E][\u2718] Test event(at: 01/01/2003 0100-02/02/2003 0200)");//Test
    }

    @Test
    void whenExceptionThrown() {
        Assertions.assertThrows(ChronologerException.class, () -> {
            ParserFactory.parse("event");
        });
    }

}
