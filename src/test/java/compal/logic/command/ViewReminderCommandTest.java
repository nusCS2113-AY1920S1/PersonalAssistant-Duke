package compal.logic.command;

import compal.logic.command.exceptions.CommandException;
import compal.model.tasks.Deadline;
import compal.model.tasks.Event;
import compal.model.tasks.Task;
import compal.model.tasks.TaskList;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static compal.logic.command.CommandTestUtil.assertCommandFailure;
import static org.junit.jupiter.api.Assertions.assertEquals;

//@@author Catherinetan99
public class ViewReminderCommandTest {

    private ArrayList<Task> taskArrListMain = new ArrayList<>();
    private ArrayList<Task> taskArrListDup = new ArrayList<>();

    private TaskList taskListMain = new TaskList();
    private TaskList taskListDup = new TaskList();

    @BeforeEach
    void setUp() {
        taskListMain.setArrList(taskArrListMain);
        taskListDup.setArrList(taskArrListDup);

        Event event1 = new Event("Event 1", Task.Priority.medium, "26/10/2019", "26/10/2019", "1400", "1500");
        taskListMain.addTask(event1);
        taskListDup.addTask(event1);

        Event event2 = new Event("Event 2", Task.Priority.high, "05/12/2019", "05/12/2019", "0900", "1600");
        event2.setHasReminder(true);
        taskListMain.addTask(event2);
        taskListDup.addTask(event2);

        Deadline deadline1 = new Deadline("Deadline 1", Task.Priority.high, "01/11/2019", "1500");
        taskListMain.addTask(deadline1);
        taskListDup.addTask(deadline1);

        Deadline deadline2 = new Deadline("Deadline 2", Task.Priority.low, "29/10/2019", "1400");
        deadline2.markAsDone();
        taskListMain.addTask(deadline2);
        taskListDup.addTask(deadline2);

        taskListMain.sortTask(taskListMain.getArrList());
        taskListDup.sortTask(taskListDup.getArrList());
    }

    @Test
    void execute_arrayListNotInitialised_exceptionThrown() {
        TaskList taskList = new TaskList();
        ViewReminderCommand testViewReminder = new ViewReminderCommand();
        assertCommandFailure(testViewReminder, taskList, ViewReminderCommand.MESSAGE_UNABLE_TO_EXECUTE);
    }

    @Test
    void execute_viewReminderCommand_success() throws CommandException {
        ViewReminderCommand testViewReminder = new ViewReminderCommand();
        CommandResult testCommandResult = testViewReminder.commandExecute(taskListMain);
        String testString;
        testString = testCommandResult.feedbackToUser;

        String expectedString = "Here are your tasks:\n";
        expectedString = expectedString.concat(taskListDup.getTaskById(0).toString() + "\n");
        expectedString = expectedString.concat(taskListDup.getTaskById(2).toString() + "\n");
        expectedString = expectedString.concat(taskListDup.getTaskById(1).toString() + "\n");

        assertEquals(expectedString, testString);
    }
}
