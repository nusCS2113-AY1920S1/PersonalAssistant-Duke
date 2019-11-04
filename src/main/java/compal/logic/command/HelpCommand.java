package compal.logic.command;

import compal.commons.LogUtils;
import compal.logic.command.exceptions.CommandException;
import compal.model.tasks.TaskList;

import java.util.logging.Logger;

//@@author LTPZ
public class HelpCommand extends Command {

    public static final String MESSAGE_USAGE = "help\n\t"
            + "Format:1. help <command>\n\t"
            + "2. help\n\n\t"
            + "Note: content in \"<>\": need to be fulfilled by the user\n\n\t"
            + "This command will tell you how to use command\n"
            + "Examples:\n\t"
            + "help\n\t\t"
            + "show all list of commands\n\t"
            + "help deadline\n\t\t"
            + "show how to use deadline command";
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
    public static final String HELP_STRING = "Seems like you need some help? Check these commands:\n\n\t"
            + "bye: exit the program\n\t"
            + "deadline: add deadline type tasks\n\t"
            + "delete: delete one task\n\t"
            + "edit: edit one task\n\t"
            + "event: add event type tasks\n\t"
            + "export: export your own timetable\n\t"
            + "find: search for tasks containing key word\n\t"
            + "findfreeslot: find a free slot with existing timetable\n\t"
            + "help: learn how to use all commands\n\t"
            + "import: import your own timetable\n\t"
            + "list: list all the task stored in COMPal\n\t"
            + "set-reminder: set reminder for a task\n\t"
            + "view: get the daily/weekly/monthly view of timetable\n\t"
            + "view-reminder: get all tasks with reminder\n\n"
            + "if you want to know how to use any of them\n"
            + "you can type \"help <name of command>\"  for further information!\n"
            + "e.g. \"help findfreeslot\", \"help deadline\"\n"
            + "Have a nice day with COMPal! :)";
    public static final String WRONG_COMMAND = "Sorry, the description of the command is incorrect\n"
            + "please check the following again:\n\n";

    private String type;
    private String command;

    private static final Logger logger = LogUtils.getLogger(HelpCommand.class);

    public HelpCommand(String restOfInput) {
        this.type = restOfInput.split("_", 2)[0];
        this.command = restOfInput.split("_", 2)[1];
    }

    @Override
    public CommandResult commandExecute(TaskList task) throws CommandException {
        logger.info("Executing help command");
        if (!"help".equals(type))  {
            return new CommandResult(HELP_STRING, false);
        } else {
            switch (command) {
            case "":
                return new CommandResult(HELP_STRING, false);
            case CMD_EXIT:
                return new CommandResult(ByeCommand.MESSAGE_USAGE, false);
            case CMD_VIEW:
                return new CommandResult(ViewCommand.MESSAGE_USAGE, false);
            case CMD_SET_REMINDER:
                return new CommandResult(SetReminderCommand.MESSAGE_USAGE, false);
            case CMD_VIEW_REMINDER:
                return new CommandResult(ViewReminderCommand.MESSAGE_USAGE, false);
            case CMD_FIND_FREE_SLOT:
                return new CommandResult(FindFreeSlotCommand.MESSAGE_USAGE, false);
            case CMD_DEADLINE:
                return new CommandResult(DeadlineCommand.MESSAGE_USAGE, false);
            case CMD_DONE:
                return new CommandResult(DoneCommand.MESSAGE_USAGE, false);
            case CMD_FIND:
                return new CommandResult(FindCommand.MESSAGE_USAGE, false);
            case CMD_EVENT:
                return new CommandResult(EventCommand.MESSAGE_USAGE, false);
            case CMD_EDIT:
                return new CommandResult(EditCommand.MESSAGE_USAGE, false);
            case CMD_HELP:
                return new CommandResult(HelpCommand.MESSAGE_USAGE, false);
            case CMD_LIST:
                return new CommandResult(ListCommand.MESSAGE_USAGE, false);
            case CMD_DELETE:
                return new CommandResult(DeleteCommand.MESSAGE_USAGE, false);
            case CMD_IMPORT:
                return new CommandResult(ImportCommand.MESSAGE_USAGE, false);
            case CMD_EXPORT:
                return new CommandResult(ExportCommand.MESSAGE_USAGE, false);
            default:
                return new
                        CommandResult(WRONG_COMMAND + HelpCommand.MESSAGE_USAGE, false);
            }
        }
    }
}
