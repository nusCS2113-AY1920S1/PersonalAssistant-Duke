import spinbox.DateTime;
import spinbox.items.tasks.Deadline;
import spinbox.items.tasks.Event;
import spinbox.items.tasks.Task;

import java.util.Date;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertFalse;

public class ViewScheduleTest {

    @org.junit.jupiter.api.Test
    void testDateCheckDeadline() {
        DateTime inputDate = new DateTime(new Date(2019, 9,20));
        Task task = new Deadline("Test", inputDate);
        assertTrue(task.compareEquals(inputDate));
        DateTime inputDateTwo = new DateTime(new Date(2019, 9,21));
        assertFalse(task.compareEquals(inputDateTwo));
    }

    @org.junit.jupiter.api.Test
    void testDateCheckEvent() {
        DateTime inputDate = new DateTime(new Date(2019, 9,20));
        DateTime startDate = new DateTime(new Date(2019, 9,19));
        DateTime endDate = new DateTime(new Date(2019, 9,21));

        Task task = new Event("Test", startDate, endDate);
        assertTrue(task.compareEquals(inputDate));
        assertTrue(task.compareEquals(startDate));
        assertTrue(task.compareEquals(endDate));
        DateTime inputDateTwo = new DateTime(new Date(2019, 9,23));
        assertFalse(task.compareEquals(inputDateTwo));
    }
}
