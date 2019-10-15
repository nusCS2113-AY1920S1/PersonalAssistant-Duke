import command.AddCommand;
import command.Command;
import exception.DukeException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import parser.ParserFactory;

import java.lang.reflect.Field;
import java.time.LocalDateTime;
import java.time.format.DateTimeParseException;

public class ParserFactoryTest {

    LocalDateTime startDate = LocalDateTime.of(2001,1,1,1,0);
    LocalDateTime endDate = LocalDateTime.of(2001,1,1,13,0);

    Command event = new AddCommand("event", "test", startDate, endDate);
    Command deadline = new AddCommand("deadline", "test", startDate, null);
    Command todo = new AddCommand("todo", "test", null, null);

    private Field[] getAddCommandFields(Command command) throws NoSuchFieldException {
        Field[] commandFields = new Field[4];
        commandFields[0] = command.getClass().getDeclaredField("command");
        commandFields[1] = command.getClass().getDeclaredField("taskFeatures");
        commandFields[2] = command.getClass().getDeclaredField("formattedStartDate");
        commandFields[3] = command.getClass().getDeclaredField("formattedEndDate");
        commandFields[0].setAccessible(true);
        commandFields[1].setAccessible(true);
        commandFields[2].setAccessible(true);
        commandFields[3].setAccessible(true);
        return commandFields;
    }

    private void assertEqualsAddCommand(Field[] test, Field[] expected, Command testCommand, Command expectedCommand)
            throws IllegalAccessException {
        Assertions.assertEquals(test[0].get(testCommand), expected[0].get(expectedCommand));
        Assertions.assertEquals(test[1].get(testCommand), expected[1].get(expectedCommand));
        Assertions.assertEquals(test[2].get(testCommand), expected[2].get(expectedCommand));
        Assertions.assertEquals(test[3].get(testCommand), expected[3].get(expectedCommand));
    }

    @Test
    public void testToDoParsing() throws DukeException, NoSuchFieldException, IllegalAccessException {
        Field[] todoFields = getAddCommandFields(todo);
        Command todoTest = ParserFactory.parse("todo test");
        Field[] toDoTestFields = getAddCommandFields(todoTest);
        assertEqualsAddCommand(toDoTestFields, todoFields, todoTest, todo);
    }

    @Test
    public void testDeadlineParsing() throws DukeException, NoSuchFieldException, IllegalAccessException {
        Field[] deadlineFields = getAddCommandFields(deadline);
        Command deadlineTest = ParserFactory.parse("deadline test /by 01/01/2001 0100");
        Field[] deadlineTestFields = getAddCommandFields(deadlineTest);
        assertEqualsAddCommand(deadlineTestFields, deadlineFields, deadlineTest, deadline);
    }

    @Test
    public void testEventParsing() throws DukeException, NoSuchFieldException, IllegalAccessException {
        Field[] eventFields = getAddCommandFields(event);
        Command eventTest = ParserFactory.parse("event test /at 01/01/2001 0100 - 01/01/2001 1300");
        Field[] eventTestFields = getAddCommandFields(eventTest);
        assertEqualsAddCommand(eventTestFields, eventFields, eventTest, event);
    }

    @Test
    public void testExceptionForDeadline() {
        Assertions.assertThrows(DukeException.class, () -> {
            ParserFactory.parse("deadline");
        });
        Assertions.assertThrows(DukeException.class, () -> {
            ParserFactory.parse("deadline test");
        });
        Assertions.assertThrows(DateTimeParseException.class, () -> {
            ParserFactory.parse("deadline test /by");
        });
        Assertions.assertThrows(DukeException.class, () -> {
            ParserFactory.parse("deadline /by 01/01/2001 0100");
        });
        Assertions.assertThrows(DukeException.class, () -> {
            ParserFactory.parse("deadline test 01/01/2001 0100");
        });
    }
}
