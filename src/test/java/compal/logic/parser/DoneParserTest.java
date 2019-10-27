package compal.logic.parser;


import compal.logic.command.DoneCommand;
import compal.logic.command.exceptions.CommandException;
import compal.model.tasks.Deadline;
import compal.model.tasks.Event;
import compal.model.tasks.Task;
import compal.model.tasks.TaskList;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static compal.logic.parser.CommandParserTestUtil.assertParseFailure;
import static compal.logic.parser.CommandParserTestUtil.assertParseSuccess;


//@@author SholihinK
class DoneParserTest {
    private DoneParser parser = new DoneParser();

    private ArrayList<Task> taskArrListMain = new ArrayList<>();
    private ArrayList<Task> taskArrListDup = new ArrayList<>();

    private TaskList taskListMain = new TaskList();
    private TaskList taskListDup = new TaskList();

    @org.junit.jupiter.api.BeforeEach
    void setUp() {
        Event event1 = new Event("Event 1", Task.Priority.medium, "01/10/2019", "01/10/2019", "1400", "1500");
        Event event2 = new Event("Event 2", Task.Priority.low, "01/10/2019", "01/10/2019", "1400", "1500");

        Deadline deadline1 = new Deadline("Deadline 1", Task.Priority.high, "01/10/2019", "1500");

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
    void parse_missingIdToken_failure() {
        assertParseFailure(parser, "-1", CommandParser.MESSAGE_MISSING_ID_ARG);
    }

    @Test
    void parse_missingStatus_failure() {
        assertParseFailure(parser, "/id 1", CommandParser.MESSAGE_MISSING_STATUS_ARG);
    }

    @Test
    void parse_validTokenAndID_done_success() throws CommandException {
        int id = taskListMain.getArrList().get(0).getId();
        assertParseSuccess(parser, "/id " + id + " /status y",
            new DoneCommand(0, "y").commandExecute(taskListDup), taskListMain);
    }

    @Test
    void parse_validTokenAndID_undone_success() throws CommandException {
        int id = taskListMain.getArrList().get(0).getId();
        assertParseSuccess(parser, "/id " + id + " /status n",
            new DoneCommand(0, "n").commandExecute(taskListDup), taskListMain);
    }

}