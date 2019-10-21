package compal.logic.parser;

import compal.logic.command.ViewCommand;
import compal.logic.command.exceptions.CommandException;
import compal.logic.parser.exceptions.ParserException;
import compal.model.tasks.Task;
import compal.model.tasks.TaskList;
import compal.storage.TaskStorageManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static compal.logic.parser.CommandParser.MESSAGE_INVALID_DATE_FORMAT;
import static compal.logic.parser.CommandParser.MESSAGE_MISSING_INPUT;
import static compal.logic.parser.CommandParser.MESSAGE_MISSING_TOKEN;
import static compal.logic.parser.ViewCommandParser.MESSAGE_INVALID_PARAM;


import static compal.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static compal.logic.parser.CommandParserTestUtil.assertParseFailure;
import static org.junit.jupiter.api.Assertions.assertEquals;

class ViewCommandParserTest {
    private ViewCommandParser parser = new ViewCommandParser();
    private TaskList taskList = new TaskList();

    @BeforeEach
    void setUp() {
        TaskStorageManager taskStorageManager = new TaskStorageManager();
        ArrayList<Task> taskArrList = new ArrayList<>(taskStorageManager.loadData());
        taskList.setArrList(taskArrList);
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
    void parse_executeView_success() throws CommandException {
        String cmdParam = "week /date 23/10/2019";
        assertParseSuccess(parser, cmdParam,
            new ViewCommand("week","23/10/2019").commandExecute(taskList), taskList);
    }
}