package compal.logic.parser;


import compal.model.tasks.TaskList;
import compal.storage.TaskStorageManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static compal.logic.parser.CommandParser.MESSAGE_MISSING_INPUT;
import static compal.logic.parser.CommandParser.MESSAGE_MISSING_TOKEN;

import static compal.logic.parser.CommandParserTestUtil.assertParseFailure;

class DoneParserTest {
    private DoneParser parser = new DoneParser();
    private TaskList taskList = new TaskList();


    @BeforeEach
    void setUp() {
        TaskStorageManager taskStorageManager = new TaskStorageManager();
        //ArrayList<Task> taskArrList = new ArrayList<>(taskStorageManager.loadData());
        //taskList.setArrList(taskArrList);

    }

    @Test
    void parse_invalidToken_failure() {
        assertParseFailure(parser, "-1", MESSAGE_MISSING_TOKEN);
    }

    @Test
    void parse_validToken_EmptyInput_failure() {
        assertParseFailure(parser, "/id", MESSAGE_MISSING_INPUT);
    }

    /*@Test
    void parse_validTokenAndID() throws CommandException {
        taskList.addTask(new Deadline("CS2113T Assignment", high, "20/10/2019", "1700"));
        taskList.addTask(new Deadline("CS2113T Assignment", high, "20/10/2019", "1700"));
        int id = taskList.getArrList().get(0).getId();
        assertParseSuccess(parser, "/id " + id, new DoneCommand(id).commandExecute(taskList), taskList);
    }*/

}