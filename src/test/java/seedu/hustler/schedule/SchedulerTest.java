package seedu.hustler.schedule;

import org.junit.jupiter.api.Test;
import java.time.LocalDateTime;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import seedu.hustler.logic.CommandLineException;
import seedu.hustler.task.ToDo;
import seedu.hustler.task.Deadline;

public class SchedulerTest {

    @Test
    /**
     * Testing add method.
     */
    void add() {
        Scheduler scheduler = Scheduler.getInstance();
        ToDo todo = new ToDo("random todo");
        scheduler.add(todo);
        assertEquals(todo, scheduler.getEntry(0).getTask());

        assertThrows(CommandLineException.class, () -> {
            scheduler.add(todo);
        });

        ToDo doneTodo = new ToDo("done todo");
        doneTodo.markAsDone();

        assertThrows(CommandLineException.class, () -> {
            scheduler.add(doneTodo);
        });
    } 
}
