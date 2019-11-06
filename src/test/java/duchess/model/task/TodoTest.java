package duchess.model.task;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class TodoTest {
    private final Todo todo = new Todo("something");

    @Test
    public void constructor() {
        assertEquals("something", todo.getDescription());
    }

    @Test
    public void toString_formatsCorrectly() {
        assertEquals(todo.toString(),
                "[T][X] something");
    }
}
