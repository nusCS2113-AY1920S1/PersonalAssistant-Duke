import org.junit.jupiter.api.Test;
import task.Todo;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class TodoTest {
    @Test
    void testTodo() {
        Todo todo = new Todo("eat");
        assertEquals(todo.toString(), "[T][✘] eat");
    }
}
