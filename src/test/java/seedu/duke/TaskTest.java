package seedu.duke;

import org.junit.jupiter.api.Test;
import seedu.duke.common.parser.CommandParseHelper;
import seedu.duke.task.entity.Task;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import static org.junit.jupiter.api.Assertions.*;
import static seedu.duke.task.entity.Task.getPriorityLevel;
import static seedu.duke.task.entity.Task.parseDate;

public class TaskTest {

    @Test
    public void parseDateTest() {
        //positive cases
        try {
            assertEquals(LocalDateTime.parse("31/12/2019 2359", DateTimeFormatter.ofPattern("dd/MM/uuuu HHmm")),
                    parseDate("31/12/2019 2359"));
            assertEquals(LocalDateTime.parse("29/02/2020 1200", DateTimeFormatter.ofPattern("dd/MM/uuuu HHmm")),
                    parseDate("29/02/2020 1200"));
        } catch (CommandParseHelper.CommandParseException e){
            fail("Date Time cannot be parsed exception");
        }

        //negative cases
        Throwable exception = assertThrows(CommandParseHelper.CommandParseException.class,
                () -> parseDate("1/01/2019 1000"));
        assertEquals("Wrong Date Time format", exception.getMessage());
        exception = assertThrows(CommandParseHelper.CommandParseException.class,
                () -> parseDate("01/21/2019 1000"));
        assertEquals("Wrong Date Time format", exception.getMessage());
        exception = assertThrows(CommandParseHelper.CommandParseException.class,
                () -> parseDate("abc"));
        assertEquals("Wrong Date Time format", exception.getMessage());
        exception = assertThrows(CommandParseHelper.CommandParseException.class,
                () -> parseDate("01.01.2099 1200"));
        assertEquals("Wrong Date Time format", exception.getMessage());
        exception = assertThrows(CommandParseHelper.CommandParseException.class,
                () -> parseDate("01.01.2099 1261"));
        assertEquals("Wrong Date Time format", exception.getMessage());
    }

    @Test
    public void getPriorityLevelTest() {
        assertEquals(Task.Priority.HIGH, getPriorityLevel("HIGH"));
        assertEquals(Task.Priority.MEDIUM, getPriorityLevel("MEDIUM"));
        assertEquals(Task.Priority.MEDIUM, getPriorityLevel("MED"));
        assertEquals(Task.Priority.LOW, getPriorityLevel("LOW"));
        assertEquals(Task.Priority.NULL, getPriorityLevel("high"));
        assertEquals(Task.Priority.NULL, getPriorityLevel("abc"));
    }
}
