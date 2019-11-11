package chronologer.parser;

import chronologer.command.Command;
import chronologer.command.TaskScheduleCommand;
import chronologer.exception.ChronologerException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Field;
import java.time.LocalDateTime;

//@@author fauzt
/**
 * Tests for ScheduleParser when receiving schedule instructions.
 *
 * @author Fauzan Adipratama
 * @version 1.4
 */
public class ScheduleParserTest {

    LocalDateTime deadlineDate = LocalDateTime.of(2019,11,10,12,0);

    private Field[] getTaskScheduleCommandFields(Command command) throws NoSuchFieldException {
        Field[] commandFields = new Field[4];
        commandFields[0] = command.getClass().getDeclaredField("durationToSchedule");
        commandFields[1] = command.getClass().getDeclaredField("indexOfTask");
        commandFields[2] = command.getClass().getDeclaredField("indexOfDeadline");
        commandFields[3] = command.getClass().getDeclaredField("deadlineDate");
        commandFields[0].setAccessible(true);
        commandFields[1].setAccessible(true);
        commandFields[2].setAccessible(true);
        commandFields[3].setAccessible(true);
        return commandFields;
    }

    private void assertEqualsTaskScheduleCommand(Field[] test, Field[] expected, Command testCommand,
                                                 Command expectedCommand)
            throws IllegalAccessException {
        Assertions.assertEquals(test[0].get(testCommand), expected[0].get(expectedCommand));
        Assertions.assertEquals(test[1].get(testCommand), expected[1].get(expectedCommand));
        Assertions.assertEquals(test[2].get(testCommand), expected[2].get(expectedCommand));
        Assertions.assertEquals(test[3].get(testCommand), expected[3].get(expectedCommand));
    }

    @Test
    void testParseTodoIndexAndDeadlineIndex() throws ChronologerException, NoSuchFieldException,
            IllegalAccessException {
        ScheduleParser testParser = new ScheduleParser("schedule 2 /by 1", "schedule");
        Command testCommand = testParser.parse();
        Command expectedCommand = new TaskScheduleCommand(1, 0);

        Field[] scheduleTestFields = getTaskScheduleCommandFields(testCommand);
        Field[] scheduleFields = getTaskScheduleCommandFields(expectedCommand);
        Assertions.assertEquals(1, testParser.indexOfTask);
        assertEqualsTaskScheduleCommand(scheduleTestFields, scheduleFields, testCommand, expectedCommand);
    }

    @Test
    void testParseTodoIndexAndDeadlineDate() throws ChronologerException, NoSuchFieldException, IllegalAccessException {
        ScheduleParser testParser = new ScheduleParser("schedule 2 /by 10/11/2019 1200", "schedule");
        Command testCommand = testParser.parse();
        Command expectedCommand = new TaskScheduleCommand(1, deadlineDate);

        Field[] scheduleTestFields = getTaskScheduleCommandFields(testCommand);
        Field[] scheduleFields = getTaskScheduleCommandFields(expectedCommand);
        Assertions.assertEquals(1, testParser.indexOfTask);
        assertEqualsTaskScheduleCommand(scheduleTestFields, scheduleFields, testCommand, expectedCommand);
    }

    @Test
    void testParseTodoIndexAndNoDate() throws ChronologerException, NoSuchFieldException, IllegalAccessException {
        ScheduleParser testParser = new ScheduleParser("schedule 2", "schedule");
        Command testCommand = testParser.parse();
        Command expectedCommand = new TaskScheduleCommand(1, null);

        Field[] scheduleTestFields = getTaskScheduleCommandFields(testCommand);
        Field[] scheduleFields = getTaskScheduleCommandFields(expectedCommand);
        Assertions.assertEquals(1, testParser.indexOfTask);
        assertEqualsTaskScheduleCommand(scheduleTestFields, scheduleFields, testCommand, expectedCommand);
    }

    @Test
    void testParseDurationAndDeadlineIndex() throws ChronologerException, NoSuchFieldException, IllegalAccessException {
        ScheduleParser testParser = new ScheduleParser("schedule 2 -r /by 1", "schedule");
        Command testCommand = testParser.parse();
        Long expectedDuration = (long) 2;
        Command expectedCommand = new TaskScheduleCommand(expectedDuration, 0);

        Field[] scheduleTestFields = getTaskScheduleCommandFields(testCommand);
        Field[] scheduleFields = getTaskScheduleCommandFields(expectedCommand);
        assertEqualsTaskScheduleCommand(scheduleTestFields, scheduleFields, testCommand, expectedCommand);
    }

    @Test
    void testParseDurationAndDeadlineDate() throws ChronologerException, NoSuchFieldException, IllegalAccessException {
        ScheduleParser testParser = new ScheduleParser("schedule 2 -r /by 10/11/2019 1200", "schedule");
        Command testCommand = testParser.parse();
        Long expectedDuration = (long) 2;
        Command expectedCommand = new TaskScheduleCommand(expectedDuration, deadlineDate);

        Field[] scheduleTestFields = getTaskScheduleCommandFields(testCommand);
        Field[] scheduleFields = getTaskScheduleCommandFields(expectedCommand);
        assertEqualsTaskScheduleCommand(scheduleTestFields, scheduleFields, testCommand, expectedCommand);
    }

    @Test
    void testParseDurationAndNoDate() throws ChronologerException, NoSuchFieldException, IllegalAccessException {
        ScheduleParser testParser = new ScheduleParser("schedule 2 -r", "schedule");
        Command testCommand = testParser.parse();
        Long expectedDuration = (long) 2;
        Command expectedCommand = new TaskScheduleCommand(expectedDuration, null);

        Field[] scheduleTestFields = getTaskScheduleCommandFields(testCommand);
        Field[] scheduleFields = getTaskScheduleCommandFields(expectedCommand);
        assertEqualsTaskScheduleCommand(scheduleTestFields, scheduleFields, testCommand, expectedCommand);
    }

    @Test
    void testExceptionsForEmptyParameters() {
        Assertions.assertThrows(ChronologerException.class, () -> {
            new ScheduleParser("schedule ", "schedule").parse();
        });
        Assertions.assertThrows(ChronologerException.class, () -> {
            new ScheduleParser("schedule /by ", "schedule").parse();
        });
        Assertions.assertThrows(ChronologerException.class, () -> {
            new ScheduleParser("schedule 2 /by ", "schedule").parse();
        });
        Assertions.assertThrows(ChronologerException.class, () -> {
            new ScheduleParser("schedule /by 1", "schedule").parse();
        });
    }

    @Test
    void testExceptionsForInvalidParameters() {
        Assertions.assertThrows(ChronologerException.class, () -> {
            new ScheduleParser("schedule a /by 1 ", "schedule").parse();
        });
        Assertions.assertThrows(ChronologerException.class, () -> {
            new ScheduleParser("schedule 2 /by a", "schedule").parse();
        });
        Assertions.assertThrows(ChronologerException.class, () -> {
            new ScheduleParser("schedule 2 /by aa/aa/aaaa", "schedule").parse();
        });
    }

    @Test
    void testExceptionsForInvalidDates() {
        Assertions.assertThrows(ChronologerException.class, () -> {
            new ScheduleParser("schedule 2 /by 00/00/0000 0000", "schedule").parse();
        });
        Assertions.assertThrows(ChronologerException.class, () -> {
            new ScheduleParser("schedule 2 /by 12/10/2019", "schedule").parse();
        });
    }
}
