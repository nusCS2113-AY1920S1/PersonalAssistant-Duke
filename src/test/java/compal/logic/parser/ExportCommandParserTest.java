package compal.logic.parser;

import compal.logic.command.ExportCommand;
import compal.logic.command.exceptions.CommandException;
import compal.model.tasks.Deadline;
import compal.model.tasks.Event;
import compal.model.tasks.Task;
import compal.model.tasks.TaskList;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.util.ArrayList;

import static compal.logic.parser.CommandParserTestUtil.assertParseFailure;
import static compal.logic.parser.CommandParserTestUtil.assertParseSuccess;

//@@author SholihinK
class ExportCommandParserTest {
    private ExportCommandParser parser = new ExportCommandParser();
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
    void parse_invalidParam_fail() {
        assertParseFailure(parser, "", CommandParser.MESSAGE_MISSING_FILE_NAME_ARG);
        assertParseFailure(parser, "/file-name", CommandParser.MESSAGE_MISSING_FILE_NAME);
    }

    @Test
    void parse_export_success() throws CommandException {
        assertParseSuccess(parser, "/file-name COMPalCalender",
            new ExportCommand("COMPalCalender").commandExecute(taskListMain), taskListMain);
        File file = new File("COMPalCalender.ics");
        file.delete();
    }

}