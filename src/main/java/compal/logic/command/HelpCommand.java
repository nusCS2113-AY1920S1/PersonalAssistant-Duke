package compal.logic.command;

import compal.logic.command.exceptions.CommandException;
import compal.model.tasks.TaskList;

//@@author LTPZ
public class HelpCommand extends Command {

    public static final String MESSAGE_USAGE = "help\n\t"
            + "Format: help /command <command>\n\n\t"
            + "Note: content in \"<>\": need to be fulfilled by the user\n\n\t"
            + "This command will tell you how to use command\n"
            + "Examples:\n\t"
            + "help /command deadline\n\t\t"
            + "show how to use deadline command";
    public static final String CMD_EXIT = "bye";
    public static final String CMD_CLEAR = "clear";
    public static final String CMD_DONE = "done";
    public static final String CMD_DELETE = "delete";
    public static final String CMD_EVENT = "event";
    public static final String CMD_DEADLINE = "deadline";
    public static final String CMD_RECUR_TASK = "recurtask";
    public static final String CMD_VIEW = "view";
    public static final String CMD_FIND = "find";
    public static final String CMD_SET_REMINDER = "set-reminder";
    public static final String CMD_VIEW_REMINDER = "view-reminder";
    public static final String CMD_LECT = "lect";
    public static final String CMD_TUT = "tut";
    public static final String CMD_SECT = "sect";
    public static final String CMD_LAB = "lab";
    public static final String CMD_HELP = "help";
    public static final String CMD_FIND_FREE_SLOT = "findfreeslot";
    public static final String CMD_EDIT = "edit";
    public static final String HELP_STRING = "Seems like you need some help? Check these commands:\n\n\t"
            + "bye: exit the program\n\t"
            + "edit: edit one task\n\t"
            + "find: search for tasks containing key word\n\t"
            + "findfreeslot: find a free slot with existing timetable\n\t"
            + "help: learn how to use all commands\n\t"
            + "set-reminder: set reminder for a task\n\t"
            + "view: get the daily/weekly/monthly view of timetable\n\t"
            + "view-reminder: get all tasks with reminder\n\t"
            + "deadline: add deadline type tasks\n\t"
            + "event: add event type tasks\n\n"
            + "if you want to know how to use any of them\n"
            + "you can type \"help /command <name of command>\"  for further information!\n"
            + "e.g. \"help /command findfreeslot\", \"help /command deadline\"\n"
            + "Have a nice day with COMPal! :)";
    public static final String WRONG_COMMAND = "Sorry, the description of the command is incorrect\n"
            + "please check the following again:\n\n";

    private String command;

    public HelpCommand() {
    }

    public HelpCommand(String command) {
        this.command = command;
    }

    @Override
    public CommandResult commandExecute(TaskList task) throws CommandException {
        if (command == null)  {
            return new CommandResult(HELP_STRING, false);
        } else {
            switch (command) {
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
            default:
                return new CommandResult(WRONG_COMMAND + HELP_STRING, false);
            }
        }
    }
}
