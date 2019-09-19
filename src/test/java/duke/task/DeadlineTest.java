package duke.task;

import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class DeadlineTest {
    @Test
    public void testToString() {
        LocalDateTime testTime = LocalDateTime.of(2017, 2, 13, 15, 56);
        Deadline newDeadline = new Deadline("To Complete Test", testTime);
        assertEquals("[D][NOT DONE] To Complete Test (by: 13/2/2017 1556)", newDeadline.toString());

        newDeadline.markAsDone();
        assertEquals("[D][DONE] To Complete Test (by: 13/2/2017 1556)", newDeadline.toString());
    }
}