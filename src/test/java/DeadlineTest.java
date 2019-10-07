import controllers.TaskFactory;
import exceptions.DukeException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
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
    public void test_createTask_deadlineValidDate() {
        String input = "deadline return book /by 02/08/2012 1830";
        ITask expectedTask;
        try {
            SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy HHmm");
            Date date = formatter.parse("02/08/2012 1830");
            ITask dummyTask = new Deadline("return book", "02 August 2012 06.30 pm", date);
            expectedTask = taskFactory.createTask(input);
            assertEquals(dummyTask.getDescription(), expectedTask.getDescription());
        } catch (DukeException | ParseException e) {
            e.printStackTrace();
        }
    }
}
