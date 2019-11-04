import java.text.ParseException;
import java.time.LocalDateTime;
import java.time.format.DateTimeParseException;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import chronologer.exception.ChronologerException;
import chronologer.parser.DateTimeExtractor;
import chronologer.parser.ParserFactory;
import chronologer.task.Todo;

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
        String title = todo.getDescription();
        Assertions.assertEquals(title, "testing todo");
        Assertions.assertEquals(todo.toString(), "[\u2605\u2605][T][\u2718] testing todo"); //Test
    }

    @Test
    public void whenExceptionThrown() {
        Assertions.assertThrows(ChronologerException.class, () -> {
            ParserFactory.parse("todo");
        });
    }

    @Test
    public void testTodoWithinPeriod() throws ChronologerException {
        try {
            LocalDateTime from;
            LocalDateTime to;
            from = DateTimeExtractor.extractDateTime("01/01/2019 0800", "todo");
            to = DateTimeExtractor.extractDateTime("01/01/2019 2200", "todo");
            Todo newTodo = new Todo("testTodo", from, to);
            Assertions.assertEquals(newTodo.getStartDate(), from);
        } catch (DateTimeParseException e) {
            throw new ChronologerException(ChronologerException.wrongDateOrTime());
        }
    }
}