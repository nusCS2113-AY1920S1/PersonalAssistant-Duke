import duke.items.tasks.Recurring;
import duke.items.tasks.Task;
import org.junit.jupiter.api.Test;

import java.util.Date;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class RecurringTest {

    @Test
    public void recurringTask_createNewWithValidInput_successfullyCreatedWithInstanceCountOne() {
        com.joestelmach.natty.Parser parser = new com.joestelmach.natty.Parser();

        Date startDate = parser.parse("09/19/2019 12:00").get(0).getDates().get(0);
        Date endDate = parser.parse("09/20/2019 12:00").get(0).getDates().get(0);

        Task task = new Recurring("meet with team daily", startDate, endDate, 1440);

        assertEquals("[R][1] meet with team daily (at: 09/19/2019 12:00 to 09/20/2019 12:00 every 1440 minutes)",
                task.toString());
    }

    @Test
    public void recurringTask_incrementInstanceUsingDone_instanceCountIncrementedByOne() {
        com.joestelmach.natty.Parser parser = new com.joestelmach.natty.Parser();

        Date startDate = parser.parse("10/1/2019 05:00").get(0).getDates().get(0);
        Date endDate = parser.parse("10/1/2019 06:00").get(0).getDates().get(0);

        Task task = new Recurring("don't talk about the fight club", startDate, endDate, 10080,
                3);

        assertEquals("[R][3] don't talk about the fight club (at: 10/01/2019 05:00 to 10/01/2019"
                + " 06:00 every 10080 minutes)",
                task.toString());

        task.markDone();

        assertEquals("[R][4] don't talk about the fight club (at: 10/08/2019 05:00 to 10/08/2019"
                + " 06:00 every 10080 minutes)",
                task.toString());
    }
}
