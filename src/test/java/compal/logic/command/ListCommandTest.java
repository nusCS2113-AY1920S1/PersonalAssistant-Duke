package compal.logic.command;

import compal.logic.command.exceptions.CommandException;
import compal.logic.parser.ListCommandParser;
import compal.model.tasks.Deadline;
import compal.model.tasks.Event;
import compal.model.tasks.Task;
import compal.model.tasks.TaskList;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import static compal.logic.parser.CommandParserTestUtil.assertParseSuccess;

//@@author SholihinK
class ListCommandTest {
    private ArrayList<Task> taskArrListMain = new ArrayList<>();
    private ArrayList<Task> taskArrListDup = new ArrayList<>();
    private ArrayList<Task> taskArrListEmpty = new ArrayList<>();

    private TaskList taskListMain = new TaskList();
    private TaskList taskListDup = new TaskList();
    private TaskList taskListEmpty = new TaskList();

    @org.junit.jupiter.api.BeforeEach
    void setUp() {
        Event event1 = new Event("Event 1", Task.Priority.medium, "01/10/2019", "1400", "1500");
        Event event2 = new Event("Event 2", Task.Priority.low, "01/10/2019", "1400", "1500");

        Deadline deadline1 = new Deadline("Deadline 1", Task.Priority.high, "01/10/2019", "1500");

        taskArrListMain.add(event1);
        taskArrListMain.add(event2);
        taskArrListMain.add(deadline1);

        taskArrListDup.add(event1);
        taskArrListDup.add(event2);
        taskArrListDup.add(deadline1);

        this.taskListMain.setArrList(taskArrListMain);
        this.taskListDup.setArrList(taskArrListDup);
        this.taskListEmpty.setArrList(taskArrListEmpty);
    }

    @Test
    void execute_list_withoutType_successs() {
        ListCommandParser parser = new ListCommandParser();
        assertParseSuccess(parser, "",
            new ListCommand("").commandExecute(taskListDup), taskListMain);
    }

    @Test
    void execute_listDeadline_success() {
        ListCommandParser parser = new ListCommandParser();
        assertParseSuccess(parser, "/type deadline",
            new ListCommand("deadline").commandExecute(taskListDup), taskListMain);
    }

    @Test
    void execute_listEvent_success() {
        ListCommandParser parser = new ListCommandParser();
        assertParseSuccess(parser, "/type event",
            new ListCommand("event").commandExecute(taskListDup), taskListMain);
    }

    @Test
    void execute_emptyList_success() {
        ListCommandParser parser = new ListCommandParser();
        assertParseSuccess(parser, "/type event",
            new ListCommand("event").commandExecute(taskListEmpty), taskListEmpty);
    }
}