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

//@@author LTPZ
public class HelpCommandParserTest {
    private HelpCommandParser parser = new HelpCommandParser();
    private TaskList taskList = new TaskList();
    private static final String CMD_EXIT = "bye";
    private static final String CMD_LIST = "list";
    private static final String CMD_DONE = "done";
    private static final String CMD_DELETE = "delete";
    private static final String CMD_EVENT = "event";
    private static final String CMD_DEADLINE = "deadline";
    private static final String CMD_RECUR_TASK = "recurtask";
    private static final String CMD_VIEW = "view";
    private static final String CMD_FIND = "find";
    private static final String CMD_SET_REMINDER = "set-reminder";
    private static final String CMD_VIEW_REMINDER = "view-reminder";
    private static final String CMD_HELP = "help";
    private static final String CMD_FIND_FREE_SLOT = "findfreeslot";
    private static final String CMD_EDIT = "edit";



    @BeforeEach
    void setUp() {
        TaskStorageManager taskStorageManager = new TaskStorageManager();
        ArrayList<Task> taskArrList = new ArrayList<>(taskStorageManager.loadData());
        taskList.setArrList(taskArrList);
    }

    @Test
    void parse_correct() throws CommandException {
        assertParseSuccess(parser,"", new HelpCommand("").commandExecute(taskList),taskList);
    }

    @Test
    void parse_correct_cmd_exit() throws CommandException {
        assertParseSuccess(parser,CMD_EXIT, new HelpCommand(CMD_EXIT).commandExecute(taskList),taskList);
    }

    @Test
    void parse_correct_cmd_list() throws CommandException {
        assertParseSuccess(parser,CMD_LIST, new HelpCommand(CMD_LIST).commandExecute(taskList),taskList);
    }

    @Test
    void parse_correct_cmd_done() throws CommandException {
        assertParseSuccess(parser,CMD_DONE, new HelpCommand(CMD_DONE).commandExecute(taskList),taskList);
    }

    @Test
    void parse_correct_cmd_delete() throws CommandException {
        assertParseSuccess(parser,CMD_DELETE, new HelpCommand(CMD_DELETE).commandExecute(taskList),taskList);
    }

    @Test
    void parse_correct_cmd_event() throws CommandException {
        assertParseSuccess(parser,CMD_EVENT, new HelpCommand(CMD_EVENT).commandExecute(taskList),taskList);
    }

    @Test
    void parse_correct_cmd_recur_task() throws CommandException {
        assertParseSuccess(parser,CMD_RECUR_TASK, new HelpCommand(CMD_RECUR_TASK).commandExecute(taskList),taskList);
    }

    @Test
    void parse_correct_cmd_view() throws CommandException {
        assertParseSuccess(parser,CMD_VIEW, new HelpCommand(CMD_VIEW).commandExecute(taskList),taskList);
    }

    @Test
    void parse_correct_cmd_find() throws CommandException {
        assertParseSuccess(parser,CMD_FIND, new HelpCommand(CMD_FIND).commandExecute(taskList),taskList);
    }

    @Test
    void parse_correct_cmd_set_reminder() throws CommandException {
        assertParseSuccess(parser,CMD_SET_REMINDER,
                new HelpCommand(CMD_SET_REMINDER).commandExecute(taskList),taskList);
    }

    @Test
    void parse_correct_cmd_view_reminder() throws CommandException {
        assertParseSuccess(parser,CMD_VIEW_REMINDER,
                new HelpCommand(CMD_VIEW_REMINDER).commandExecute(taskList),taskList);
    }

    @Test
    void parse_correct_cmd_deadline() throws CommandException {
        assertParseSuccess(parser,CMD_DEADLINE, new HelpCommand(CMD_DEADLINE).commandExecute(taskList),taskList);
    }

    @Test
    void parse_correct_cmd_help() throws CommandException {
        assertParseSuccess(parser,CMD_HELP, new HelpCommand(CMD_HELP).commandExecute(taskList),taskList);
    }

    @Test
    void parse_correct_cmd_find_free_slot() throws CommandException {
        assertParseSuccess(parser,CMD_FIND_FREE_SLOT,
                new HelpCommand(CMD_FIND_FREE_SLOT).commandExecute(taskList),taskList);
    }

    @Test
    void parse_correct_cmd_edit() throws CommandException {
        assertParseSuccess(parser,CMD_EDIT, new HelpCommand(CMD_EDIT).commandExecute(taskList),taskList);
    }
}
