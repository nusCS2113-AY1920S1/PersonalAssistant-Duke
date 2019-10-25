package compal.logic.command;

import compal.logic.command.exceptions.CommandException;
import compal.logic.parser.DoneParser;
import compal.model.tasks.Deadline;
import compal.model.tasks.Event;
import compal.model.tasks.Task;
import compal.model.tasks.TaskList;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static compal.logic.command.CommandTestUtil.assertCommandFailure;
import static compal.logic.command.DoneCommand.MESSAGE_INVALID_ID;
import static compal.logic.command.DoneCommand.MESSAGE_INVALID_INPUT;
import static compal.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static compal.model.tasks.Task.Priority.high;
import static compal.model.tasks.Task.Priority.low;
import static compal.model.tasks.Task.Priority.medium;

//@@author SholihinK
class DoneCommandTest {

    private ArrayList<Task> taskArrListMain = new ArrayList<>();
    private ArrayList<Task> taskArrListDup = new ArrayList<>();

    private TaskList taskListMain = new TaskList();
    private TaskList taskListDup = new TaskList();

    @org.junit.jupiter.api.BeforeEach
    void setUp() {
        Event event1 = new Event("Event 1", medium, "01/10/2019", "1400", "1500");
        Event event2 = new Event("Event 2", low, "01/10/2019", "1400", "1500");

        Deadline deadline1 = new Deadline("Deadline 1", high, "01/10/2019", "1500");

        taskArrListMain.add(event1);
        taskArrListMain.add(event2);
        taskArrListMain.add(deadline1);

        taskArrListDup.add(event1);
        taskArrListDup.add(event2);
        taskArrListDup.add(deadline1);

        this.taskListMain.setArrList(taskArrListMain);
        this.taskListDup.setArrList(taskArrListDup);
    }

    @Test
    void execute_invalidTaskID_validStatus_fail() {
        DoneCommand doneCommand = new DoneCommand(1243134214, "y");
        assertCommandFailure(doneCommand, taskListMain, MESSAGE_INVALID_ID);
    }

    @Test
    void execute_validTaskID_invalidStatus_fail() {
        DoneCommand doneCommand = new DoneCommand(0, "wrong status here");
        assertCommandFailure(doneCommand, taskListMain, MESSAGE_INVALID_INPUT);
    }

    @Test
    void execute_validIdAndStatus_undone_success() throws CommandException {
        DoneParser parser = new DoneParser();
        int id = taskListMain.getArrList().get(0).getId();
        assertParseSuccess(parser, "/id " + id + " /status n",
            new DoneCommand(0, "n").commandExecute(taskListDup), taskListMain);
    }

    @Test
    void execute_validIdAndStatus_done_success() throws CommandException {
        DoneParser parser = new DoneParser();
        int id = taskListMain.getArrList().get(0).getId();
        assertParseSuccess(parser, "/id " + id + " /status y",
            new DoneCommand(0, "y").commandExecute(taskListDup), taskListMain);
    }
}