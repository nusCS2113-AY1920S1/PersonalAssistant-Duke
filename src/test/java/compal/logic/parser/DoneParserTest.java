package compal.logic.parser;


import compal.logic.command.DoneCommand;
import compal.logic.command.exceptions.CommandException;
import compal.model.tasks.Task;
import compal.model.tasks.TaskList;
import compal.storage.TaskStorageManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static compal.logic.parser.CommandParser.MESSAGE_MISSING_INPUT;
import static compal.logic.parser.CommandParser.MESSAGE_MISSING_TOKEN;

import static compal.logic.parser.CommandParserTestUtil.assertParseFailure;
import static compal.logic.parser.CommandParserTestUtil.assertParseSuccess;


class DoneParserTest {
    private DoneParser parser = new DoneParser();
    private TaskList taskList = new TaskList();


    @BeforeEach
    void setUp() {
        TaskStorageManager taskStorageManager = new TaskStorageManager();
        ArrayList<Task> taskArrList = new ArrayList<>(taskStorageManager.loadData());
        taskList.setArrList(taskArrList);
    }

    @Test
    void parse_invalidToken_failure() {
        assertParseFailure(parser, "-1", MESSAGE_MISSING_TOKEN);
    }

    @Test
    void parse_validToken_EmptyInput_failure() {
        assertParseFailure(parser, "/id", MESSAGE_MISSING_INPUT);
    }

    @Test
    void parse_validTokenAndID() throws CommandException {
        assertParseSuccess(parser, "/id 1", new DoneCommand(1).commandExecute(taskList), taskList);
    }

}