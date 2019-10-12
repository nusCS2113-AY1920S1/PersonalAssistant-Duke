package temp;

import controllers.temp.RecurringFactory;
import exceptions.DukeException;
import models.temp.tasks.ITask;
import models.temp.tasks.Recurring;
import org.junit.jupiter.api.Test;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class RecurringTest {
    RecurringFactory recurringFactory = new RecurringFactory();

    @Test
    public void alwaysTrue() {
        assertEquals(2, 2);
    }

    @Test
    public void testRecurringCreation() {
        try {
            String input = "recurring do Tutorial /at 23/09/2019 1500";
            SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy HHmm");
            Date dummyDate = formatter.parse("23/09/2019 1500");
            Recurring dummyTask = new Recurring("do Tutorial", "23 September 2019 03.00 PM", dummyDate);
            ITask generatedTask = recurringFactory.createTask(input);
            assertEquals(generatedTask.getDescription(), dummyTask.getDescription());
        } catch (DukeException | ParseException e) {
            e.printStackTrace();
        }
    }
}
