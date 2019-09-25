import duchess.logic.commands.exceptions.DukeException;
import duchess.model.task.Deadline;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class TaskTest {
    @Test
    void maskAsDone_works() throws DukeException {
        Deadline deadline = new Deadline(
                List.of("return book /by 12/12/2020 1200".split(" "))
        );

        assertTrue(deadline.toString().contains("✘"));
        deadline.markAsDone();
        assertTrue(deadline.toString().contains("✓"));
    }
}
