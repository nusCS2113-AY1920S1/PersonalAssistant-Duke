import duke.exception.DukeException;
import duke.task.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.Duration;
import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

public class DurationPeriodTest {
    private TaskList taskList;

    /**
     * Adds tasks to an empty TaskList. This is executed before each JUnit test.
     */
    @BeforeEach
    public void taskList_addTasksSuccess() {
        taskList = new TaskList();
        ToDoTask todo = new ToDoTask("JUnit tests");
        LocalDateTime t = LocalDateTime.parse("12/09/2019 1400", TimedTask.getPatDatetime());
        Duration d = Duration.parse("PT3H5M30S");
        FixedDurationTask fixedD = new FixedDurationTask("lecture", d);
        DoWithinPeriodTask periodT = new DoWithinPeriodTask("assignment submission", t, t);

        try {
            assertTrue(taskList.addTask(todo).contains("1 task"));
            assertTrue(taskList.addTask(fixedD).contains("2 tasks"));
            assertTrue(taskList.addTask(periodT).contains("3 tasks"));
        } catch (AssertionError excp) {
            fail("Total number of tasks added is not 3!");
        }

        try {
            String expectedTaskListStr = System.lineSeparator() + "1.[T][\u2718] JUnit tests"
                    + System.lineSeparator() + "2.[F][\u2718] lecture (for: 3 hour, 05 minutes, 30 seconds)"
                    + System.lineSeparator() + "3.[B][\u2718] assignment submission (between: Thu, 12 Sep 2019 2:00 PM and Thu, 12 Sep 2019 2:00 PM)";
            assertEquals(expectedTaskListStr, taskList.listTasks());
        } catch (DukeException excp) {
            fail("No tasks in the list after adding!");
        } catch (AssertionError excp) {
            try{
                fail("Tasks not listed correctly!" + taskList.listTasks());
            } catch (DukeException e) {
                fail(e);
            }

        }
    }

    @Test
    public void taskList_deleteTasksSuccess() {
        try {
            taskList.deleteTask("1");
            taskList.deleteTask("2");
            assertEquals(System.lineSeparator() + "1.[F][\u2718] lecture (for: 3 hour, 05 minutes, 30 seconds)",
                    taskList.listTasks());
        } catch (DukeException excp) {
            fail("Unable to find added tasks!");
        } catch (AssertionError excp) {
            fail("Tasks not deleted correctly!");
        }
    }

    @Test
    public void taskList_deleteTasksFailure() { //also tests for failure of other "getIdx" tasks
        assertThrows(DukeException.class, () -> {
            taskList.deleteTask("100");
        });
        String expectedTaskListStr = System.lineSeparator() + "1.[T][\u2718] JUnit tests"
                + System.lineSeparator() + "2.[F][\u2718] lecture (for: 3 hour, 05 minutes, 30 seconds)"
                + System.lineSeparator() + "3.[B][\u2718] assignment submission (between: Thu, 12 Sep 2019 2:00 PM and Thu, 12 Sep 2019 2:00 PM)";
        try {
            assertEquals(expectedTaskListStr, taskList.listTasks());
        } catch (DukeException excp) {
            fail("Task list empty after invalid deletion!");
        } catch (AssertionError excp) {
            fail("Task list modified by invalid deletion!");
        }
    }

    @Test
    public void taskList_findTasksSuccess() {
        String expectedSearchResult = "Here are the tasks that contain 'u':"
                + System.lineSeparator() + "1.[F][\u2718] lecture (for: 3 hour, 05 minutes, 30 seconds)"
                + System.lineSeparator() + "2.[B][\u2718] assignment submission (between: Thu, 12 Sep 2019 2:00 PM and Thu, 12 Sep 2019 2:00 PM)";
        assertEquals(expectedSearchResult, taskList.find("u"));
    }

    @Test
    public void taskList_findTasksFailure() {
        String expectedSearchResult = "Can't find any matching tasks!";
        assertEquals(expectedSearchResult, taskList.find("CS2113"));
    }

    @Test
    public void taskList_markDoneSuccess() {
        try {
            taskList.markDone("1");
            taskList.markDone("2");
            taskList.markDone("3");
        } catch (DukeException excp) {
            fail("Not able to find tasks to mark as done!");
        }

        try {
            String expectedTaskListStr = System.lineSeparator() + "1.[T][\u2713] JUnit tests"
                    + System.lineSeparator() + "2.[F][\u2713] lecture (for: 3 hour, 05 minutes, 30 seconds)"
                    + System.lineSeparator() + "3.[B][\u2713] assignment submission (between: Thu, 12 Sep 2019 2:00 PM and Thu, 12 Sep 2019 2:00 PM)";
            assertEquals(expectedTaskListStr, taskList.listTasks());
        } catch (DukeException excp) {
            fail("Marking tasks as done somehow deleted all tasks in the list!");
        } catch (AssertionError excp) {
            fail("Tasks not correctly marked as done!");
        }
    }
}