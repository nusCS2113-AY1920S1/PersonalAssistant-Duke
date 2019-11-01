package seedu.duke;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;
import seedu.duke.task.command.TaskParseNaturalDateHelper;
import seedu.duke.task.entity.Task;

import java.time.LocalDateTime;

public class NaturalDateTest {

    @Test
    public void getDateTest() throws Exception {
        LocalDateTime dateTime = TaskParseNaturalDateHelper.convertNaturalDate("Mon", "1220");
        assertEquals(dateTime, TaskParseNaturalDateHelper.getDate("Mon 1220"));
        assertEquals(dateTime, TaskParseNaturalDateHelper.getDate("mon 1220"));
        assertEquals(dateTime, TaskParseNaturalDateHelper.getDate("Mon     1220"));
        dateTime = TaskParseNaturalDateHelper.convertNaturalDate("Thu", null);
        assertEquals(dateTime, TaskParseNaturalDateHelper.getDate("Thu"));
        assertEquals(dateTime, TaskParseNaturalDateHelper.getDate("Thu   "));
        assertEquals(dateTime, TaskParseNaturalDateHelper.getDate("thu"));
        LocalDateTime time = Task.parseDate("21/10/2019 1220");
        assertEquals(time, TaskParseNaturalDateHelper.getDate("21/10/2019 1220"));
    }
}
