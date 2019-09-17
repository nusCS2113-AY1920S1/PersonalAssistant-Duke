package duke.task;
import duke.command.RecurringCommand;
import duke.task.*;
import org.junit.jupiter.api.Test;

import java.time.Duration;
import java.time.LocalDateTime;
import static org.junit.jupiter.api.Assertions.*;

public class RecurringTest {

    private Task dummyToDo = new Todo("essay");
    private Task dummyDeadline = new Deadline("paper", "03/08/2018 1159");
    private Task dummyEvent = new Event("presentation", "15/09/2019 1000");


    @Test
    public void recurringDailyDeadlineTest() {
        LocalDateTime oldDateTime = dummyDeadline.getDateTime();
        dummyDeadline.makeTaskRecurring(Task.RecurringFrequency.DAILY);
        assert(dummyDeadline.getDateTime().isAfter(oldDateTime));

        Duration dayDifference = Duration.between(LocalDateTime.now(), dummyDeadline.getDateTime());
        long numberOfDaysDifference = Math.abs(dayDifference.toDays());
        assert(numberOfDaysDifference <= 1);
    }

    @Test
    public void recurringWeeklyEventTest() {
        LocalDateTime oldDateTime = dummyEvent.getDateTime();
        dummyEvent.makeTaskRecurring(Task.RecurringFrequency.WEEKLY);
        assert(dummyEvent.getDateTime().isAfter(oldDateTime));

        Duration dayDifference = Duration.between(LocalDateTime.now(), dummyEvent.getDateTime());
        long numberOfDaysDifference = Math.abs(dayDifference.toDays());
        assert(numberOfDaysDifference <= 7);
    }

    @Test public void recurringMonthlyDeadlineTest() {
        LocalDateTime oldDateTime = dummyDeadline.getDateTime();
        dummyDeadline.makeTaskRecurring(Task.RecurringFrequency.MONTHLY);
        assert(dummyDeadline.getDateTime().isAfter(oldDateTime));

        Duration dayDifference = Duration.between(LocalDateTime.now(), dummyDeadline.getDateTime());
        long numberOfDaysDifference = Math.abs(dayDifference.toDays());
        assert(numberOfDaysDifference <= 31);
    }

    @Test public void recurringTodoTest() {
        dummyToDo.makeTaskRecurring(Task.RecurringFrequency.DAILY);
        assertNotEquals(true, dummyToDo.isTaskRecurring());
    }

}
