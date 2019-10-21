import dolla.command.CompleteCommand;
import dolla.task.Task;
import dolla.task.TaskList;
import dolla.task.ToDo;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

public class CompleteCommandTest {
    /*
    @Test
    public void execute_indexInBounds_success() throws Exception {
        TaskList tasks = new TaskList(new ArrayList<Task>());
        Task sampleTodo = new ToDo("Sample Task");
        tasks.add(sampleTodo);
        new CompleteCommand("1").execute(tasks);
        assertEquals(true, tasks.getFromList(0).getIsDone());
    }

    @Test
    public void execute_indexOutOfBounds_exceptionThrown() {
        try {
            TaskList tasks = new TaskList(new ArrayList<Task>());
            new CompleteCommand("1").execute(tasks);
            fail();
        } catch (Exception e) {
            assertEquals("Task number not in list", e.getMessage());
        }
    }
    */
}