package compal.logic.parser;


import compal.commons.Messages;

import compal.logic.command.Command;
import compal.logic.parser.exceptions.ParseException;
import compal.model.tasks.Task;
import compal.storage.TaskStorageManager;
import compal.ui.UiUtil;

import java.util.ArrayList;
import java.util.Scanner;

/**
 * Deals with user inputs.
 */
public class ParserManager {
    //***Class Properties/Variables***--------------------------------------------------------------------------------->
    public static final String CMD_EXIT = "bye";
    public static final String CMD_LIST = "list";
    public static final String CMD_CLEAR = "clear";
    public static final String CMD_DONE = "done";
    public static final String CMD_DELETE = "delete";
    public static final String CMD_EVENT = "event";
    public static final String CMD_DEADLINE = "deadline";
    public static final String CMD_RECUR_TASK = "recurtask";
    public static final String CMD_VIEW = "view";
    public static final String CMD_FIND = "find";
    public static final String CMD_SET_REMINDER = "set-reminder";
    public static final String CMD_VIEW_REMIND = "view-reminder";
    public static final String CMD_LECT = "lect";
    public static final String CMD_TUT = "tut";
    public static final String CMD_SECT = "sect";
    public static final String CMD_LAB = "lab";
    public static final String CMD_HELP = "help";
    public static final String CMD_FIND_FREE_SLOT = "findfreeslot";
    public static final String CMD_EDIT = "edit";
    public static final String CMD_DEFAULT_EMPTY_REST_OF_INPUT = "";

    /**
     * Processes command input by user.
     * Based on the command input by user, it instantiates different command classes
     * and executes the respective methods implemented.
     *
     * @param userInput Entire user string input.
     * @throws ParseException If command input is unknown or user input is empty.
     */
    public Command processCmd(String userInput) throws ParseException {
        Scanner sc = new Scanner(userInput);
        if (sc.hasNext()) {
            sc.next();
            String[] args = userInput.split(" ", 2);
            String commandWord = args[0];
            switch (commandWord) {
            case CMD_LIST:
                ListCommandParser listCommandParser = new ListCommandParser();
                return listCommandParser.parseCommand(CMD_DEFAULT_EMPTY_REST_OF_INPUT);
            case CMD_EXIT:
                ByeCommandParser byeCommandParser = new ByeCommandParser();
                return byeCommandParser.parseCommand(CMD_DEFAULT_EMPTY_REST_OF_INPUT);
            default:
                throw new ParseException(Messages.MESSAGE_INVALID_COMMAND);
            }
        } else {
            throw new ParseException(Messages.MESSAGE_EMPTY_INPUT);
        }
    }

}


