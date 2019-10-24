package compal.logic.parser;

import compal.logic.command.HelpCommand;
import compal.logic.command.exceptions.CommandException;
import compal.model.tasks.Task;
import compal.model.tasks.TaskList;
import compal.storage.TaskStorageManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static compal.logic.parser.CommandParserTestUtil.assertParseSuccess;

public class HelpCommandParserTest {
    private HelpCommandParser parser = new HelpCommandParser();
    private TaskList taskList = new TaskList();

    @BeforeEach
    public void setUp() {
        TaskStorageManager taskStorageManager = new TaskStorageManager();
        ArrayList<Task> taskArrList = new ArrayList<>(taskStorageManager.loadData());
        taskList.setArrList(taskArrList);
    }

    @Test
    void parse_correct() throws CommandException {
        assertParseSuccess(parser,"", new HelpCommand("").commandExecute(taskList),taskList);
    }
}
