package seedu.hustler.schedule;

import org.junit.jupiter.api.Test;
import java.time.LocalDateTime;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
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

        ToDo doneTodo = new ToDo("done todo");
        doneTodo.markAsDone();
        
        assertEquals(1, scheduler.size());
    }
    
    @Test
    /**
     * Tests the remove method.
     */
    void remove() {
        Scheduler scheduler = Scheduler.getInstance();
        ToDo todo = new ToDo("random todo");
        ToDo todoNotInList = new ToDo("random todo 2");
        scheduler.add(todo);
        scheduler.recommend(3600);
        scheduler.remove(todoNotInList);
        assertEquals(1, scheduler.size());
        assertEquals(1, scheduler.recommended.size());
        scheduler.remove(todo);
        assertEquals(0, scheduler.size());
        assertEquals(0, scheduler.recommended.size());
    }
    
    @Test
    /**
     * Test the remove addtorecommended method.
     */
    void addToRecommended() {
        Scheduler scheduler = Scheduler.getInstance();
        ToDo todo = new ToDo("random todo");
        scheduler.add(todo);
        assertDoesNotThrow(() -> {
            scheduler.addToRecommended(todo);
        });

        assertThrows(CommandLineException.class, () -> {
            scheduler.addToRecommended(todo);
        });

        ToDo doneTodo = new ToDo("random todo 2");
        doneTodo.markAsDone();

        assertThrows(CommandLineException.class, () -> {
            scheduler.addToRecommended(todo);
        });
    }

    @Test
    /**
     * Tests the confirm command.
     */
    void confirm() {
        Scheduler scheduler = Scheduler.getInstance();
        ToDo todo = new ToDo("random todo");
        scheduler.add(todo);
        scheduler.recommend(3600);
        assertEquals(scheduler.recommended.size(), 1);
        try {
            scheduler.confirm();
        } catch(NullPointerException e) {
            System.out.println("Ignore this message.");
        }
        assertEquals(scheduler.getEntry(0).getTimeSpent(), 3600);
    }
}
