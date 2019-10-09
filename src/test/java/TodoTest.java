import java.text.ParseException;
import java.time.LocalDateTime;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import exception.DukeException;
import parser.DateTimeExtractor;
import parser.Parser;
import task.Todo;
import task.TodoWithDuration;

/**
 * This class implements the unit testing code for the To-do class.
 *
 * @author Sai Ganesh Suresh
 * @version v1.0
 */
public class TodoTest {

    Todo todo = new Todo("testing todo");

    @Test
    public void testTodoCreation() {
        String title = todo.description;
        Assertions.assertEquals(title, "testing todo");
        Assertions.assertEquals(todo.toString(), "[T][✘] testing todo");
    }

    @Test
    public void whenExceptionThrown() {
        Assertions.assertThrows(DukeException.class, () -> {
            Parser.parse("todo");
        });
    }

    @Test
    public void testTodoWithinPeriod() throws DukeException {
        try {
            LocalDateTime from;
            LocalDateTime to;
            from = DateTimeExtractor.extractDateTime("01/01/2019 0800", "todo");
            to = DateTimeExtractor.extractDateTime("01/01/2019 2200", "todo");
            Todo newTodo = new TodoWithDuration("testTodo", from, to);
            Assertions.assertEquals(newTodo.startDate, from);
        } catch (ParseException e) {
            throw new DukeException(DukeException.wrongDateOrTime());
        }
    }
}