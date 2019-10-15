import Model_Classes.ToDo;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;


public class ToDoTest {
    private static ToDo toDo = new ToDo("homework");
    @Test
    public void testStringConversion() {
        assertEquals("[T][\u2718] homework (nul)", toDo.toString());
    }
}