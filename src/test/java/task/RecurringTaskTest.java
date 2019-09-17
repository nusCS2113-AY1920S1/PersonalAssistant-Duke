package task;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.concurrent.TimeUnit;

import org.junit.jupiter.api.Test;

import duke.exception.DukeException;
import duke.task.RecurringTask;

public class RecurringTaskTest {

    private static final DateTimeFormatter inputFormatter = DateTimeFormatter.ofPattern("d/M/yyyy HHmm");
    private static final DateTimeFormatter displayFormatter = DateTimeFormatter.ofPattern("EEEE dd MMMM yyyy hh:mm a");

    @Test
    public void createRecurringTask_normalCreation_noError() throws DukeException {
        RecurringTask testRecurringTask =
                RecurringTask.create("new test recurring task /at 20/9/2019 1205");
        assertEquals(testRecurringTask.toString()
                ,"[R][✘] new test recurring task (at: Friday 20 September 2019 12:05 PM)");
    }

    @Test
    public void createRecurringTask_oldDate_updateDate() throws DukeException {
        LocalDateTime currentDate = LocalDateTime.now();
        LocalDateTime oldDate = currentDate.minusDays(1);
        String oldDateString = oldDate.format(inputFormatter).toString();
        LocalDateTime expectedDate = oldDate.plusDays(7);
        String expectedDateString = expectedDate.format(displayFormatter);
        RecurringTask testRecurringTask =
                RecurringTask.create("test old recurring task date /at "+oldDateString);
        assertEquals(testRecurringTask.toString()
                ,"[R][✘] test old recurring task date (at: "+expectedDateString+")");
    }

    @Test void createRecurringTask_regexCheck_throwError() throws DukeException {
        DukeException err = assertThrows(DukeException.class
                , () -> RecurringTask.create(" /at 1/1/2019 1200"));
        assertEquals("The description of a recurring task cannot be "
                + "empty or space even when /at is correct",err.getMessage());
    }

}
