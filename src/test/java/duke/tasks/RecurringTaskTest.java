package duke.tasks;

import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;

class RecurringTaskTest {

    @Test
    void testToString() {
        LocalDateTime startDate = LocalDateTime.of(2219, 10, 2, 8, 8);
        RecurringTask recurringTask = new RecurringTask("Homework", startDate, 2);
        recurringTask.updateRecurringTask();
        assertEquals(recurringTask.toString(), "[R][✘] Homework by "
                        + startDate.toString().replace("T", " ") + " (every 2 days)");
    }
}
