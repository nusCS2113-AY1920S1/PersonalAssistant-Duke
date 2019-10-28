package compal.logic.parser;

import compal.logic.command.DeleteCommand;
import compal.logic.command.exceptions.CommandException;
import compal.model.tasks.Deadline;
import compal.model.tasks.Event;
import compal.model.tasks.Task;
import compal.model.tasks.TaskList;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static compal.logic.parser.CommandParserTestUtil.assertParseFailure;
import static compal.logic.parser.CommandParserTestUtil.assertParseSuccess;

//@@author yueyeah
public class DeleteCommandParserTest {
    private DeleteCommandParser parser = new DeleteCommandParser();

    private ArrayList<Task> taskArrListMain = new ArrayList<>();
    private ArrayList<Task> taskArrListDup = new ArrayList<>();

    private TaskList taskListMain = new TaskList();
    private TaskList taskListDup = new TaskList();

    @BeforeEach
    void setUp() {
        this.taskListMain.setArrList(taskArrListMain);
        this.taskListDup.setArrList(taskArrListDup);
        Event testEvent = new Event("Event 1", Task.Priority.medium, "01/10/2019", "01/10/2019", "1400", "1500");
        Deadline testDeadline = new Deadline("Deadline 1", Task.Priority.high, "01/10/2019", "1500");
        this.taskListDup.addTask(testEvent);
        this.taskListDup.addTask(testDeadline);
        this.taskListMain.addTask(testEvent);
        this.taskListMain.addTask(testDeadline);
    }

    @Test
    void parse_missingIdToken_failure() {
        assertParseFailure(parser, "0", CommandParser.MESSAGE_MISSING_ID_ARG);
    }

    @Test
    void parse_validIdTokenEvent_success() throws CommandException {
        DeleteCommand expectedDeleteCommand = new DeleteCommand(0);
        assertParseSuccess(parser, "/id 0", expectedDeleteCommand.commandExecute(taskListDup), taskListMain);
    }

    @Test
    void parse_validIdTokenDeadline_success() throws CommandException {
        DeleteCommand expectedDeleteCommand = new DeleteCommand(1);
        assertParseSuccess(parser, "/id 1", expectedDeleteCommand.commandExecute(taskListDup), taskListMain);
    }
}
