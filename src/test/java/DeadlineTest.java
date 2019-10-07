import controllers.temp.TaskFactory;
import exceptions.DukeException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import models.temp.tasks.Deadline;
import models.temp.tasks.ITask;
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
            ITask dummyTask = new Deadline("return book", "02 August 2012 06.30 PM", date);
            expectedTask = taskFactory.createTask(input);
            System.out.println(dummyTask.getDescription());
            System.out.println(expectedTask.getDescription());
            assertEquals(dummyTask.getDescription(), expectedTask.getDescription());
        } catch (DukeException | ParseException e) {
            e.printStackTrace();
        }
    }
}
