import controllers.TaskFactory;
import exceptions.DukeException;
import models.tasks.Deadline;
import models.tasks.ITask;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertSame;

public class DeadlineTest {
    TaskFactory taskFactory = new TaskFactory();

    @Test
    public void alwaysTrue() {
        assertEquals(2, 2);
    }

    @Test
    public void creationDeadlineValidDate() {
        String input = "deadline return book /by 02/08/2012 1830";
        ITask expectedTask;
        ITask dummyTask = new Deadline("return book", "2 August 2012 06.30 PM");
        try {
            expectedTask = taskFactory.createTask(input);
            assertEquals(expectedTask.getDescription(), dummyTask.getDescription());
        } catch (DukeException e) {
            e.printStackTrace();
        }
    }
}
