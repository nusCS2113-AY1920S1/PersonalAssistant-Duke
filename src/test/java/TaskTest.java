package java;

import Enums.Priority;
import CustomExceptions.DukeException;
import Model_Classes.Task;
import Operations.Parser;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;
import java.util.Date;

public class TaskTest {
    private static final Parser parser = new Parser();

    @Test
    public void testGetUser() {
        assertEquals("user", new Task("test", "user").getUser());
    }

    @Test
    public void testGetCreated() {
        assertEquals(LocalDateTime.now().format(dateTimeFormatterNow), new Task("test", "user").getCreated();
    }

    @Test
    public void testGetDone() {
        assertEquals(false, new Task("test", "user").getDone());
    }

    @Test
    public void testGetStatusIcon() {
        assertEquals("[✘] ", new Task("test", "user").getStatusIcon());
    }

    @Test
    public void testGetDescription() {
        assertEquals("test", new Task("test", "user").getDescription());
    }

    @Test
    public void testGetPriority() {
        assertEquals(Priority.low, new Task("test", "user").getPriority());
    }

    @Test
    public void testSetDone() {
        assertEquals(true, new Task("test", "user").setDone().getDone());
    }

    @Test
    public void testSetPriority() {
        assertEquals(Priority.high, new Task("test", "user").setPriority(Priority.high).getPriority());
    }

    @Test
    public void testToString() {
        assertEquals("[✘] test (user)", new Task("test", "user").toString());
    }
}