import duke.task.Task;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.LocalDate;

class TaskTest {

     private final Task task = new Task("homework", "daily");

    @Test
     public void testIsTimeToReset() {
        assertEquals(true, task.isTimeToReset(LocalDate.now().minusDays(1), LocalDate.now()));
        assertEquals(false, task.isTimeToReset(LocalDate.now(), LocalDate.now()));
    }

}
