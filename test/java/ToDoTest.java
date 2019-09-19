package java;

import Model_Classes.ToDo;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;


public class ToDoTest {
    @Test
    public void testStringConversion() {
        assertEquals("[T][\u2718] homework", new ToDo("homework").toString());
    }
}