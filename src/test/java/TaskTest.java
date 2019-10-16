import duke.task.Task;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.LocalDate;

class TaskTest {


    @Test
     public void testIsTimeToReset() {
        final Task taskDaily = new Task("homework", "daily");
        assertEquals(true, taskDaily.isTimeToReset(LocalDate.now().minusDays(1), LocalDate.now()));
        assertEquals(false, taskDaily.isTimeToReset(LocalDate.now(), LocalDate.now()));

        final Task taskWeekly = new Task("homework", "weekly");
        assertEquals(false, taskWeekly.isTimeToReset(LocalDate.now().minusDays(1), LocalDate.now()));
        assertEquals(false, taskWeekly.isTimeToReset(LocalDate.now(), LocalDate.now()));
        assertEquals(true, taskWeekly.isTimeToReset(LocalDate.now().minusDays(8), LocalDate.now()));
    }



}
