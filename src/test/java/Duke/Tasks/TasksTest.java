package Duke.Tasks;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class TasksTest {

    @Test
    public void testTask()  {
        String taskLabel = "Items to be tested";
        Task test = new Task(taskLabel);
        assertEquals(taskLabel, test.getTask());
    }

    @Test
    public void testTodo() {
        String taskLabel = "TodoTest";
        String expectedPrintTodo = "[T][✗] TodoTest";
        String expectedWriteTodo = "T|TodoTest|0";
        Task test = new Todo(taskLabel);
        assertEquals(expectedPrintTodo, test.toString());
        assertEquals(expectedWriteTodo, test.writingFile());
    }

    @Test
    public void testEvents() {
        String taskLabel = "EventTest";
        String dateLabel = "02-10-2012";
        String expectedPrintTodo = "[E][✗] EventTest (at: 02-10-2012 00:00)";
        String expectedWriteTodo = "E|EventTest|0|02-10-2012 00:00";
        Task test = new Events(taskLabel, dateLabel);
        assertEquals(expectedPrintTodo, test.toString());
        assertEquals(expectedWriteTodo, test.writingFile());
    }

    @Test
    public void testDeadlines() {
        String taskLabel = "DeadlineTest";
        String dateLabel = "02/11/2013 1730";
        String expectedPrintTodo = "[D][✗] DeadlineTest (by: 02-11-2013 17:30)";
        String expectedWriteTodo = "D|DeadlineTest|0|02-11-2013 17:30";
        Task test = new Deadline(taskLabel, dateLabel);
        assertEquals(expectedPrintTodo, test.toString());
        assertEquals(expectedWriteTodo, test.writingFile());
    }
}
