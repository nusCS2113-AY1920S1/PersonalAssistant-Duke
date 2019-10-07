import controllers.temp.PeriodTaskFactory;
import exceptions.DukeException;
import models.tasks.ITask;
import models.tasks.PeriodTask;
import org.junit.jupiter.api.Test;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class PeriodTaskTest {
    PeriodTaskFactory periodTaskFactory = new PeriodTaskFactory();

    @Test
    public void alwaysTrue() {
        assertEquals(2, 2);
    }

    @Test
    void testPeriodTaskCreation() {
        try {
            String input = "period collect test /between 23/09/2019 and 26/09/2019";
            SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
            Date dummyStartDate = formatter.parse("23/09/2019");
            Date dummyEndDate = formatter.parse("26/09/2019");
            String dummyPeriodDescription = "23 Sep 2019 and 26 Sep 2019";
            PeriodTask dummyTask = new PeriodTask("collect test", dummyPeriodDescription, dummyStartDate, dummyEndDate);
            ITask generatedTask = periodTaskFactory.createTask(input);
            assertEquals(generatedTask.getDescription(), dummyTask.getDescription());
        } catch (DukeException | ParseException e) {
            e.printStackTrace();
        }
    }
}
