package compal.logic.command;

import compal.logic.command.exceptions.CommandException;
import compal.model.tasks.Deadline;
import compal.model.tasks.Event;
import compal.model.tasks.Task;
import compal.model.tasks.TaskList;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static compal.logic.command.CommandTestUtil.assertCommandFailure;
import static org.junit.jupiter.api.Assertions.assertEquals;

//@@author Catherinetan99
public class SetReminderCommandTest {

    private ArrayList<Task> taskArrListMain = new ArrayList<>();
    private ArrayList<Task> taskArrListDup = new ArrayList<>();

    private TaskList taskListMain = new TaskList();
    private TaskList taskListDup = new TaskList();

    @org.junit.jupiter.api.BeforeEach
    void setUp() {
        taskListMain.setArrList(taskArrListMain);
        taskListDup.setArrList(taskArrListDup);

        Event event1 = new Event("Event 1", Task.Priority.medium, "05/12/2019", "01/10/2019", "1400", "1500");
        taskListMain.addTask(event1);
        taskListDup.addTask(event1);

        Deadline deadline1 = new Deadline("Deadline 1", Task.Priority.high, "05/12/2019", "1500");
        taskListMain.addTask(deadline1);
        taskListDup.addTask(deadline1);
    }

    @Test
    void execute_invalidTaskId_exceptionThrown() {
        SetReminderCommand testSetReminder = new SetReminderCommand(1000, "Y");
        assertCommandFailure(testSetReminder, taskListMain, SetReminderCommand.MESSAGE_INVALID_ID);
    }

    @Test
    void execute_invalidTaskStatus_exceptionThrown() {
        SetReminderCommand testSetReminder = new SetReminderCommand(0, "invalid");
        assertCommandFailure(testSetReminder, taskListMain, SetReminderCommand.MESSAGE_INVALID_STATUS_INPUT);
    }

    @Test
    void execute_setReminderTrue_success() throws CommandException {
        SetReminderCommand testSetReminder = new SetReminderCommand(0, "Y");
        CommandResult testCommandResult = testSetReminder.commandExecute(taskListMain);
        String testString = testCommandResult.feedbackToUser;

        String expectedString = "Noted. I have changed the reminder status of this task.\n";
        expectedString = expectedString.concat(taskListDup.getTaskById(0).toString());

        assertEquals(expectedString, testString);
    }

    @Test
    void execute_setReminderFalse_success() throws CommandException {
        SetReminderCommand testSetReminder = new SetReminderCommand(1, "N");
        CommandResult testCommandResult = testSetReminder.commandExecute(taskListMain);
        String testString = testCommandResult.feedbackToUser;

        String expectedString = "Noted. I have changed the reminder status of this task.\n";
        expectedString = expectedString.concat(taskListDup.getTaskById(1).toString());

        assertEquals(expectedString, testString);
    }
}
