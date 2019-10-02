package duke.tasks;

import duke.exceptions.DukeCommandException;
import duke.exceptions.DukeInvalidIndexException;
import duke.exceptions.DukeMissingArgumentException;
import jdk.jfr.StackTrace;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;

import duke.exceptions.DukeInvalidTimeException;

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
        try {
            Task test = new Events(taskLabel, dateLabel);
            assertEquals(expectedPrintTodo, test.toString());
            assertEquals(expectedWriteTodo, test.writingFile());
        } catch (DukeInvalidTimeException e) {
            System.out.println(e.getMessage());
        }
    }

    @Test
    public void testDeadlines() {
        String taskLabel = "DeadlineTest";
        String dateLabel = "02/11/2013 1730";
        String expectedPrintTodo = "[D][✗] DeadlineTest (by: 02-11-2013 17:30)";
        String expectedWriteTodo = "D|DeadlineTest|0|02-11-2013 17:30";
        try {
            Task test = new Deadline(taskLabel, dateLabel);
            assertEquals(expectedPrintTodo, test.toString());
            assertEquals(expectedWriteTodo, test.writingFile());
        } catch (DukeInvalidTimeException e) {
            System.out.println(e.getMessage());
        }
    }

    @Test
    public void testRecurringTask() {
        String taskLabel = "RecurringTaskTest";
        String dateLabel = "1";
        String expectedPrintTodo = "[R][✗] RecurringTaskTest (every: 1 days)";
        String expectedWriteTodo = "R|RecurringTaskTest|0|1";
        try {
            Task test = new RecurringTask(taskLabel, dateLabel);
            assertEquals(expectedPrintTodo, test.toString());
            assertEquals(expectedWriteTodo, test.writingFile());
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
}
