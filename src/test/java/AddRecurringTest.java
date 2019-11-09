import java.lang.reflect.Field;
import java.time.LocalDateTime;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import chronologer.command.AddRecurringCommand;
import chronologer.command.Command;
import chronologer.exception.ChronologerException;
import chronologer.parser.ParserFactory;

/**
 * Test class for adding of different school tasks.
 * 
 * @author Hans kurnia
 * @version 1.0
 */
public class AddRecurringTest {

    LocalDateTime startDate = LocalDateTime.of(2019, 11, 17, 21, 30);
    LocalDateTime endDate = LocalDateTime.of(2019, 11, 17, 22, 0);
    String modCode = "CS2113";

    AddRecurringCommand lecture = new AddRecurringCommand("lecture", "lecture", startDate, endDate, modCode);
    AddRecurringCommand tutorial = new AddRecurringCommand("tutorial", "tutorial", startDate, endDate, modCode);

    private Field[] getAddRecurringCommandFields(Command command) throws NoSuchFieldException {
        Field[] commandFields = new Field[5];
        commandFields[0] = command.getClass().getSuperclass().getDeclaredField("command");
        commandFields[1] = command.getClass().getSuperclass().getDeclaredField("taskDescription");
        commandFields[2] = command.getClass().getSuperclass().getDeclaredField("formattedStartDate");
        commandFields[3] = command.getClass().getSuperclass().getDeclaredField("formattedEndDate");
        commandFields[4] = command.getClass().getSuperclass().getDeclaredField("modCode");
        commandFields[0].setAccessible(true);
        commandFields[1].setAccessible(true);
        commandFields[2].setAccessible(true);
        commandFields[3].setAccessible(true);
        commandFields[4].setAccessible(true);
        return commandFields;
    }

    private void assertEqualsAddRecurringCommand(Field[] test, Field[] expected, Command testCommand,
            Command expectedCommand) throws IllegalAccessException {
        Assertions.assertEquals(test[0].get(testCommand), expected[0].get(expectedCommand));
        Assertions.assertEquals(test[1].get(testCommand), expected[1].get(expectedCommand));
        Assertions.assertEquals(test[2].get(testCommand), expected[2].get(expectedCommand));
        Assertions.assertEquals(test[3].get(testCommand), expected[3].get(expectedCommand));
        Assertions.assertEquals(test[4].get(testCommand), expected[4].get(expectedCommand));
    }

    @Test
    @DisplayName("Testing parsing of lectures")
    public void testAddLectures() throws ChronologerException, NoSuchFieldException, IllegalAccessException {
        Field[] lectureFields = getAddRecurringCommandFields(lecture);
        Command lectureTest = ParserFactory.parse("lecture /m cs2113 /at sundays 2130-2200");
        Field[] lectureTestFields = getAddRecurringCommandFields(lectureTest);
        assertEqualsAddRecurringCommand(lectureFields, lectureTestFields, lecture, lectureTest);
    }

    @Test
    @DisplayName("Testing parsing of tutorials")
    public void testAddTutorials() throws ChronologerException, NoSuchFieldException, IllegalAccessException {
        Field[] tutorialFields = getAddRecurringCommandFields(tutorial);
        Command tutorialTest = ParserFactory.parse("tutorial /m cs2113 /at sundays 2130-2200");
        Field[] tutorialTestFields = getAddRecurringCommandFields(tutorialTest);
        assertEqualsAddRecurringCommand(tutorialFields, tutorialTestFields, tutorial, tutorialTest);
    }
}