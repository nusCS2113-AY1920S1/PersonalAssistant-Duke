package compal.logic.command;

import compal.logic.command.exceptions.CommandException;
import compal.model.tasks.Deadline;
import compal.model.tasks.Event;
import compal.model.tasks.Task;
import compal.model.tasks.TaskList;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import static compal.model.tasks.Task.Priority.high;
import static compal.model.tasks.Task.Priority.medium;
import static compal.logic.command.CommandTestUtil.assertCommandFailure;

//@@author yueyeah
public class DeleteCommandTest {
    private ArrayList<Task> taskArrListMain = new ArrayList<>();
    private ArrayList<Task> taskArrListDup = new ArrayList<>();

    private TaskList taskListMain = new TaskList();
    private TaskList taskListDup = new TaskList();

    @BeforeEach
    void setUp() {
        this.taskListMain.setArrList(taskArrListMain);
        this.taskListDup.setArrList(taskArrListDup);
        Event testEvent = new Event("Event 1", medium, "01/10/2019", "01/10/2019", "1400", "1500");
        Deadline testDeadline = new Deadline("Deadline 1", high, "01/10/2019", "1500");
        taskListMain.addTask(testEvent);
        taskListMain.addTask(testDeadline);
        taskListDup.addTask(testEvent);
        taskListDup.addTask(testDeadline);
    }

    @Test
    void execute_invalidTaskID_fail() {
        DeleteCommand testDeleteCommand = new DeleteCommand(1000);
        assertCommandFailure(testDeleteCommand, taskListMain, DeleteCommand.MESSAGE_INVALID_ID);
    }

    @Test
    void execute_validTaskIdEvent_success() throws CommandException {
        DeleteCommand testDeleteCommand = new DeleteCommand(0);
        CommandResult testCommandResult = testDeleteCommand.commandExecute(taskListMain);
        String testString = testCommandResult.feedbackToUser;
        String expectedString = "The following task has been deleted: \n";
        expectedString += taskListDup.getTaskById(0).toString();
        Assertions.assertEquals(expectedString, testString);
    }

    @Test
    void execute_validTaskIdDeadline_success() throws CommandException {
        DeleteCommand testDeleteCommand = new DeleteCommand(1);
        CommandResult testCommandResult = testDeleteCommand.commandExecute(taskListMain);
        String testString = testCommandResult.feedbackToUser;
        String expectedString = "The following task has been deleted: \n";
        expectedString += taskListDup.getTaskById(1).toString();
        Assertions.assertEquals(expectedString, testString);
    }
}
