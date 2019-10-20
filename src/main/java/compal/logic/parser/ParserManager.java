package compal.logic.parser;

import compal.logic.command.ByeCommand;
import compal.logic.command.Command;

import compal.logic.parser.exceptions.ParserException;

import java.text.ParseException;

/**
 * Deals with user inputs.
 */
public class ParserManager {
    //***Class Properties/Variables***--------------------------------------------------------------------------------->
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

    public static final String MESSAGE_INVALID_COMMAND = "Error: Unknown command input detected!";

    /**
     * Processes command input by user.
     * Based on the command input by user, it instantiates different command classes
     * and executes the respective methods implemented.
     *
     * @param userInput Entire user string input.
     * @throws ParserException If command input is unknown or user input is empty.
     */
    public Command processCmd(String userInput) throws ParserException, ParseException {
        String[] args = userInput.split(" ", 2);
        String commandWord = args[0];
        String restOfInput = "";
        if (args.length != 1) {
            restOfInput = args[1];
        }
        switch (commandWord) {
        case CMD_EXIT:
            return new ByeCommand();
        case CMD_VIEW:
            return new ViewCommandParser().parseCommand(restOfInput);
        case CMD_SET_REMINDER:
            return new SetReminderParser().parseCommand(restOfInput);
        case CMD_VIEW_REMINDER:
            return new ViewReminderParser().parseCommand(restOfInput);
        case CMD_DEADLINE:
            return new DeadlineCommandParser().parseCommand(restOfInput);
        case CMD_DONE:
            return new DoneParser().parseCommand(restOfInput);
        default:
            //suppose to return helpCommand();
            throw new ParserException(MESSAGE_INVALID_COMMAND);
        }

    }

}


