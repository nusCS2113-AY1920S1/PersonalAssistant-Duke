package compal.logic.parser;

import compal.logic.command.FindCommand;
import compal.logic.command.ViewCommand;
import compal.logic.command.exceptions.CommandException;
import compal.logic.parser.exceptions.ParserException;
import compal.model.tasks.Task;
import compal.model.tasks.TaskList;
import compal.storage.TaskStorageManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static compal.logic.parser.CommandParserTestUtil.assertParseFailure;
import static compal.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static compal.logic.parser.ViewCommandParser.MESSAGE_INVALID_PARAM;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class FindCommandParserTest {
    private FindCommandParser parser = new FindCommandParser();
    private TaskList taskList = new TaskList();



    @BeforeEach
    void setUp() {
        TaskStorageManager taskStorageManager = new TaskStorageManager();
        ArrayList<Task> taskArrList = new ArrayList<>(taskStorageManager.loadData());
        taskList.setArrList(taskArrList);
    }

    @Test
    void parse_correct() throws CommandException {
        assertParseSuccess(parser,"", new FindCommand("").commandExecute(taskList),taskList);
    }



}
