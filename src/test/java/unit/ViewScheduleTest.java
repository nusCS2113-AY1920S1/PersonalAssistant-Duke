package unit;

import org.junit.jupiter.api.Test;
import spinbox.DateTime;
import spinbox.entities.items.tasks.Deadline;
import spinbox.entities.items.tasks.Event;
import spinbox.entities.items.tasks.Schedulable;
import spinbox.exceptions.DateFormatException;
import spinbox.exceptions.ScheduleDateException;

import java.util.Calendar;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.fail;

public class ViewScheduleTest {

    @Test
    void testDateCheckDeadline() {
        Calendar inputDateOne = Calendar.getInstance();
        inputDateOne.set(2029, 9, 28);

        DateTime inputDateTimeOne = new DateTime(inputDateOne.getTime());
        try {
            Schedulable task = new Deadline("Test", inputDateTimeOne);
            assertTrue(task.compareEquals(inputDateTimeOne));

            Calendar inputDateTwo = Calendar.getInstance();
            inputDateTwo.set(2029,9,21);

            DateTime inputDateTimeTwo = new DateTime(inputDateTwo.getTime());
            assertFalse(task.compareEquals(inputDateTimeTwo));
        } catch (ScheduleDateException | DateFormatException e) {
            fail(e.getMessage());
        }
    }

    @Test
    void testDateCheckEvent() {
        Calendar inputOne = Calendar.getInstance();
        inputOne.set(2029, 9, 20);

        Calendar inputTwo = Calendar.getInstance();
        inputTwo.set(2029, 9, 19);

        Calendar inputThree = Calendar.getInstance();
        inputThree.set(2029, 9,21);

        DateTime inputDate = new DateTime(inputOne.getTime());
        DateTime startDate = new DateTime(inputTwo.getTime());
        DateTime endDate = new DateTime(inputThree.getTime());

        try {
            Schedulable task = new Event("Test", startDate, endDate);
            assertTrue(task.compareEquals(inputDate));
            assertTrue(task.compareEquals(startDate));
            assertTrue(task.compareEquals(endDate));

            Calendar inputFour = Calendar.getInstance();
            inputFour.set(2029, 9, 23);
            DateTime inputDateTwo = new DateTime(inputFour.getTime());
            assertFalse(task.compareEquals(inputDateTwo));
        } catch (ScheduleDateException | DateFormatException e) {
            fail(e.getMessage());
        }
    }
}
