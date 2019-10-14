package seedu.hustler.data;

import org.junit.jupiter.api.Test;
import seedu.hustler.task.Deadline;
import seedu.hustler.task.Task;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;
import static seedu.hustler.parser.DateTimeParser.getDateTime;

import java.text.ParseException;
import java.time.LocalDateTime;
import java.util.Date;

public class ScheduleTest {

    @Test
    public void taskInScheduleTest() throws ParseException {

        LocalDateTime by = getDateTime("10/10/2019 1234");

        Schedule schedule = new Schedule();
        String description = "test description";
        String dateBy = "10/10/2019 1234";
        String dateOnly = "10/10/2019";
        Date date = schedule.convertStringToDate(dateOnly);
        Deadline deadline = new Deadline(description, by);
        schedule.addToSchedule(deadline, date);
        Task toCheckTask = schedule.getDatedList(date).get(0);
        assertEquals(deadline.toString(), toCheckTask.toString());
    }
}
