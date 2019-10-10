import duke.DateTime;
import duke.items.tasks.Deadline;
import duke.items.tasks.Event;
import duke.items.tasks.Task;

import java.util.Date;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertFalse;

public class ViewScheduleTest {

    @org.junit.jupiter.api.Test
    void testDateCheckDeadline() {
        Date inputDate = new Date(2019, 9,20);
        Task task = new Deadline("Test", inputDate);
        assertTrue(task.compareEquals(new DateTime(inputDate)));
        Date inputDateTwo = new Date(2019, 9,21);
        assertFalse(task.compareEquals(new DateTime(inputDateTwo)));
    }

    @org.junit.jupiter.api.Test
    void testDateCheckEvent() {
        Date inputDate = new Date(2019, 9,20);
        Date startDate = new Date(2019, 9,19);
        Date endDate = new Date(2019, 9,21);

        Task task = new Event("Test", startDate, endDate);
        assertTrue(task.compareEquals(new DateTime(inputDate)));
        assertTrue(task.compareEquals(new DateTime(startDate)));
        assertTrue(task.compareEquals(new DateTime(endDate)));
        Date inputDateTwo = new Date(2019, 9,23);
        assertFalse(task.compareEquals(new DateTime(inputDateTwo)));
    }
}
