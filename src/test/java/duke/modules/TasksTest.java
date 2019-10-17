package duke.modules;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;

import duke.exceptions.ModInvalidTimePeriodException;


public class TasksTest {

    @Test
    public void testTaskPrint() {
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
        } catch (Throwable e) {
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
        } catch (Throwable e) {
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
        } catch (Throwable e) {
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
        } catch (Throwable e) {
            System.out.println(e.getMessage());
        }
    }

    @Test
    public void testRecurringTask() {
        String taskLabel = "RecurringTaskTest";
        String dateLabel = "/days 1";
        String expectedPrintTodo = "[R][✗] RecurringTaskTest (every: 24Hours)";
        String expectedWriteTodo = "R|RecurringTaskTest|0|PT24H";
        try {
            Task test = new RecurringTask(taskLabel, dateLabel);
            assertEquals(expectedPrintTodo, test.toString());
            assertEquals(expectedWriteTodo, test.writingFile());
        } catch (Throwable e) {
            System.out.println(e.getMessage());
        }
    }

    @Test
    public void testDoWithinTask() {
        String taskLabel = "DoWithinTaskTest";
        String beginDateLabel = "02/11/2030 1730";
        String endDateLabel = "03/11/2030 2am";
        String expectedPrintTodo = "[W][✗] DoWithinTaskTest (begin: 02-11-2030 17:30, end: 03-11-2030 02:00)";
        String expectedWriteTodo = "W|DoWithinTaskTest|0|02-11-2030 17:30|03-11-2030 02:00";
        try {
            Task test = new DoWithin(taskLabel, beginDateLabel, endDateLabel);
            assertEquals(expectedPrintTodo, test.toString());
            assertEquals(expectedWriteTodo, test.writingFile());
        } catch (Throwable e) {
            System.out.println(e.getMessage());
        }
    }

    @Test
    public void testFixedDurationTask() {
        String taskLabel = "FixedDurationTaskTest";
        String timeNeededLabel = "/hours 3";
        String expectedPrint = "[F][✗] FixedDurationTaskTest (needs: 3Hours)";
        String expectedWrite = "F|FixedDurationTaskTest|0|PT3H";
        try {
            Task test = new FixedDurationTask(taskLabel, timeNeededLabel);
            assertEquals(expectedPrint, test.toString());
            assertEquals(expectedWrite, test.writingFile());
        } catch (Throwable e) {
            System.out.println(e.getMessage());
        }
    }
}
