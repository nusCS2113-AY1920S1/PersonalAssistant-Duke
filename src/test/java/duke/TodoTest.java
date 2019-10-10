package duke;

import org.junit.jupiter.api.Test;
import duke.task.Task;
import duke.task.Todo;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class TodoTest {

    @Test
    public void shouldTestTodoToSaveString() {
        // assert statements
        assertEquals("T | - | borrow book", new Todo("borrow book").toSaveString());
    }

    @Test
    public void shouldTestTodoToString() {
        // assert statements
        assertEquals("[T][-] borrow book", new Todo("borrow book").toString());
    }

    @Test
    public void shouldTestTodoGetStatusIcon() {
        Task todo = new Todo("borrow book");
        todo.markAsDone();
        // assert statements
        assertEquals("+", todo.getStatusIcon());
    }
}
