package compal.logic.command;

import compal.logic.command.exceptions.CommandException;
import compal.model.tasks.Deadline;
import compal.model.tasks.Event;
import compal.model.tasks.Task;
import compal.model.tasks.TaskList;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static compal.logic.command.CommandTestUtil.assertCommandFailure;
import static compal.logic.command.DoneCommand.MESSAGE_INVALID_ID;
import static compal.logic.command.DoneCommand.MESSAGE_INVALID_INPUT;
import static compal.model.tasks.Task.Priority.high;
import static compal.model.tasks.Task.Priority.medium;

import org.junit.jupiter.api.Assertions;

//@@author SholihinK
class DoneCommandTest {

    private ArrayList<Task> taskArrListMain = new ArrayList<>();
    private ArrayList<Task> taskArrListDup = new ArrayList<>();

    private TaskList taskListMain = new TaskList();
    private TaskList taskListDup = new TaskList();

    @org.junit.jupiter.api.BeforeEach
    void setUp() {
        Event event1 = new Event("Event 1", medium, "01/10/2019", "01/10/2019", "1400", "1500");
        Deadline deadline1 = new Deadline("Deadline 1", high, "01/10/2019", "1500");

        taskArrListMain.add(event1);
        taskArrListMain.add(deadline1);

        taskArrListDup.add(event1);
        taskArrListDup.add(deadline1);

        this.taskListMain.setArrList(taskArrListMain);
        this.taskListDup.setArrList(taskArrListDup);
    }

    @Test
    void execute_invalidTaskID_fail() {
        DoneCommand doneCommand = new DoneCommand(1243134214, "y");
        assertCommandFailure(doneCommand, taskListMain, MESSAGE_INVALID_ID);
    }

    @Test
    void execute_invalidStatus_fail() {
        DoneCommand doneCommand = new DoneCommand(0, "wrong status here");
        assertCommandFailure(doneCommand, taskListMain, MESSAGE_INVALID_INPUT);
    }

    @Test
    void execute_done_success() throws CommandException {
        String expected = new DoneCommand(0, "y").commandExecute(taskListDup).feedbackToUser;
        String tested = done(taskListMain,0,"y");
        Assertions.assertEquals(expected, tested);
    }

    @Test
    void execute_undone_success() throws CommandException {
        String expected = new DoneCommand(0, "n").commandExecute(taskListDup).feedbackToUser;
        String tested = done(taskListMain,0,"n");
        Assertions.assertEquals(expected, tested);
    }

    private String done(TaskList taskList, int taskID, String status) {
        Task task = taskList.getTaskById(taskID);
        String finalString = "";

        final String COMMAND_PREFIX = "Noted. I have mark the below task as done: \n";
        final String COMMAND_PREFIX2 = "Noted. I have mark the below task as not done: \n";
        if (status.equalsIgnoreCase("y")) {
            task.markAsDone();
            finalString += COMMAND_PREFIX.concat(task.toString());
        } else if (status.equalsIgnoreCase("n")) {
            task.markAsNotDone();
            finalString += COMMAND_PREFIX2.concat(task.toString());
        }
        return finalString;

    }
}