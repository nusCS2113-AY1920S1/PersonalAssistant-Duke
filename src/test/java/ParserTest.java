import command.AddCommand;
import command.Command;
import exception.DukeException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import parser.Parser;

import java.lang.reflect.Field;
import java.time.LocalDateTime;
import java.time.format.DateTimeParseException;

import static parser.DateTimeExtractor.NULL_DATE;

public class ParserTest {

    LocalDateTime fromDate = LocalDateTime.of(2001,1,1,1,0);
    AddCommand deadlineTest = new AddCommand("deadline", "test", fromDate, NULL_DATE, NULL_DATE);

    Command toDoTest = new AddCommand("todo", "test", NULL_DATE, NULL_DATE, NULL_DATE);


    @Test
    public void testToDoParsing() throws DukeException, NoSuchFieldException, IllegalAccessException {
        Field[] toDoTestFields = new Field[2];
        toDoTestFields[0] = toDoTest.getClass().getDeclaredField("command");
        toDoTestFields[1] = toDoTest.getClass().getDeclaredField("taskFeatures");
        toDoTestFields[0].setAccessible(true);
        toDoTestFields[1].setAccessible(true);

        Command todo = Parser.parse("todo test");
        Field[] toDoFields = new Field[2];
        toDoFields[0] = todo.getClass().getDeclaredField("command");
        toDoFields[1] = todo.getClass().getDeclaredField("taskFeatures");
        toDoFields[0].setAccessible(true);
        toDoFields[1].setAccessible(true);
        Assertions.assertEquals(toDoFields[0].get(todo), toDoTestFields[0].get(toDoTest));
        Assertions.assertEquals(toDoFields[1].get(todo), toDoTestFields[1].get(toDoTest));
    }

    @Test
    public void testExceptionForDeadline() {
        Assertions.assertThrows(DukeException.class, () -> {
            Parser.parse("deadline");
        });
        Assertions.assertThrows(DukeException.class, () -> {
            Parser.parse("deadline test");
        });
        Assertions.assertThrows(DateTimeParseException.class, () -> {
            Parser.parse("deadline test /by");
        });
        Assertions.assertThrows(DukeException.class, () -> {
            Parser.parse("deadline /by 01/01/2001 0100");
        });
        Assertions.assertThrows(DukeException.class, () -> {
            Parser.parse("deadline test 01/01/2001 0100");
        });
    }
}
