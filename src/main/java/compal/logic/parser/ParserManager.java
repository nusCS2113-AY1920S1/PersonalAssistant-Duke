package compal.logic.parser;

import compal.logic.command.Command;

import compal.logic.parser.exceptions.ParserException;

import java.text.ParseException;

/**
 * Deals with user inputs.
 */
public class ParserManager {
    //***Class Properties/Variables***--------------------------------------------------------------------------------->
    public static final String CMD_EXIT = "bye";
    public static final String CMD_DONE = "done";
    public static final String CMD_DELETE = "delete";
    public static final String CMD_EVENT = "event";
    public static final String CMD_DEADLINE = "deadline";
    public static final String CMD_VIEW = "view";
    public static final String CMD_FIND = "find";
    public static final String CMD_SET_REMINDER = "set-reminder";
    public static final String CMD_VIEW_REMINDER = "view-reminder";
    public static final String CMD_FIND_FREE_SLOT = "findfreeslot";
    public static final String CMD_EDIT = "edit";
    public static final String CMD_LIST = "list";
    public static final String CMD_EXPORT = "export";
    public static final String CMD_IMPORT = "import";


    /**
     * Processes command input by user.
     * Based on the command input by user, it instantiates different command classes
     * and executes the respective methods implemented.
     *
     * @param userInput Entire user string input.
     * @throws ParserException If command input is unknown or user input is empty.
     */
    public Command processCmd(String userInput) throws ParserException, ParseException {
        userInput = userInput.stripLeading();
        String[] args = userInput.split(" ", 2);
        String commandWord = args[0];
        String restOfInput = "";
        if (args.length != 1) {
            restOfInput = args[1];
        }
        switch (commandWord) {
        case CMD_EXIT:
            return new ByeCommandParser().parseCommand(restOfInput);
        case CMD_VIEW:
            return new ViewCommandParser().parseCommand(restOfInput);
        case CMD_SET_REMINDER:
            return new SetReminderCommandParser().parseCommand(restOfInput);
        case CMD_VIEW_REMINDER:
            return new ViewReminderCommandParser().parseCommand(restOfInput);
        case CMD_FIND_FREE_SLOT:
            return new FindFreeSlotCommandParser().parseCommand(restOfInput);
        case CMD_DEADLINE:
            return new DeadlineCommandParser().parseCommand(restOfInput);
        case CMD_DONE:
            return new DoneCommandParser().parseCommand(restOfInput);
        case CMD_FIND:
            return new FindCommandParser().parseCommand(restOfInput);
        case CMD_EVENT:
            return new EventCommandParser().parseCommand(restOfInput);
        case CMD_EDIT:
            return new EditCommandParser().parseCommand(restOfInput);
        case CMD_LIST:
            return new ListCommandParser().parseCommand(restOfInput);
        case CMD_DELETE:
            return new DeleteCommandParser().parseCommand(restOfInput);
        case CMD_EXPORT:
            return new ExportCommandParser().parseCommand(restOfInput);
        case CMD_IMPORT:
            return new ImportCommandParser().parseCommand(restOfInput);
        default:
            return new HelpCommandParser().parseCommand(commandWord + "_" + restOfInput);
        //suppose to return helpCommand();
        //throw new ParserException(MESSAGE_INVALID_COMMAND);
        }

    }

}


