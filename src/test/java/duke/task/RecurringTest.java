package duke.task;
import duke.core.DukeException;
import org.junit.jupiter.api.Test;

import java.time.Duration;
import java.time.LocalDateTime;

public class RecurringTest {

    @Test
    public void recurringDailyDeadlineTest() throws DukeException {
        Deadline dummyDeadline = new Deadline("paper", "03/08/2018 1159");
        LocalDateTime oldDateTime = dummyDeadline.getDateTime();
        dummyDeadline.makeTaskRecurring(Task.RecurringFrequency.DAILY);
        assert(dummyDeadline.getDateTime().isAfter(oldDateTime));

        Duration dayDifference = Duration.between(LocalDateTime.now(), dummyDeadline.getDateTime());
        long numberOfDaysDifference = Math.abs(dayDifference.toDays());
        assert(numberOfDaysDifference <= 1);
    }

    @Test
    public void recurringWeeklyEventTest() throws DukeException {
        Event dummyEvent = new Event("presentation", "15/09/2019 1000");
        LocalDateTime oldDateTime = dummyEvent.getDateTime();
        dummyEvent.makeTaskRecurring(Task.RecurringFrequency.WEEKLY);
        assert(dummyEvent.getDateTime().isAfter(oldDateTime));

        Duration dayDifference = Duration.between(LocalDateTime.now(), dummyEvent.getDateTime());
        long numberOfDaysDifference = Math.abs(dayDifference.toDays());
        assert(numberOfDaysDifference <= 7);
    }

    @Test public void recurringMonthlyDeadlineTest() throws DukeException {
        Deadline dummyDeadline = new Deadline("paper", "03/08/2018 1159");
        LocalDateTime oldDateTime = dummyDeadline.getDateTime();
        dummyDeadline.makeTaskRecurring(Task.RecurringFrequency.MONTHLY);
        assert(dummyDeadline.getDateTime().isAfter(oldDateTime));

        Duration dayDifference = Duration.between(LocalDateTime.now(), dummyDeadline.getDateTime());
        long numberOfDaysDifference = Math.abs(dayDifference.toDays());
        assert(numberOfDaysDifference <= 31);
    }

    /*
    @Test public void recurringTodoTest() {
        Task dummyToDo = new Todo("essay");
        dummyToDo.makeTaskRecurring(Task.RecurringFrequency.DAILY);
        assertNotEquals(true, dummyToDo.isTaskRecurring());
    }

     */

}
