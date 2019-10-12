import controllers.TaskFactory;
import models.task.Task;
import models.task.TaskState;
import org.junit.jupiter.api.Test;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class TaskTest {
    private TaskFactory consoleInputFactory = new TaskFactory();

    @Test
    public void alwaysTrue() {
        assertEquals(2, 2);
    }

    @Test
    public void testAddTask() {
        try {
            SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
            Date dueDate = formatter.parse("19/10/2019");
            Task dummyTask = new Task("task1",1,dueDate,10,TaskState.TODO);
            Task task = consoleInputFactory.createTask("t/task1 p/1 d/19/10/2019 c/10 s/todo");
            assertEquals(dummyTask.getDetails(),task.getDetails());
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }
}
