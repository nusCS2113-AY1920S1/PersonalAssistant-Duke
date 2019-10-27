package compal.logic.parser;

import compal.commons.CompalUtils;
import compal.logic.command.ViewCommand;
import compal.logic.command.exceptions.CommandException;
import compal.logic.parser.exceptions.ParserException;
import compal.model.tasks.Deadline;
import compal.model.tasks.Event;
import compal.model.tasks.Task;
import compal.model.tasks.TaskList;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Calendar;

import static compal.logic.parser.CommandParser.MESSAGE_INVALID_DATE_FORMAT;
import static compal.logic.parser.CommandParser.MESSAGE_MISSING_INPUT;
import static compal.logic.parser.CommandParser.MESSAGE_MISSING_TOKEN;
import static compal.logic.parser.ViewCommandParser.MESSAGE_INVALID_PARAM;


import static compal.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static compal.logic.parser.CommandParserTestUtil.assertParseFailure;
import static org.junit.jupiter.api.Assertions.assertEquals;

//@@author SholihinK
class ViewCommandParserTest {

    private ViewCommandParser parser = new ViewCommandParser();

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
    void parse_invalidDate_failure() {
        assertParseFailure(parser, "day /date 30/02/2019", MESSAGE_INVALID_DATE_FORMAT);
    }

    @Test
    void parse_invalidParam_failure() {
        assertParseFailure(parser, "years /date 29/02/2020", MESSAGE_INVALID_PARAM);
        assertParseFailure(parser, "months /date 29/02/2020", MESSAGE_INVALID_PARAM);
    }

    @Test
    void parse_missingDate_failure() {
        assertParseFailure(parser, "day /date", MESSAGE_MISSING_INPUT);
    }

    @Test
    void parse_missingToken_failure() {
        assertParseFailure(parser, "", MESSAGE_MISSING_TOKEN);
    }

    @Test
    void parse_CorrectDate_success() throws ParserException {
        boolean execute = parser.isDateValid("10/10/2010");
        assertEquals(execute, true);
    }

    @Test
    void parse_view_success() throws CommandException {
        String cmdParam = "week /date 23/10/2019";
        assertParseSuccess(parser, cmdParam,
            new ViewCommand("week", "23/10/2019").commandExecute(taskListMain), taskListMain);
    }

    @Test
    void parse_withoutDate_success() throws CommandException {
        String cmdParam = "week";
        Calendar currentDay = Calendar.getInstance();
        String finalDate = CompalUtils.dateToString(currentDay.getTime());
        assertParseSuccess(parser, cmdParam,
            new ViewCommand("week", finalDate).commandExecute(taskListMain), taskListMain);
    }

    @Test
    void parse_withType_success() throws CommandException {
        String cmdParam = "week /type deadline";
        Calendar currentDay = Calendar.getInstance();
        String finalDate = CompalUtils.dateToString(currentDay.getTime());
        assertParseSuccess(parser, cmdParam,
            new ViewCommand("week", finalDate, "deadline")
                .commandExecute(taskListMain), taskListMain);
    }

    @Test
    void parse_fullDeadline_success() throws CommandException {
        String cmdParam = "week /date 01/10/2019 /type deadline";
        assertParseSuccess(parser, cmdParam,
            new ViewCommand("week", "01/10/2019", "deadline")
                .commandExecute(taskListMain), taskListMain);
    }

    @Test
    void parse_fullEvent_success() throws CommandException {
        String cmdParam = "week /date 01/10/2019 /type event";
        assertParseSuccess(parser, cmdParam,
            new ViewCommand("week", "01/10/2019", "event")
                .commandExecute(taskListMain), taskListMain);
    }



}