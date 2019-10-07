package owlmoney.model.task;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import org.junit.jupiter.api.Test;

public class RecurringTaskTest {

    private static final DateTimeFormatter inputFormatter = DateTimeFormatter.ofPattern("d/M/yyyy HHmm");
    private static final DateTimeFormatter displayFormatter = DateTimeFormatter.ofPattern("EEEE dd MMMM yyyy hh:mm a");

    /**
     * Tests for the normal creation of a recurring task.
     * @throws DukeException when unable to create recurring task.
     */
    @Test
    public void createRecurringTask_normalCreation_noError() throws DukeException {
        RecurringTask testRecurringTask =
                RecurringTask.create("new test recurring task /at 20/9/2019 1205");
        assertEquals(testRecurringTask.toString(),
                "[R][✘] new test recurring task (at: Friday 20 September 2019 12:05 PM)");
    }

    /**
     * Tests for the normal creation of a recurring task with old dates and expecting an auto update.
     * @throws DukeException when unable to create recurring task.
     */
    @Test
    public void createRecurringTask_oldDate_updateDate() throws DukeException {
        LocalDateTime currentDate = LocalDateTime.now();
        LocalDateTime oldDate = currentDate.minusDays(1);
        String oldDateString = oldDate.format(inputFormatter).toString();
        LocalDateTime expectedDate = oldDate.plusDays(7);
        String expectedDateString = expectedDate.format(displayFormatter);
        RecurringTask testRecurringTask =
                RecurringTask.create("test old recurring task date /at " + oldDateString);
        assertEquals(testRecurringTask.toString(),
                "[R][✘] test old recurring task date (at: " + expectedDateString + ")");
    }

    /**
     * Tests for the DukeException when a description is not entered even with a correct date.
     */
    @Test void createRecurringTask_regexCheck_throwError() {
        String wrongDate = " /at 1/1/2019 1200";
        DukeException err = assertThrows(DukeException.class, () -> RecurringTask.create(wrongDate));
        assertEquals("The description of a recurring task cannot be "
                + "empty or space even when /at is correct", err.getMessage());
    }

}
