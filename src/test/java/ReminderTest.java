import duke.module.Reminder;
import duke.task.TaskList;
import org.junit.jupiter.api.Test;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ReminderTest {

    @Test
    public void dummyTest() {
      assertEquals(2, 2);
    }

    /**
     * Tests the saving of the dates to the reminder class
     */
    @Test
    public void reminderTestDate () {
        String date = "20/09/2020 1900";
        Date formatDate = TaskList.dateConvert(date);
        Reminder reminder = new Reminder(formatDate);
        try {
            Date expectedDate = new SimpleDateFormat("dd/MM/yyyy HHmm").parse(date);
            assertEquals(expectedDate, reminder.getEndDate());
        }
        catch (ParseException e) {
            System.err.println("Date error");
        }
    }

}
