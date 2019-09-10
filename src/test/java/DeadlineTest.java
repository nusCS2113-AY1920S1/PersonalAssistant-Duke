import org.junit.jupiter.api.Test;
import task.Deadline;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class DeadlineTest {
    @Test
    void testDeadline() {
        String a = "eat";
        String b = "28/9/2019 1700";
        Deadline deadline = new Deadline(a, b);
        assertEquals(deadline.toString(), "[D][✘] eat (by: Sat Sep 28 17:00:00 SGT 2019)");
    }
}
