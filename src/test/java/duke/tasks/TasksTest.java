package duke.tasks;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;

import duke.exceptions.DukeInvalidTimeException;

public class TasksTest {

    @Test
    public void testTaskPrint()  {
        String taskLabel = "Items to be tested";
        Task test = new Task(taskLabel);
        assertEquals(taskLabel, test.getTask());
    }

    @Test
    public void testTodoDisplay() {
        String taskLabel = "TodoTest";
        String expectedPrintTodo = "[T][✗] TodoTest";
        Task test = new Todo(taskLabel);
        assertEquals(expectedPrintTodo, test.toString());
    }

    @Test
    public void testTodoFile() {
        String taskLabel = "TodoTest";
        String expectedWriteTodo = "T|TodoTest|0";
        Task test = new Todo(taskLabel);
        assertEquals(expectedWriteTodo, test.writingFile());
    }

    @Test
    public void testEventsDisplay() {
        String taskLabel = "EventTest";
        String dateLabel = "02-10-2012";
        String expectedPrintTodo = "[E][✗] EventTest (at: 02-10-2012 00:00)";
        try {
            Task test = new Events(taskLabel, dateLabel);
            assertEquals(expectedPrintTodo, test.toString());
        } catch (DukeInvalidTimeException e) {
            System.out.println(e.getMessage());
        }
    }

    @Test
    public void testEventsFile() {
        String taskLabel = "EventTest";
        String dateLabel = "02-10-2012";
        String expectedWriteTodo = "E|EventTest|0|02-10-2012 00:00";
        try {
            Task test = new Events(taskLabel, dateLabel);
            assertEquals(expectedWriteTodo, test.writingFile());
        } catch (DukeInvalidTimeException e) {
            System.out.println(e.getMessage());
        }
    }

    @Test
    public void testDeadlineDisplay() {
        String taskLabel = "DeadlineTest";
        String dateLabel = "02/11/2013 1730";
        String expectedPrintTodo = "[D][✗] DeadlineTest (by: 02-11-2013 17:30)";
        try {
            Task test = new Deadline(taskLabel, dateLabel);
            assertEquals(expectedPrintTodo, test.toString());
        } catch (DukeInvalidTimeException e) {
            System.out.println(e.getMessage());
        }
    }

    @Test
    public void testDeadlineFile() {
        String taskLabel = "DeadlineTest";
        String dateLabel = "02/11/2013 1730";
        String expectedWriteTodo = "D|DeadlineTest|0|02-11-2013 17:30";
        try {
            Task test = new Deadline(taskLabel, dateLabel);
            assertEquals(expectedWriteTodo, test.writingFile());
        } catch (DukeInvalidTimeException e) {
            System.out.println(e.getMessage());
        }
    }




}
