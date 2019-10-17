import CustomExceptions.RoomShareException;
import Enums.Priority;
import Model_Classes.Task;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Task_Test {
    private static Task task;

    static {
        task = new Task("test", "user");
    }

    @Test
    public void testGetUser() { assertEquals("user", new Task("test", "user").getUser()); }

    @Test
    public void testGetCreated() {
        DateTimeFormatter dateTimeFormatterNow = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");
        LocalDateTime now = LocalDateTime.now();
        assertEquals(now.format(dateTimeFormatterNow), new Task("test", "user").getCreated());
    }

    @Test
    public void testGetDone() {
        assertEquals(false, new Task("test", "user").getDone());
    }

    @Test
    public void testGetStatusIcon() {
        assertEquals("[\u2718] ", new Task("test", "user").getStatusIcon());
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
        task.setDone();
        assertEquals(true, task.getDone());
    }

    @Test
    public void testSetPriority() {
        task.setPriority(Priority.high);
        assertEquals(Priority.high, task.getPriority());
    }

    @Test
    public void testToString() {
        assertEquals("[\u2718] test (user)", new Task("test", "user").toString());
    }
}