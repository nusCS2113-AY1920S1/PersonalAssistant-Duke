package compal.logic.parser;

import compal.logic.command.ListCommand;
import compal.logic.command.exceptions.CommandException;
import compal.model.tasks.Deadline;
import compal.model.tasks.Event;
import compal.model.tasks.Task;
import compal.model.tasks.TaskList;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static compal.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static compal.model.tasks.Task.Priority.high;
import static compal.model.tasks.Task.Priority.medium;
import static compal.model.tasks.Task.Priority.low;

//@@author SholihinK
class ListCommandParserTest {
    private ListCommandParser parser = new ListCommandParser();
    private ArrayList<Task> taskArrListMain = new ArrayList<>();
    private ArrayList<Task> taskArrListDup = new ArrayList<>();

    private TaskList taskListMain = new TaskList();
    private TaskList taskListDup = new TaskList();

    @org.junit.jupiter.api.BeforeEach
    void setUp() {
        Event event1 = new Event("Event 1", medium, "01/10/2019", "01/10/2019", "1400", "1500");
        taskArrListMain.add(event1);
        taskArrListDup.add(event1);

        Deadline deadline1 = new Deadline("Deadline 1", high, "01/10/2019", "1500");
        taskArrListMain.add(deadline1);
        taskArrListDup.add(deadline1);

        this.taskListMain.setArrList(taskArrListMain);
        this.taskListDup.setArrList(taskArrListDup);
    }

    @Test
    void parse_emptyType_success() {
        assertParseSuccess(parser, "",
            new ListCommand("").commandExecute(taskListDup), taskListMain);
    }

    @Test
    void parse_withType_success() {
        assertParseSuccess(parser, "/type event",
            new ListCommand("event").commandExecute(taskListDup), taskListMain);
    }
}