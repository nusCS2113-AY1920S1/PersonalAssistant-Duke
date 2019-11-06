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
    public static final String CMD_EXIT = "bye";
    public static final String CMD_LIST = "list";
    public static final String CMD_DONE = "done";
    public static final String CMD_DELETE = "delete";
    public static final String CMD_EVENT = "event";
    public static final String CMD_DEADLINE = "deadline";
    public static final String CMD_VIEW = "view";
    public static final String CMD_FIND = "find";
    public static final String CMD_SET_REMINDER = "set-reminder";
    public static final String CMD_VIEW_REMINDER = "view-reminder";
    public static final String CMD_IMPORT = "import";
    public static final String CMD_EXPORT = "export";
    public static final String CMD_HELP = "help";
    public static final String CMD_FIND_FREE_SLOT = "findfreeslot";
    public static final String CMD_EDIT = "edit";



    @BeforeEach
    void setUp() {
        TaskStorageManager taskStorageManager = new TaskStorageManager();
        ArrayList<Task> taskArrList = new ArrayList<>(taskStorageManager.loadData());
        taskList.setArrList(taskArrList);
    }

    @Test
    void parse_correct() throws CommandException {
        assertParseSuccess(parser,"trash_",
                new HelpCommand("trash_").commandExecute(taskList),taskList);
    }

    @Test
    void parse_correct_cmd_exit() throws CommandException {
        assertParseSuccess(parser,"help_" + CMD_EXIT,
                new HelpCommand("help_" + CMD_EXIT).commandExecute(taskList),taskList);
    }

    @Test
    void parse_correct_help() throws CommandException {
        assertParseSuccess(parser,"help_",
                new HelpCommand("help_").commandExecute(taskList),taskList);
    }

    @Test
    void parse_correct_cmd_list() throws CommandException {
        assertParseSuccess(parser,"help_" + CMD_LIST,
                new HelpCommand("help_" + CMD_LIST).commandExecute(taskList),taskList);
    }

    @Test
    void parse_correct_cmd_done() throws CommandException {
        assertParseSuccess(parser,"help_" + CMD_DONE,
                new HelpCommand("help_" + CMD_DONE).commandExecute(taskList),taskList);
    }

    @Test
    void parse_correct_cmd_delete() throws CommandException {
        assertParseSuccess(parser,"help_" + CMD_DELETE,
                new HelpCommand("help_" + CMD_DELETE).commandExecute(taskList),taskList);
    }

    @Test
    void parse_correct_cmd_event() throws CommandException {
        assertParseSuccess(parser,"help_" + CMD_EVENT,
                new HelpCommand("help_" + CMD_EVENT).commandExecute(taskList),taskList);
    }

    @Test
    void parse_correct_cmd_import() throws CommandException {
        assertParseSuccess(parser,"help_" + CMD_IMPORT,
                new HelpCommand("help_" + CMD_IMPORT).commandExecute(taskList),taskList);
    }

    @Test
    void parse_correct_cmd_export() throws CommandException {
        assertParseSuccess(parser,"help_" + CMD_EXPORT,
                new HelpCommand("help_" + CMD_EXPORT).commandExecute(taskList),taskList);
    }

    @Test
    void parse_correct_cmd_view() throws CommandException {
        assertParseSuccess(parser,"help_" + CMD_VIEW,
                new HelpCommand("help_" + CMD_VIEW).commandExecute(taskList),taskList);
    }

    @Test
    void parse_correct_cmd_find() throws CommandException {
        assertParseSuccess(parser,"help_" + CMD_FIND,
                new HelpCommand("help_" + CMD_FIND).commandExecute(taskList),taskList);
    }

    @Test
    void parse_correct_cmd_set_reminder() throws CommandException {
        assertParseSuccess(parser,"help_" + CMD_SET_REMINDER,
                new HelpCommand("help_" + CMD_SET_REMINDER).commandExecute(taskList),taskList);
    }

    @Test
    void parse_correct_cmd_view_reminder() throws CommandException {
        assertParseSuccess(parser,"help_" + CMD_VIEW_REMINDER,
                new HelpCommand("help_" + CMD_VIEW_REMINDER).commandExecute(taskList),taskList);
    }

    @Test
    void parse_correct_cmd_deadline() throws CommandException {
        assertParseSuccess(parser,"help_" + CMD_DEADLINE,
                new HelpCommand("help_" + CMD_DEADLINE).commandExecute(taskList),taskList);
    }

    @Test
    void parse_correct_cmd_help() throws CommandException {
        assertParseSuccess(parser,"help_" + CMD_HELP,
                new HelpCommand("help_" + CMD_HELP).commandExecute(taskList),taskList);
    }

    @Test
    void parse_correct_cmd_find_free_slot() throws CommandException {
        assertParseSuccess(parser,"help_" + CMD_FIND_FREE_SLOT,
                new HelpCommand("help_" + CMD_FIND_FREE_SLOT).commandExecute(taskList),taskList);
    }

    @Test
    void parse_correct_cmd_edit() throws CommandException {
        assertParseSuccess(parser,"help_" + CMD_EDIT,
                new HelpCommand("help_" + CMD_EDIT).commandExecute(taskList),taskList);
    }
}
