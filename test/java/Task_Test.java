package java;

import Enums.Priority;
import CustomExceptions.DukeException;
import Model_Classes.Deadline;
import Operations.Parser;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;
import java.util.Date;

public class TaskTest {
    private static final Parser parser = new Parser();

    @Test
    public void testStringConversion() {
        assertEquals("user", new Task("test", "user").getUser());
        assertEquals(LocalDateTime.now().format(dateTimeFormatterNow), new Task("test", "user").getCreated();
        assertEquals(false, new Task("test", "user").getDone());
        assertEquals("[✘] ", new Task("test", "user").getStatusIcon());
        assertEquals("test", new Task("test", "user").getDescription());
        assertEquals(Priority.low, new Task("test", "user").getPriority());
        assertEquals(true, new Task("test", "user").setDone().getDone());
        assertEquals(Priority.high, new Task("test", "user").setPriority(Priority.high).getPriority());
        assertEquals("[✘] test (user)", new Task("test", "user").toString());
    }
}