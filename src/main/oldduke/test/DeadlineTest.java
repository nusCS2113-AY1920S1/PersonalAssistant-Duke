package duke;

import org.junit.jupiter.api.Test;
import duke.task.duketasks.Deadline;
import duke.task.duketasks.Task;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class DeadlineTest {

    @Test
    public void shouldTestDeadlineToSaveString() {
        // assert statements
        assertEquals("D | - | return book | 2/12/2019 1800",
                new Deadline("return book", "2/12/2019 1800").toSaveString());
    }

    @Test
    public void shouldTestDeadlineToString() {
        // assert statements
        assertEquals("[D][-] return book (by: 2/12/2019 1800)",
                new Deadline("return book", "2/12/2019 1800").toString());
    }

    @Test
    public void shouldTestDeadlineDateConversion() {
        String date = new Deadline("return book", "2/12/2019 1800").convertDate("2/12/2019 1800");
        // assert statements
        assertEquals("2nd of December 2019, 6.00pm", date);
    }

    @Test
    public void shouldTestDeadlineGetStatusIcon() {
        Task deadline = new Deadline("borrow book", "2/12/2019 1800");
        deadline.markAsDone();
        // assert statements
        assertEquals("+", deadline.getStatusIcon());
    }
}