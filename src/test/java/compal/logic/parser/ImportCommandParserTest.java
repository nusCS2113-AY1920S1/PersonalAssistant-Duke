package compal.logic.parser;

import compal.logic.command.ImportCommand;
import compal.logic.command.exceptions.CommandException;
import compal.model.tasks.Deadline;
import compal.model.tasks.Event;
import compal.model.tasks.Task;
import compal.model.tasks.TaskList;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static compal.logic.parser.CommandParserTestUtil.assertParseFailure;
import static compal.logic.parser.CommandParserTestUtil.assertParseSuccess;

class ImportCommandParserTest {

    private ImportCommandParser parser = new ImportCommandParser();
    private ArrayList<Task> taskArrListMain = new ArrayList<>();
    private TaskList taskListMain = new TaskList();

    @org.junit.jupiter.api.BeforeEach
    void setUp() {
        Event event1 = new Event("CS2105 Lecture", Task.Priority.medium, "01/10/2019", "01/10/2019", "1400", "1500");
        event1.markAsDone();
        Event event2 = new Event("CS2106 Tut", Task.Priority.low, "02/10/2019", "02/10/2019", "1400", "1500");
        Event event3 = new Event("CS2113T Lab", Task.Priority.low, "03/10/2019", "03/10/2019", "1400", "1500");
        Event event4 = new Event("CS2101 Sect", Task.Priority.low, "03/10/2019", "03/10/2019", "1400", "1500");
        Event event5 = new Event("CS2101 Sect", Task.Priority.low, "05/10/2019", "05/10/2019", "1400", "1500");
        Event event6 = new Event("CS2101 rt", Task.Priority.low, "06/10/2019", "06/10/2019", "1400", "1500");

        taskArrListMain.add(event1);
        taskArrListMain.add(event2);
        taskArrListMain.add(event3);
        taskArrListMain.add(event4);
        taskArrListMain.add(event5);
        taskArrListMain.add(event6);

        Deadline deadline1 = new Deadline("Deadline 1", Task.Priority.high, "03/10/2019", "1500");
        deadline1.markAsDone();
        Deadline deadline2 = new Deadline("CS2106 Assignment", Task.Priority.low, "04/10/2019", "1500");
        Deadline deadline3 = new Deadline("Deadline 3", Task.Priority.high, "05/10/2019", "1500");
        Deadline deadline4 = new Deadline("Deadline 4", Task.Priority.high, "05/10/2019", "1500");

        taskArrListMain.add(deadline1);
        taskArrListMain.add(deadline2);
        taskArrListMain.add(deadline3);
        taskArrListMain.add(deadline4);

        this.taskListMain.setArrList(taskArrListMain);
    }

    @Test
    void parse_invalidFileName_fail() {
        assertParseFailure(parser, "/*\\0", CommandParser.MESSAGE_INVALID_FILE_NAME_FORMAT);
    }

    @Test
    void parse_export_success() throws CommandException {
        assertParseSuccess(parser, "",
            new ImportCommand("COMPalCalender").commandExecute(taskListMain), taskListMain);
    }
}