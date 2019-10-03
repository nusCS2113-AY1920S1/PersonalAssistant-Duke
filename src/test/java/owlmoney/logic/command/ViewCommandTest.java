package owlmoney.logic.command;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.time.LocalDate;
import java.util.ArrayList;

import org.junit.jupiter.api.Test;

import owlmoney.logic.exception.DukeException;
import owlmoney.model.task.Task;
import owlmoney.model.task.TaskList;

public class ViewCommandTest {
    private ArrayList<String> viewTasks = new ArrayList<>();
    private String input;
    private LocalDate date;

    /**
     * Test for the correct exception thrown when empty date is provided.
     */
    @Test
    void viewCommand_EmptyDate_throwsDukeException() {
        input = "";
        DukeException error = assertThrows(DukeException.class, () -> new ViewCommand(input));
        assertEquals("The time cannot be empty or space bar", error.getMessage());
    }

    /**
     * Test for the correct exception thrown when invalid format date is provided.
     */
    @Test
    void viewCommand_InvalidFormatDate_throwsDukeException() {
        input = "05/25/2019";
        DukeException error = assertThrows(DukeException.class, () -> new ViewCommand(input));
        assertEquals("The date format is wrong/invalid, please try in DD/MM/YYYY format", error.getMessage());
    }

    /**
     * Test for correct exception thrown when a non-existent date is provided.
     */
    @Test
    void viewCommand_InvalidDate_throwsDukeException() {
        input = "31/04/2019";
        DukeException error = assertThrows(DukeException.class, () -> new ViewCommand(input));
        assertEquals("This date doesn't exist in the calendar!", error.getMessage());
    }

    /**
     * Test for the correct number of tasks return when there is no task on 1/10/2019.
     */
    @Test
    void viewCommand_viewSchedulesOnDate_expectZeroTask() {
        viewTasks.add("T | 0 | 4 | read");
        TaskList tasks = new TaskList(viewTasks);
        date = LocalDate.of(2019, 10, 1);
        ArrayList<Task> checks = tasks.viewFilterByDate(date);
        assertEquals(checks.size(), 0);
    }

    /**
     * Test for correct number of tasks return when there is one task on 22/09/2019.
     */
    @Test
    void viewCommand_viewSchedulesOnDate_expectOneTask() {
        viewTasks.add("D | 0 | 4 | 2113 | 14 | 22/9/2019 2359");
        TaskList tasks = new TaskList(viewTasks);
        date = LocalDate.of(2019, 9, 22);
        ArrayList<Task> checks = tasks.viewFilterByDate(date);
        assertEquals(checks.size(), 1);
    }

    /**
     * Test for correct number of tasks (2) return on specific date despite having multiple tasks
     * with different dates and task description.
     */
    @Test
    void viewCommand_viewSchedulesOnDate_expectTwoTasks() {
        viewTasks.add("D | 0 | 4 | 2113 | 14 | 22/9/2019 2359");
        viewTasks.add("E | 0 | 3 | run | 14 | 20/9/2019 0600");
        viewTasks.add("T | 0 | 4 | read");
        viewTasks.add("E | 0 | 4 | comp | 14 | 22/9/2019 0600");

        TaskList tasks = new TaskList(viewTasks);
        date = LocalDate.of(2019, 9, 22);
        ArrayList<Task> checks = tasks.viewFilterByDate(date);
        assertEquals(checks.size(), 2);

    }

    /**
     * Test for correct number of tasks return on specific date.
     */
    @Test
    void viewCommand_viewSchedulesOnDate_expectMultipleTasks() {
        viewTasks.add("D | 0 | 4 | 2113 | 14 | 21/9/2019 2359");
        viewTasks.add("E | 0 | 3 | run | 14 | 21/9/2019 0600");
        viewTasks.add("D | 1 | 4 | work | 14 | 21/9/2019 2300");
        viewTasks.add("E | 0 | 4 | comp | 14 | 21/9/2019 0800");
        viewTasks.add("E | 1 | 8 | carnival | 14 | 21/9/2019 1800");


        TaskList tasks = new TaskList(viewTasks);
        date = LocalDate.of(2019, 9, 21);
        ArrayList<Task> checks = tasks.viewFilterByDate(date);
        assertEquals(checks.size(), 5);

    }

}
